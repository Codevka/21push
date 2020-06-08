package buaa.backend.response;

import buaa.backend.metadata.HouseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestoreRent {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/restoreRent", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update House set houseStatus = ? where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, HouseStatus.INT.ordinal());
            cs.setInt(2, Integer.parseInt((String) body.get("houseId")));
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
