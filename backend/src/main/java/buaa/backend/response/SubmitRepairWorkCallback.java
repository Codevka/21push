package buaa.backend.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SubmitRepairWorkCallback {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitRepairWorkCallback", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update WorkOrder set callback = ? where repairWorkId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, (String) body.get("callback"));
            cs.setInt(2, Integer.parseInt((String) body.get("repairWorkId")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        return result;
    }
}
