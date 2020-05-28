package buaa.backend.response;

import buaa.backend.metadata.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Login {

    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> login(@RequestBody Map<String, Object> body) {
        Object[] objects = body.values().toArray();
        for (Object o : objects) {
            System.out.println(o);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("userType","");
        result.put("username","");
        result.put("password","");
        result.put("tel","");
        result.put("email","");
        result.put("name","");
        result.put("province","");
        result.put("city","");
        result.put("area","");
        return result;
    }
}
