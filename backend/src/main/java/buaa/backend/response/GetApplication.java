package buaa.backend.response;

import buaa.backend.metadata.ContractStatus;
import buaa.backend.metadata.HouseStatus;
import buaa.backend.metadata.HouseType;
import buaa.backend.metadata.RentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RestController
public class GetApplication {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getApplication", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        List<Map<String, Object>> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where status = 0";
            return con.prepareCall(storedProc);
        }, this::getOrderResult);
        return result;
    }

    private List<Map<String, Object>> getOrderResult(CallableStatement cs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> res = new HashMap<>();
            res.put("contractId", String.valueOf(rs.getInt("contractId")));
            res.put("houseId", String.valueOf(rs.getInt("houseId")));
            res.put("rentTime", rs.getDate("rentTime").toString());
            res.put("orderGenerateTime", rs.getDate("orderGenerateTime").toString());
            res.put("contractDuration", rs.getInt("contractDuration"));
            res.put("price", String.valueOf(rs.getInt("price")));
            res.put("contractStatus", ContractStatus.values()[rs.getInt("status")].getText());
            list.add(res);
        }
        return list;
    }
}
