package buaa.backend.response;

import buaa.backend.metadata.RepairStatus;
import buaa.backend.metadata.WorkStatus;
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
public class SubmitRepairWorkCallback {
    private static final Logger logger = LoggerFactory.getLogger(SubmitRepairWorkCallback.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitRepairWorkCallback", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update WorkOrder set callback = ?,status = ? where repairWorkId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, (String) body.get("callback"));
            cs.setInt(2, WorkStatus.FINISHED.ordinal());
            cs.setInt(3, Integer.parseInt((String) body.get("repairWorkId")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        Integer rid = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from WorkOrder where repairWorkId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("repairWorkId")));
            return cs;
        }, cs -> {
            cs.execute();
            ResultSet rs = cs.getResultSet();
            Integer s = null;
            while (rs.next()) {
                s = rs.getInt("repairId");
            }
            return s;
        });
        assert rid != null;
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Repair set  status = ? where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, RepairStatus.UNEVAL.ordinal());
            cs.setInt(2, rid);
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        return result;
    }
}
