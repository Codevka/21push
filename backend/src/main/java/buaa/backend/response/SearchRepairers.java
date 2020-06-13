package buaa.backend.response;

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
public class SearchRepairers {
    private static final Logger logger = LoggerFactory.getLogger(SearchRepairers.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String province;
    private String city;
    private String area;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/searchRepairers", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        jdbcTemplate.execute(con -> {
            String storedProc = "select * from Account where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("username")));
            return cs;
        }, this::getPCA);
        String key = "%" + body.get("keyword") + "%";
        return jdbcTemplate.execute(con -> {
            String storedProc = "select * from Account where userType = 2 and province = ? and city = ? and area = ?" +
                    "and (name = ? or tel = ? or username = ?)";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, province);
            cs.setString(2, city);
            cs.setString(3, area);
            cs.setString(4, key);
            cs.setString(5, key);
            cs.setInt(6, Integer.parseInt((String) body.get("keyword")));
            return cs;
        }, this::getResult);
    }

    private boolean getPCA(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            province = rs.getString("province");
            city = rs.getString("city");
            area = rs.getString("area");
        }
        return true;
    }

    private List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        List<Map<String, Object>> res = new ArrayList<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("username", String.valueOf(rs.getInt("username")));
            tmp.put("name", rs.getString("name"));
            tmp.put("tel", rs.getString("tel"));
            res.add(tmp);
        }
        return res;
    }
}
