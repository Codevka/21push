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
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetComplaint {
    private static final Logger logger = LoggerFactory.getLogger(GetComplaint.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getComplaint", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        return jdbcTemplate.execute(con -> {
            String storedProc = "select * from Complaint where complaintId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("complaintId")));
            return cs;
        }, this::getResult);
    }

    private Map<String, Object> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> map = new HashMap<>();
        while (rs.next()) {
            map.put("complaintId", String.valueOf(rs.getInt("complaintId")));
            map.put("houseId", String.valueOf(rs.getInt("houseId")));
            map.put("username", String.valueOf(rs.getInt("username")));
            map.put("content", rs.getString("content"));
            map.put("status", ComplaintStatus.values()[rs.getInt("dealingStatus")].getText());
            map.put("adminId", String.valueOf(rs.getInt("adminID")));
            map.put("reply", rs.getString("reply"));
            map.put("evaluation", rs.getString("evaluation"));
            map.put("score", rs.getInt("score"));
            map.put("pic", rs.getString("pic").split(";"));
        }
        return map;
    }
}
