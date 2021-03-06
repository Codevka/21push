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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchAllHouses {
    private static final Logger logger = LoggerFactory.getLogger(SearchAllHouses.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/searchAllHouses", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        String key = "%" + body.get("keyword") + "%";
        List<Map<String, Object>> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from House where address like ? or intro like ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, key);
            cs.setString(2, key);
            return cs;
        }, this::getResult);
        return result;
    }

    private List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        List<Map<String, Object>> res = new ArrayList<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("houseId", String.valueOf(rs.getInt("houseId")));
            tmp.put("area", rs.getString("province") + rs.getString("city") + rs.getString("area"));
            tmp.put("address", rs.getString("address"));
            tmp.put("rentType", RentType.values()[rs.getInt("rentType")].getText());
            tmp.put("houseType", HouseType.values()[rs.getInt("houseType")].getText());
            if (HouseStatus.values()[rs.getInt("houseStatus")] == HouseStatus.INT) {
                tmp.put("housestatus", HouseStatus.INT.getText());
            } else {
                tmp.put("housestatus", (HouseRentCount.count(rs.getInt("houseId"), jdbcTemplate) >= rs.getInt("houseType") ?
                        HouseStatus.FULL : HouseStatus.OK).getText());
            }
            res.add(tmp);
        }
        return res;
    }
}
