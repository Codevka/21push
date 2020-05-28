package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RentHouse {
    /**
     * @param body {houseId, username, rentType, rentDuration} params 房源编号, 账号, 租房类型, 租房时间:短租日数 或 长租月数
     * @return result: true为成功
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/rentHouse", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
