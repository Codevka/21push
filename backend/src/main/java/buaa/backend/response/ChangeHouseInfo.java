package buaa.backend.response;

import buaa.backend.metadata.HouseStatus;
import buaa.backend.metadata.HouseType;
import buaa.backend.metadata.RentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ChangeHouseInfo {
    private static final Logger logger = LoggerFactory.getLogger(ChangeHouseInfo.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/changeHouseInfo", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace(body.toString());
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call adminChangeHouseInfo(?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, Integer.parseInt((String) body.get("houseId")));
            cs.setString(3, (String) body.get("province"));
            cs.setString(4, (String) body.get("city"));
            cs.setString(5, (String) body.get("area"));
            cs.setString(6, (String) body.get("address"));
            cs.setInt(7, Objects.requireNonNull(RentType.fromString((String) body.get("rentType"))).ordinal());
            cs.setInt(8, Objects.requireNonNull(HouseType.fromString((String) body.get("houseType"))).ordinal());
            cs.setString(9, String.join(";", ((List<String>) body.get("pic"))));
            cs.setString(10, (String) body.get("intro"));
            cs.setString(11, (String) body.get("tel"));
            cs.setString(12, (String) body.get("price"));
            cs.setInt(13, Objects.requireNonNull(HouseStatus.fromString((String) body.get("housestatus"))).ordinal());
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        return result;
    }
}
