package buaa.backend.response;

import buaa.backend.metadata.ComplaintStatus;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param body {complaintId} params 投诉编号
     * @return {complaintId,houseId,content,status,adminId,reply,evaluation,score}
     * 投诉编号, 房源编号, 投诉内容, 状态, 客服编号, 回复, 评价内容, 评分
     * 状态: '未处理' '未评价' '已评价'
     * 评分: range[1,5]
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getComplaint", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
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
        }
        return map;
    }
}
