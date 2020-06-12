package buaa.backend.service;

import buaa.backend.metadata.RentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component                //实例化
@Configurable             //注入bean
@EnableScheduling
public class Alert {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Mail mail;
    private final String template = "%s，您好，您租的位于%s的房子即将到期，请及时缴费（如已缴费请忽略）。";

    @Scheduled(cron = "0 0 0 23 * ?")
    private void alertMoneyPayment() {
        List<Map<String, Object>> res = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where status = 2";
            return con.prepareCall(storedProc);
        }, this::getResult);
        assert res != null;
        for (final Map<String, Object> map : res) {
            map.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
                String storedProc = "select * from House where houseId = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, (Integer) map.get("houseId"));
                return cs;
            }, this::getHouseResult)));
        }
        for (final Map<String, Object> map : res) {
            map.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
                String storedProc = "select * from Account where username = ?";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, (Integer) map.get("username"));
                return cs;
            }, this::getUserResult)));
        }
        for (final Map<String, Object> map : res) {
            if (((Integer) map.get("rentType")) != RentType.LONG.ordinal()) {
                continue;
            }
            Date dt = (Date) ((Date) map.get("rentTime")).clone();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.MONTH, (Integer) map.get("contractDuration"));
            dt = c.getTime();
            Date now = new Date(System.currentTimeMillis());
            if (now.after(dt)) {
                continue;
            }
            mail.sendSimpleTextMail((String) map.get("email"), "房租缴费提醒",
                    String.format(template, map.get("name"), map.get("address")));
        }
    }

    private List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        List<Map<String, Object>> res = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("contractId", rs.getInt("contractId"));
            tmp.put("username", rs.getInt("username"));
            tmp.put("houseId", rs.getInt("houseId"));
            tmp.put("rentTime", rs.getDate("rentTime"));
            tmp.put("orderGenerateTime", rs.getDate("orderGenerateTime"));
            tmp.put("contractDuration", rs.getInt("contractDuration"));
            tmp.put("price", rs.getInt("price"));
            res.add(tmp);
        }
        return res;
    }

    private Map<String, Object> getHouseResult(CallableStatement cs) throws SQLException {
        cs.execute();
        Map<String, Object> res = new HashMap<>();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("rentType", rs.getInt("rentType"));
            res.put("address", rs.getString("address"));
        }
        return res;
    }

    private Map<String, Object> getUserResult(CallableStatement cs) throws SQLException {
        cs.execute();
        Map<String, Object> res = new HashMap<>();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            res.put("email", rs.getString("email"));
            res.put("name", rs.getString("name"));
        }
        return res;
    }
}
