package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SubmitRepair {
    /**
     * @param body {houseId, username, content, pic} params 房源编号 账号 报修内容 图片url
     * @return result: true为成功
     */
    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitRepair", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
