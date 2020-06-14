package buaa.backend.response;

import buaa.backend.metadata.ContractStatus;
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
public class SignContract {
    private static final Logger logger = LoggerFactory.getLogger(SignContract.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/signContract", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Orders set status = ? where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, ContractStatus.SIGN.ordinal());
            cs.setInt(2, Integer.parseInt((String) body.get("contractId")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        return result;
    }
}
