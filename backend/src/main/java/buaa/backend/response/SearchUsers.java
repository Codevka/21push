package buaa.backend.response;

import buaa.backend.metadata.UserType;
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
public class SearchUsers {
    private static final Logger logger = LoggerFactory.getLogger(SearchUsers.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/searchUsers", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        if (body.get("keyword").equals("")) {
            return jdbcTemplate.execute(con -> {
                String storedProc = "select * from Account where userType = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, UserType.RENTER.ordinal());
                return cs;
            }, this::getResult);
        }
        List<Map<String, Object>> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Account where username = ? and userType = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("keyword")));
            cs.setInt(2, UserType.RENTER.ordinal());
            return cs;
        }, this::getResult);
        assert result != null;
        result.addAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Account where tel = ? and userType = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setString(1, (String) body.get("keyword"));
            cs.setInt(2, UserType.RENTER.ordinal());
            return cs;
        }, this::getResult)));
        Map<String, Map<String, Object>> tmp = new HashMap<>();
        for (Map<String, Object> m : result) {
            tmp.put((String) m.get("username"), m);
        }
        List<Map<String, Object>> res = new ArrayList<>(tmp.values());
        logger.trace("res is {}", res);
        return res;
    }

    private List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        List<Map<String, Object>> res = new ArrayList<>();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("username", String.valueOf(rs.getInt("username")));
            tmp.put("userType", UserType.values()[rs.getInt("userType")].getText());
            tmp.put("name", rs.getString("name"));
            res.add(tmp);
        }
        return res;
    }
}
