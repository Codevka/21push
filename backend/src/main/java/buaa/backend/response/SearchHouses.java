package buaa.backend.response;

import buaa.backend.metadata.HouseStatus;
import buaa.backend.metadata.HouseType;
import buaa.backend.metadata.RentType;
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
public class SearchHouses {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private int ht;
    private int rt;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/searchHouses", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        String key = "%" + body.get("keyword") + "%";
        ht = Integer.parseInt((String) body.get("houseType"));
        rt = Integer.parseInt((String) body.get("rentType"));
        List<Map<String, Object>> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from House where (address like ? or intro like ?)";
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
            if (ht != 0 && ht != rs.getInt("houseType")) continue;
            if (rt != 0 && rt != rs.getInt("rentType")) continue;
            if (rs.getInt("houseStatus") != 1) continue;
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("houseId", String.valueOf(rs.getInt("houseId")));
            tmp.put("area", rs.getString("province") + rs.getString("city") + rs.getString("area"));
            tmp.put("address", rs.getString("address"));
            tmp.put("rentType", RentType.values()[rs.getInt("rentType")].getText());
            tmp.put("houseType", HouseType.values()[rs.getInt("houseType")].getText());
            res.add(tmp);
        }
        return res;
    }
}
