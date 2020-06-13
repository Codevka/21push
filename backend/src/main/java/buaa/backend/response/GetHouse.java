package buaa.backend.response;

import buaa.backend.metadata.HouseStatus;
import buaa.backend.metadata.HouseType;
import buaa.backend.metadata.RentType;
import buaa.backend.service.HouseRentCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetHouse {
    private static final Logger logger = LoggerFactory.getLogger(GetHouse.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/getHouse", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        return jdbcTemplate.execute(con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("houseId")));
            return cs;
        }, this::getResult);
    }

    private Map<String, Object> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> map = new HashMap<>();
        while (rs.next()) {
            map.put("houseId", String.valueOf(rs.getInt("houseId")));
            map.put("province", rs.getString("province"));
            map.put("city", rs.getString("city"));
            map.put("area", rs.getString("area"));
            map.put("address", rs.getString("address"));
            map.put("rentType", RentType.values()[rs.getInt("rentType")].getText());
            map.put("houseType", HouseType.values()[rs.getInt("houseType")].getText());
            map.put("intro", rs.getString("intro"));
            map.put("tel", rs.getString("ownerTel"));
            map.put("price", String.valueOf(rs.getInt("price")));
            map.put("housestatus", HouseStatus.OK.getText());
//            map.put("housestatus", (HouseRentCount.count(rs.getInt("houseId")) == rs.getInt("houseType") ?
//                    HouseStatus.FULL : HouseStatus.OK).getText());
            map.put("pic", rs.getString("pic").split(";"));
        }
        return map;
    }
}
