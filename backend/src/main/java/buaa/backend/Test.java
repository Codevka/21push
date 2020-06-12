package buaa.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

@RestController
@RequestMapping("/")
public class Test {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/hello")
    public List<?> hello() {
        List<?> res = jdbcTemplate.queryForList("select * from Account");
        System.out.println(res);
        ResultSet result = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call allGetPasswordAndTypeById(?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(2, 1000000);
            cs.registerOutParameter(1, Types.INTEGER);
            return cs;
        }, cs -> {
            cs.execute();
            System.out.println(cs.getInt(1));
            ResultSet set = cs.getResultSet();
            System.out.println(2);
            System.out.println(set.getMetaData().getColumnCount());
            while (set.next()) {
                System.out.println(set.getString("password"));
            }
            return cs.getResultSet();
        });
        return res;
    }
}
