package buaa.backend.response;

import buaa.backend.metadata.UserType;
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
public class Register {
    private static final Logger logger = LoggerFactory.getLogger(Register.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call serverInsertNewAccount(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, (String) body.get("password"));
            cs.setInt(3, Integer.parseInt((String) body.get("userType")));
            cs.setString(4, (String) body.get("name"));
            if (Integer.parseInt((String) body.get("userType")) == UserType.RENTER.ordinal()) {
                cs.setString(5, ((Long) body.get("tel")).toString());
            } else {
                cs.setString(5, (String) body.get("tel"));
            }
            cs.setString(6, (String) body.get("area"));
            cs.setString(7, "nouse");
            cs.setString(8, (String) body.get("province"));
            cs.setString(9, (String) body.get("city"));
            cs.setString(10, (String) body.get("email"));
            return cs;
        }, cs -> {
            cs.execute();
            Map<String, Object> res = new HashMap<>();
            if (cs.getInt(1) == 1) {
                res.put("result", true);
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    res.put("username", String.valueOf(rs.getInt(1)));
                }
                System.out.println(res);
            } else {
                res.put("result", false);
            }
            return res;
        });
        if (Integer.parseInt((String) body.get("userType")) == UserType.RENTER.ordinal()) {
            assert result != null;
            logger.trace("result is {}", result);
            addMoneyAccount((String) result.get("username"));
        }
        logger.trace("res is {}", result);
        return result;
    }

    private void addMoneyAccount(String username) {
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "insert into AccountMoney values (?,0)";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt(username));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
    }
}
