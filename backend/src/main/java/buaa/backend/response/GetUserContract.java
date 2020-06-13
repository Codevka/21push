package buaa.backend.response;

import buaa.backend.metadata.ContractStatus;
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
public class GetUserContract {
    private static final Logger logger = LoggerFactory.getLogger(GetUserContract.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getUserContract", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        return jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where username = ?";
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
            tmp.put("contractId", String.valueOf(rs.getInt("contractId")));
            tmp.put("houseId", String.valueOf(rs.getInt("houseId")));
            tmp.put("rentTime", rs.getDate("rentTime").toString());
            tmp.put("contractTime", rs.getDate("orderGenerateTime").toString());
            tmp.put("contractDuration", String.valueOf(rs.getInt("contractDuration")));
            tmp.put("price", String.valueOf(rs.getInt("price")));
            tmp.put("status", ContractStatus.values()[rs.getInt("status")].getText());
            res.add(tmp);
        }
        return res;
    }
}
