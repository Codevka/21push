package buaa.backend.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Login {
    private static final Logger logger = LoggerFactory.getLogger(Login.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = new HashMap<>();

        //login with tel
        Boolean ok = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call allGetPasswordAndTypeByTel(?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(2, (String) body.get("username"));
            cs.registerOutParameter(1, Types.INTEGER);
            return cs;
        }, cs -> callLogin(cs, body));
        assert ok != null;
        logger.trace("login with tel:{}", ok);
        if (ok) {
            return jdbcTemplate.execute((CallableStatementCreator) con -> {
                String storedProc = "select * from Account where tel = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setString(1, (String) body.get("username"));
                return cs;
            }, cs -> {
                cs.execute();
                return getInfo(cs.getResultSet());
            });
        }
        try {
            Integer.parseInt((String) body.get("username"));
        } catch (Exception e) {
            result.put("result", false);
            return result;
        }
        // login with username
        ok = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call allGetPasswordAndTypeById(?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(2, Integer.parseInt((String) body.get("username")));
            cs.registerOutParameter(1, Types.INTEGER);
            return cs;
        }, cs -> callLogin(cs, body));
        assert ok != null;
        logger.trace("login with username:{}", ok);
        if (ok) {
            return jdbcTemplate.execute((CallableStatementCreator) con -> {
                String storedProc = "select * from Account where username = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setString(1, (String) body.get("username"));
                return cs;
            }, cs -> {
                cs.execute();
                return getInfo(cs.getResultSet());
            });
        }
        result.put("result", false);
        return result;
    }

    private boolean callLogin(CallableStatement cs, Map<String, Object> body) throws SQLException {
        cs.execute();
        if (cs.getInt(1) == -1) return false;
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            if (rs.getInt("userType") != Integer.parseInt((String) body.get("userType"))) {
                return false;
            } else {
                return rs.getString("password").equals(body.get("password"));
            }
        }
        return true;
    }

    private Map<String, Object> getInfo(ResultSet rs) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        while (rs.next()) {
            result.put("result", true);
            result.put("userType", String.valueOf(rs.getInt("userType")));
            result.put("username", String.valueOf(rs.getInt("username")));
            result.put("password", rs.getString("password"));
            result.put("tel", rs.getString("tel"));
            result.put("email", rs.getString("email"));
            result.put("name", rs.getString("name"));
            result.put("province", rs.getString("province"));
            result.put("city", rs.getString("city"));
            result.put("area", rs.getString("area"));
        }
        logger.trace("user info:{}", result);
        return result;
    }
}
