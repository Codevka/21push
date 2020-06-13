package buaa.backend.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangeUserPassword {
    private static final Logger logger = LoggerFactory.getLogger(ChangeUserPassword.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = new HashMap<>();
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Account set password = ? where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, (String) body.get("password"));
            cs.setInt(2, Integer.parseInt((String) body.get("username")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        result.put("result", true);
        return result;
    }
}
