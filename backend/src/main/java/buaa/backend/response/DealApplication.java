package buaa.backend.response;

import buaa.backend.metadata.ContractStatus;
import buaa.backend.metadata.RentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DealApplication {
    private static final Logger logger = LoggerFactory.getLogger(DealApplication.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/dealApplication", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        logger.trace("body is {}", body);
        Integer houseId = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from Orders where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("contractId")));
            return cs;
        }, cs -> {
            cs.execute();
            ResultSet rs = cs.getResultSet();
            int i = 0;
            while (rs.next()) {
                i = rs.getInt("houseId");
            }
            return i;
        });
        assert houseId != null;
        Integer t = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, houseId);
            return cs;
        }, cs -> {
            cs.execute();
            ResultSet rs = cs.getResultSet();
            int i = 0;
            while (rs.next()) {
                i = rs.getInt("rentType");
            }
            return i;
        });
        assert t != null;
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Orders set status = ? where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            //TODO
            cs.setInt(1, (Boolean.parseBoolean((String) body.get("isAccept")) ?
                    (t == RentType.SHORT.ordinal() ? ContractStatus.UNPAY : ContractStatus.NOT_SIGN)
                    : ContractStatus.REFUSED).ordinal());
            cs.setInt(2, Integer.parseInt((String) body.get("contractId")));
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
