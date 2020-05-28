package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Register {
    /**
     * @param body {username, password, tel, email, name, province, city, area} params 账号, 密码, 手机, 邮箱, 昵称, 省, 市, 地
     * @return {result,username} result:true为成功 username:账号
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Object[] objects = body.values().toArray();
        for (Object o : objects) {
            System.out.println(o);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("username", String.valueOf(10));
        //TODO
        return result;
    }
}
