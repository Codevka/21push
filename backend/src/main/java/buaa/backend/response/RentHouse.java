package buaa.backend.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RentHouse {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/rentHouse", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        Map<String, Object> result = new HashMap<>();
        List<?> t = jdbcTemplate.queryForList("select * from Orders where houseId = ?",
                Integer.parseInt((String) body.get("houseId")));
        int[] i = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("houseId")));
            return cs;
        }, cs -> {
            cs.execute();
            int[] f = new int[2];
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {
                f[0] = rs.getInt("rentType");
                f[1] = rs.getInt("price");
            }
            return f;
        });
        assert i != null;
        if (i[0] == 0) {
            result.put("result", false);
            return result;
        }
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "insert into Orders(username, houseId, rentTime, orderGenerateTime, contractDuration, status, price, comment)" +
                    " values (?,?,?,now(),?,0,?,null)";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("username")));
            cs.setInt(2, Integer.parseInt((String) body.get("houseId")));
            cs.setInt(3, Integer.parseInt((String) body.get("houseId")));
            // TODO
            cs.setDate(4, null);
            cs.setInt(5, Integer.parseInt((String) body.get("rentDuration")) * i[1]);
            return cs;
        }, cs -> {
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {
                return rs.getInt("rentType");
            }
            return 0;
        });
        result.put("result", true);
        return result;
    }
}
