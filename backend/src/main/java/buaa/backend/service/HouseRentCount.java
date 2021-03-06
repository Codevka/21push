package buaa.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class HouseRentCount {
    private static final Logger logger = LoggerFactory.getLogger(HouseRentCount.class);

    public static boolean isValidRentHouse(int houseId, int username, JdbcTemplate jdbcTemplate) {
        int[] i = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, houseId);
            return cs;
        }, cs -> {
//            logger.trace("before execute");
            cs.execute();
//            logger.trace("after execute");
            int[] f = new int[2];
            ResultSet rs = cs.getResultSet();
//            logger.trace("after get rs");
            while (rs.next()) {
                f[0] = rs.getInt("rentType");
                f[1] = rs.getInt("houseType");
            }
            return f;
        });
        List<Map<String, Object>> res = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where houseId = ? and username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, houseId);
            cs.setInt(2, username);
            return cs;
        }, HouseRentCount::getResult);
        assert res != null;
        assert i != null;
        for (Map<String, Object> map : res) {
            Date dt = (Date) ((Date) map.get("rentTime")).clone();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            int t = i[1] == 1 ? Calendar.DATE : Calendar.MONTH;
            c.add(t, (Integer) map.get("contractDuration"));
            dt = c.getTime();
            Date now = new Date(System.currentTimeMillis());
            if (!now.after(dt)) {
                return true;
            }
        }
        return false;
    }

    public static int count(int houseId, JdbcTemplate jdbcTemplate) {
        int[] i = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, houseId);
            return cs;
        }, cs -> {
//            logger.trace("before execute");
            cs.execute();
//            logger.trace("after execute");
            int[] f = new int[2];
            ResultSet rs = cs.getResultSet();
//            logger.trace("after get rs");
            while (rs.next()) {
                f[0] = rs.getInt("rentType");
                f[1] = rs.getInt("houseType");
            }
            return f;
        });
        List<Map<String, Object>> res = jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, houseId);
            return cs;
        }, HouseRentCount::getResult);
        int cnt = 0;
        assert res != null;
        assert i != null;
        for (Map<String, Object> map : res) {
            Date dt = (Date) ((Date) map.get("rentTime")).clone();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            int t = i[1] == 1 ? Calendar.DATE : Calendar.MONTH;
            c.add(t, (Integer) map.get("contractDuration"));
            dt = c.getTime();
            Date now = new Date(System.currentTimeMillis());
            if (now.after(dt)) {
                continue;
            }
            cnt++;
        }
        logger.trace("houseId {} rentType {} houseType {} cnt {}", houseId, i[0], i[1], cnt);

        return cnt;
    }

    private static List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        List<Map<String, Object>> res = new ArrayList<>();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("rentTime", rs.getDate("rentTime"));
            tmp.put("contractDuration", rs.getInt("contractDuration"));
            res.add(tmp);
        }
        return res;
    }
}
