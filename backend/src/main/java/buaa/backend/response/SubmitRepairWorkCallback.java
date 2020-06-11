package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SubmitRepairWorkCallback {
    /**
     * @param body {repairWorkId, callback} params 维修工单编号, 维修反馈
     * @return result: true为成功
     */
    @CrossOrigin//("http://localhost:8080")
    @RequestMapping(value = "/submitRepairWorkCallback", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
