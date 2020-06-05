package buaa.backend.response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IsSuperAdmin {
    /**
     * @param body {username} params 账号
     * @return isSuperAdmin: true为当前用户是超级管理员
     */
    public Map<String, Object> isSuperAdmin(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        if (Integer.parseInt((String) body.get("username")) == 100000000) {
            result.put("isSuperAdmin", true);
        } else {
            result.put("isSuperAdmin", false);
        }
        return result;
    }
}
