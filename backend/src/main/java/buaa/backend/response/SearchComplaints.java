package buaa.backend.response;

import buaa.backend.metadata.ComplaintStatus;
import buaa.backend.metadata.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@RestController
public class SearchComplaints {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/searchComplaints", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        List<Map<String, Object>> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Complaint where complaintId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("keyword")));
            return cs;
        }, this::getResult);
        assert result != null;
        result.addAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Complaint where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("keyword")));
            return cs;
        }, this::getResult)));
        result.addAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Complaint where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("keyword")));
            return cs;
        }, this::getResult)));
        Map<String, Map<String, Object>> tmp = new HashMap<>();
        for (Map<String, Object> m : result) {
            tmp.put((String) m.get("complaintId"), m);
        }
        return new ArrayList<>(tmp.values());
    }

    private List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        List<Map<String, Object>> res = new ArrayList<>();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("complaintId", String.valueOf(rs.getInt("complaintId")));
            tmp.put("houseId", String.valueOf(rs.getInt("houseId")));
            tmp.put("username", String.valueOf(rs.getInt("username")));
            tmp.put("status", ComplaintStatus.values()[rs.getInt("status")].getText());
            res.add(tmp);
        }
        return res;
    }
}
