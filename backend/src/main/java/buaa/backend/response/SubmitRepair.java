package buaa.backend.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SubmitRepair {
    private static final Logger logger = LoggerFactory.getLogger(SubmitRepair.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitRepair", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "insert into Repair (username, houseId, status, content, pic)" +
                    " values(?,?,0,?,?) ";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("username")));
            cs.setInt(2, Integer.parseInt((String) body.get("houseId")));
            cs.setString(3, (String) body.get("content"));
            cs.setString(4, String.join(";", (List<String>) body.get("pic")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        return result;
    }
}
