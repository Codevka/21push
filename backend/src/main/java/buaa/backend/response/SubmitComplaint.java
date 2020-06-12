package buaa.backend.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SubmitComplaint {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param body {houseId, username, content, pic} params 房源编号 账号 报修内容 图片url
     * @return result: true为成功
     */
    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitComplaint", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "insert into Complaint (username, houseId, dealingStatus, content, pic)" +
                    " values(?,?,0,?,?) ";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("username")));
            cs.setInt(2, Integer.parseInt((String) body.get("houseId")));
            cs.setString(3, (String) body.get("content"));
            cs.setString(4, String.join(";", (List<String>) body.get("pic")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        return result;
    }
}
