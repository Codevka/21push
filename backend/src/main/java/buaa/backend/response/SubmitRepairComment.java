package buaa.backend.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SubmitRepairComment {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param body {repairId, evaluation, score} params 报修编号, 评价内容, 评分
     * @return result: true为成功
     */
    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitRepairComment", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Repair set evaluation = ?, score = ? where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, (String) body.get("evaluation"));
            cs.setInt(2, Integer.parseInt((String) body.get("score")));
            cs.setInt(3, Integer.parseInt((String) body.get("repairId")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        return result;
    }
}
