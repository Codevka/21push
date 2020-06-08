package buaa.backend.response;

import buaa.backend.metadata.ComplaintStatus;
import buaa.backend.metadata.RepairStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetRepair {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param body {repairId} params 报修编号
     * @return {repairId, houseId, content, status, evaluation, score}
     * 报修编号, 房源编号, 报修内容, 处理状态, 评价内容, 评分
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getRepair", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        return jdbcTemplate.execute(con -> {
            String storedProc = "select * from Repair where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("repairId")));
            return cs;
        }, this::getResult);
    }

    private Map<String, Object> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> map = new HashMap<>();
        while (rs.next()) {
            map.put("repairId", String.valueOf(rs.getInt("repairId")));
            map.put("houseId", String.valueOf(rs.getInt("houseId")));
            map.put("content", rs.getString("content"));
            map.put("status", RepairStatus.values()[rs.getInt("dealingStatus")].getText());
            map.put("evaluation", rs.getString("evaluation"));
            map.put("score", rs.getInt("score"));
        }
        return map;
    }
}