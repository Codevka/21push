package buaa.backend.response;

import buaa.backend.metadata.RentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RentHouse {
    private static final Logger logger = LoggerFactory.getLogger(RentHouse.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/rentHouse", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = new HashMap<>();
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
        RentType r = RentType.values()[i[0]];
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
            cs.setDate(3, Date.valueOf((String) body.get("rentTime")));
            cs.setInt(4, (Integer) body.get("rentDuration"));
            cs.setInt(5, i[1]);
            return cs;
        }, cs -> {
            cs.execute();
            return 0;
        });
        result.put("result", true);
        return result;
    }

}
