package buaa.backend.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LeaseRenew {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param body {contractId, month} params 订单编号, 续租月数
     * @return result: true为成功
     */
    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/leaseRenew", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Orders set contractDuration = contractDuration + ? where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, (Integer) result.get("month"));
            cs.setInt(2, (Integer) result.get("contractId"));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        result.put("result", true);
        return result;
    }
}
