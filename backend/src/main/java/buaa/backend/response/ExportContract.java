package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ExportContract {

    @CrossOrigin
    @RequestMapping(value = "/exportContract", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("url", "http://123.57.41.160/download?id=" + body.get("contractId"));
        return result;
    }
}
