package buaa.backend.response;

import buaa.backend.metadata.WorkStatus;
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
import java.util.Objects;

@RestController
public class GetRepairWork {
    private static final Logger logger = LoggerFactory.getLogger(GetRepairWork.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getRepairWork", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);

        Map<String, Object> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from WorkOrder where repairWorkId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("repairWorkId")));
            return cs;
        }, this::getWorkResult);

        assert result != null;
        String repairId = (String) result.get("repairId");
        result.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Repair where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt(repairId));
            return cs;
        }, this::getRepairResult)));

        String houseId = (String) result.get("houseId");
        result.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt(houseId));
            return cs;
        }, this::getHouseResult)));

        String username = (String) result.get("username");
        result.remove("username", username);
        result.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Account where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt(username));
            return cs;
        }, this::getAccountResult)));
        return result;
    }

    private Map<String, Object> getWorkResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("repairId", String.valueOf(rs.getInt("repairId")));
            res.put("repairWorkId", String.valueOf(rs.getInt("repairWorkId")));
            res.put("status", WorkStatus.values()[rs.getInt("status")].getText());
            res.put("callback", rs.getString("callback"));
        }
        return res;
    }

    private Map<String, Object> getRepairResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("houseId", String.valueOf(rs.getInt("houseId")));
            res.put("content", rs.getString("content"));
            res.put("pic", rs.getString("pic").split(";"));
            res.put("evaluation", rs.getString("evaluation"));
            res.put("username", String.valueOf(rs.getInt("username")));
        }
        return res;
    }

    private Map<String, Object> getHouseResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("location", rs.getString("address"));
        }
        return res;
    }

    private Map<String, Object> getAccountResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("tel", rs.getString("tel"));
        }
        return res;
    }
}
