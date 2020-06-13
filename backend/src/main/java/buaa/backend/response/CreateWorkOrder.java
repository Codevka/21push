package buaa.backend.response;

import buaa.backend.metadata.HouseStatus;
import buaa.backend.metadata.HouseType;
import buaa.backend.metadata.RentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CreateWorkOrder {
    private static final Logger logger = LoggerFactory.getLogger(CreateWorkOrder.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/createWorkOrder", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Repair set status = 1 where repairId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("repairId")));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "insert into WorkOrder (repairId, username, status)" +
                    " values (?,?,1)";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("repairId")));
            cs.setInt(2, Integer.parseInt((String) body.get("username")));
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
