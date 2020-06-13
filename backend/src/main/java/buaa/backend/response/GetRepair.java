package buaa.backend.response;

import buaa.backend.metadata.ComplaintStatus;
import buaa.backend.metadata.RepairStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetRepair {
    private static final Logger logger = LoggerFactory.getLogger(GetRepair.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getRepair", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> res = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Repair where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("repairId")));
            return cs;
        }, this::getResult);
        assert res != null;
        if (res.get("status").equals(RepairStatus.UNSOLVED.getText())) {
            res.put("tel", "");
            res.put("callback", "");
        } else {
            Map<String, Object> map = jdbcTemplate.execute((CallableStatementCreator) con -> {
                String storedProc = "select * from WorkOrder where repairId = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, Integer.parseInt((String) body.get("repairId")));
                return cs;
            }, cs -> {
                cs.execute();
                ResultSet rs = cs.getResultSet();
                Map<String, Object> m = new HashMap<>();
                while (rs.next()) {
                    m.put("username", rs.getInt("username"));
                    m.put("callback", rs.getString("callback"));
                }
                return m;
            });
            assert map != null;
            String tel = jdbcTemplate.execute((CallableStatementCreator) con -> {
                String storedProc = "select * from Account where username = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, (Integer) map.get("username"));
                return cs;
            }, cs -> {
                cs.execute();
                ResultSet rs = cs.getResultSet();
                String t = null;
                while (rs.next()) {
                    t = rs.getString("tel");
                }
                return t;
            });
            res.put("tel", tel);
            if (res.get("status").equals(RepairStatus.RUNNING.getText())) {
                res.put("callback", "");
            } else {
                res.put("callback", map.get("callback"));
            }
        }
        return res;
    }

    private Map<String, Object> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> map = new HashMap<>();
        while (rs.next()) {
            map.put("repairId", String.valueOf(rs.getInt("repairId")));
            map.put("houseId", String.valueOf(rs.getInt("houseId")));
            map.put("content", rs.getString("content"));
            map.put("status", RepairStatus.values()[rs.getInt("status")].getText());
            map.put("evaluation", rs.getString("evaluation"));
            map.put("score", rs.getInt("score"));
            map.put("username", String.valueOf(rs.getInt("username")));
            map.put("pic", rs.getString("pic").split(";"));
        }
        return map;
    }
}
