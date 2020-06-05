package buaa.backend.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DeleteRent {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/deleteRent", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call allUpdateSelfInfo(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.registerOutParameter(1, Types.INTEGER);
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        //TODO
        return result;
    }
}
