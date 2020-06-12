package buaa.backend.response;

import buaa.backend.metadata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
public class LeaseBack {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/leaseBack", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        Map<String, Object> result = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt((String) body.get("contractId")));
            return cs;
        }, this::getOrderResult);
        assert result != null;
        result.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, (Integer) result.get("houseId"));
            return cs;
        }, this::getHouseResult)));
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update Orders set status = 3 where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, (Integer) result.get("contractId"));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        int money;
        if (result.get("rentType") == RentType.LONG) {
            Date date = (Date) result.get("rentTime");
            LocalDate today = LocalDate.now();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            LocalDate oldDate = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            Period p = Period.between(oldDate, today);
            money = ((Integer) result.get("price")) * p.getDays() / (c.getActualMaximum(Calendar.MONTH));
        } else {
            Date date = (Date) result.get("rentTime");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            LocalDate oldDate = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            long days = LocalDate.now().until(oldDate, ChronoUnit.DAYS);
            money = (int) (((Integer) result.get("price")) * days / (c.getActualMaximum(Calendar.MONTH)));
        }
        jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "update AccountMoney set money = money + ? where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, money);
            cs.setInt(2, (Integer) result.get("username"));
            return cs;
        }, cs -> {
            cs.execute();
            return true;
        });
        return result;
    }

    private Map<String, Object> getOrderResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("contractId", rs.getInt("contractId"));
            res.put("username", rs.getInt("username"));
            res.put("houseId", rs.getInt("houseId"));
            res.put("price", rs.getInt("price"));
            res.put("rentTime", rs.getDate("rentTime"));
        }
        return res;
    }

    private Map<String, Object> getHouseResult(CallableStatement cs) throws SQLException {
        Map<String, Object> res = new HashMap<>();
        cs.execute();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("rentType", RentType.values()[rs.getInt("rentType")]);
        }
        return res;
    }
}
