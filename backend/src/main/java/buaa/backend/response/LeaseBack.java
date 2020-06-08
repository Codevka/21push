package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LeaseBack {
    /**
     * @param body {contractId} params 订单编号
     * @return result: true为成功
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/leaseBack", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}