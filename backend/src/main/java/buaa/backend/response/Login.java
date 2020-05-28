package buaa.backend.response;

import buaa.backend.metadata.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Login {
    /**
     * @param body {userType, username, password} params 用户类型:0租户 1客服 2维修师傅, 账号或手机, 密码
     * @return result: true为成功, 成功时还返回:{userType, username, password, tel, email, name, province, city, area}
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Object[] objects = body.values().toArray();
        for (Object o : objects) {
            System.out.println(o);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("userType", "");
        result.put("username", "");
        result.put("password", "");
        result.put("tel", "");
        result.put("email", "");
        result.put("name", "");
        result.put("province", "");
        result.put("city", "");
        result.put("area", "");
        //TODO
        return result;
    }
}
