package buaa.backend.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IsSuperAdmin {
    private static final Logger logger = LoggerFactory.getLogger(IsSuperAdmin.class);

    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/isSuperAdmin", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
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
