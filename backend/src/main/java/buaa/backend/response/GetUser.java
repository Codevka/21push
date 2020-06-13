package buaa.backend.response;

import buaa.backend.metadata.RepairStatus;
import buaa.backend.service.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetUser {
    private static final Logger logger = LoggerFactory.getLogger(GetUser.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getUser", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace(body.toString());
        Map<String, Object> res = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Account where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("username")));
            return cs;
        }, this::getResult);
        assert res != null;
        logger.trace(res.toString());
        return res;
    }

    private Map<String, Object> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> result = new HashMap<>();
        while (rs.next()) {
            result.put("result", true);
            result.put("username", String.valueOf(rs.getInt("username")));
            result.put("tel", rs.getString("tel"));
            result.put("email", rs.getString("email"));
            result.put("name", rs.getString("name"));
            result.put("province", rs.getString("province"));
            result.put("city", rs.getString("city"));
            result.put("area", rs.getString("area"));
        }
        return result;
    }
}
