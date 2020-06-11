package buaa.backend.response;


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
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param body {userType, username, password, tel, email, name, province, city, area} params
     * @return result: true为成功
     */
    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call allUpdateSelfInfo(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, Integer.parseInt((String) body.get("username")));
            cs.setString(3, (String) body.get("password"));
            cs.setString(4, ((Long) body.get("tel")).toString());
            cs.setString(5, (String) body.get("email"));
            cs.setString(6, (String) body.get("name"));
            cs.setString(7, (String) body.get("province"));
            cs.setString(8, (String) body.get("city"));
            cs.setString(9, (String) body.get("area"));
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
