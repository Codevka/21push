package buaa.backend.response;

import buaa.backend.metadata.ComplaintStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GetUserComplaint {
    private static final Logger logger = LoggerFactory.getLogger(GetUserComplaint.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/gtUserComplaint", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        return jdbcTemplate.execute(con -> {
            String storedProc = "select * from Complaint where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("username")));
            return cs;
        }, this::getResult);
    }

    private List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        List<Map<String, Object>> res = new ArrayList<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("complaintId", String.valueOf(rs.getInt("complaintId")));
            tmp.put("houseId", String.valueOf(rs.getInt("houseId")));
            tmp.put("status", ComplaintStatus.values()[rs.getInt("status")].getText());
            tmp.put("adminId", String.valueOf(rs.getInt("adminId")));
            res.add(tmp);
        }
        return res;
    }
}
