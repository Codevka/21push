package buaa.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class Test {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/hello")
    public List hello() {
//        String sql = "insert into test (name) values ('zhangsan')";
//        jdbcTemplate.execute(sql);
        List res=jdbcTemplate.queryForList("select * from test");
//        System.out.println("执行完成");
        return res;
    }
}
