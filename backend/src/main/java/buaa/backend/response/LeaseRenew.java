package buaa.backend.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LeaseRenew {
    private static final Logger logger = LoggerFactory.getLogger(LeaseRenew.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/leaseRenew", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = new HashMap<>();
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Orders set contractDuration = contractDuration + ? where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, (Integer) body.get("month"));
            cs.setInt(2, (Integer) body.get("contractId"));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        result.put("result", true);
        return result;
    }
}
