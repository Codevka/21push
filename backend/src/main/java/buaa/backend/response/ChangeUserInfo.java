package buaa.backend.response;


import buaa.backend.metadata.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangeUserInfo {
    /**
     * @param user {userType, username, password, tel, email, name, province, city, area} params
     * @return result: true为成功
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
//        result.put("User", user);
        //TODO
        return result;
    }
}
