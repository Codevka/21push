package buaa.backend.response;


import buaa.backend.metadata.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangeUserInfo {

    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> changeUserInfo(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("User", user);
        return result;
    }
}
