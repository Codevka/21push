package buaa.backend.response;

import buaa.backend.metadata.RepairStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RestController
public class SearchRepairs {
    private static final Logger logger = LoggerFactory.getLogger(SearchRepairs.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/searchRepairs", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        if (body.get("keyword").equals("")) {
            return jdbcTemplate.execute(con -> {
                String storedProc = "select * from Repair";
                return con.prepareCall(storedProc);
            }, this::getResult);
        }
        List<Map<String, Object>> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Repair where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("keyword")));
            return cs;
        }, this::getResult);
        assert result != null;
        result.addAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Repair where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("keyword")));
            return cs;
        }, this::getResult)));
        result.addAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Repair where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("keyword")));
            return cs;
        }, this::getResult)));
        Map<String, Map<String, Object>> tmp = new HashMap<>();
        for (Map<String, Object> m : result) {
            tmp.put((String) m.get("repairId"), m);
        }
        return new ArrayList<>(tmp.values());
    }

    private List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        List<Map<String, Object>> res = new ArrayList<>();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("repairId", String.valueOf(rs.getInt("repairId")));
            tmp.put("houseId", String.valueOf(rs.getInt("houseId")));
            tmp.put("username", String.valueOf(rs.getInt("username")));
            tmp.put("status", RepairStatus.values()[rs.getInt("status")].getText());
            res.add(tmp);
        }
        return res;
    }
}