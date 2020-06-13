package buaa.backend.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PayMoney {
    private static final Logger logger = LoggerFactory.getLogger(PayMoney.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/payMoney", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        String pwd = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from Account where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("username")));
            return cs;
        }, cs -> {
            cs.execute();
            ResultSet rs = cs.getResultSet();
            String p = null;
            while (rs.next()) {
                p = rs.getString("password");
            }
            return p;
        });
        Map<String, Object> result = new HashMap<>();
        assert pwd != null;
        if (!pwd.equals(body.get("password"))) {
            result.put("result", false);
        } else {
            jdbcTemplate.execute((CallableStatementCreator) con -> {
                String storedProc = "update AccountMoney set money = money + ? where username = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, Integer.parseInt((String) body.get("amount")));
                cs.setInt(2, Integer.parseInt((String) body.get("username")));
                return cs;
            }, cs -> {
                cs.execute();
                return true;
            });
            result.put("result", true);
        }
        logger.trace(result.toString());
        return result;
    }
}
