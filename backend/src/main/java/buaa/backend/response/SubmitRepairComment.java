package buaa.backend.response;

import buaa.backend.metadata.RepairStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SubmitRepairComment {
    private static final Logger logger = LoggerFactory.getLogger(SubmitRepairComment.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitRepairComment", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Repair set  evaluation = ?, score = ?,status = ? where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, (String) body.get("evaluation"));
            cs.setInt(2, Integer.parseInt((String) body.get("score")));
            cs.setInt(3, RepairStatus.FINISHED.ordinal());
            cs.setInt(4, Integer.parseInt((String) body.get("repairId")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        return result;
    }
}
