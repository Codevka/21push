package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Register {
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> register(@RequestBody Map<String, Object> body) {
        Object[] objects = body.values().toArray();
        for (Object o : objects) {
            System.out.println(o);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        return result;
    }
}
