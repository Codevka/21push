package buaa.backend.response;

import buaa.backend.metadata.HouseType;
import buaa.backend.metadata.RentType;
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
public class NewHouse {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/newHouse", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
//        System.out.println(body.get("test").getClass());
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call adminImportNewHouse(?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, (String) body.get("province"));
            cs.setString(3, (String) body.get("city"));
            cs.setString(4, (String) body.get("area"));
            cs.setString(5, (String) body.get("address"));
            cs.setInt(6, Objects.requireNonNull(RentType.fromString((String) body.get("rentType"))).ordinal());
            cs.setInt(7, Objects.requireNonNull(HouseType.fromString((String) body.get("houseType"))).ordinal());
            cs.setString(8, String.join(";", (List<String>) body.get("pic")));
            cs.setString(9, (String) body.get("intro"));
            cs.setString(10, (String) body.get("tel"));
//            cs.setString(10, ((Long) body.get("tel")).toString());
            cs.setString(11, (String) body.get("price"));
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
