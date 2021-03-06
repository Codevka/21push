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
public class ChangeUserInfo {
    private static final Logger logger = LoggerFactory.getLogger(ChangeUserInfo.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Account set name = ?,tel = ?,area = ?," +
                    "province = ?,city = ?,email = ?,userType = ? where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, (String) body.get("name"));
            cs.setString(2, (String) body.get("tel"));
            cs.setString(3, (String) body.get("area"));
            cs.setString(4, (String) body.get("province"));
            cs.setString(5, (String) body.get("city"));
            cs.setString(6, (String) body.get("email"));
            cs.setInt(7, Integer.parseInt((String) body.get("userType")));
            cs.setInt(8, Integer.parseInt((String) body.get("username")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        logger.trace("res is {}", result);
        return result;
    }
}
