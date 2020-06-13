package buaa.backend.response;

import buaa.backend.metadata.ContractStatus;
import buaa.backend.metadata.HouseStatus;
import buaa.backend.metadata.HouseType;
import buaa.backend.metadata.RentType;
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
import java.util.Objects;

@RestController
public class GetContract {
    private static final Logger logger = LoggerFactory.getLogger(GetContract.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getContract", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        Map<String, Object> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("contractId")));
            return cs;
        }, this::getOrderResult);
        assert result != null;
        result.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) result.get("houseId")));
            return cs;
        }, this::getHouseResult)));
        return result;
    }

    private Map<String, Object> getOrderResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("contractId", String.valueOf(rs.getInt("contractId")));
            res.put("username", String.valueOf(rs.getInt("username")));
            res.put("houseId", String.valueOf(rs.getInt("houseId")));
            res.put("price", String.valueOf(rs.getInt("price")));
            res.put("contractStatus", ContractStatus.values()[rs.getInt("status")].getText());
        }
        return res;
    }

    private Map<String, Object> getHouseResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("houseLocation", rs.getString("address"));
            res.put("houseType", HouseType.values()[rs.getInt("houseType")].getText());
            res.put("ownerTel", rs.getString("ownerTel"));
            res.put("housestatus", HouseStatus.values()[rs.getInt("houseStatus")].getText());
            res.put("rentType", RentType.values()[rs.getInt("rentType")]);
        }
        return res;
    }
}
