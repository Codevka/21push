package buaa.backend.response;

import buaa.backend.metadata.WorkStatus;
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
public class GetUserRepairWork {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param body {username} params 账号
     * @return [{repairWorkId, repairId, status, callback}]
     * 维修工单编号, 报修编号, 状态, 回复内容
     * 状态: '未处理' '已处理'
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getUserRepairWork", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        return jdbcTemplate.execute(con -> {
            String storedProc = "select * from WorkOrder where username = ?";
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
            tmp.put("repairWorkId", String.valueOf(rs.getInt("repairWorkId")));
            tmp.put("repairId", String.valueOf(rs.getInt("repairId")));
            tmp.put("status", WorkStatus.values()[rs.getInt("status")].getText());
            tmp.put("callback", rs.getString("callback"));
            res.add(tmp);
        }
        return res;
    }
}
