package buaa.backend.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StopRent {
    private static final Logger logger = LoggerFactory.getLogger(StopRent.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/stopRent", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Boolean r = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call adminCloseHouse(?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, Integer.parseInt((String) body.get("houseId")));
            return cs;
        }, cs -> {
            cs.execute();
            return cs.getInt(1) == 1;
        });
        Map<String, Object> result = new HashMap<>();
        result.put("result", r);
        return result;
    }
}
