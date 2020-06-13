package buaa.backend.response;

import buaa.backend.service.HouseRentCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DeleteRent {
    private static final Logger logger = LoggerFactory.getLogger(DeleteRent.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/deleteRent", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Map<String, Object> result = new HashMap<>();
        //TODO 可删已出租词源
        if (HouseRentCount.count(Integer.parseInt((String) body.get("houseId"))) != 0) {
            result.put("result", false);
            return result;
        }
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "delete from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("houseId")));
            return cs;
        }, cs -> {
            try {
                cs.execute();
            } catch (Exception e) {
                return false;
            }
            return true;
        });

        result.put("result", true);
        return result;
    }
}
