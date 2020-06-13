package buaa.backend.service;

import buaa.backend.metadata.ComplaintStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HouseRentCount {
    @Autowired
    private static JdbcTemplate jdbcTemplate;

    public static int count(int houseId) {
        int[] i = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, houseId);
            return cs;
        }, cs -> {
            cs.execute();
            int[] f = new int[2];
            ResultSet rs = cs.getResultSet();
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
        return 0;
    }

    private static List<Map<String, Object>> getResult(CallableStatement cs) throws SQLException {
        cs.execute();
        List<Map<String, Object>> res = new ArrayList<>();
        ResultSet rs = cs.getResultSet();
        while (rs.next()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("complaintId", String.valueOf(rs.getInt("complaintId")));
            tmp.put("houseId", String.valueOf(rs.getInt("houseId")));
            tmp.put("username", String.valueOf(rs.getInt("username")));
            tmp.put("status", ComplaintStatus.values()[rs.getInt("dealingStatus")].getText());
            res.add(tmp);
        }
        return res;
    }
}
