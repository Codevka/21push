package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class GetUserContract {
    /**
     * @param body {username} params 账号
     * @return [{contractId, houseId, rentTime, contractTime,contractDuration, price, status}]
     * 订单编号,房源编号,租房时间,订单时间,订单持续时间,价格,状态
     * 状态:'已缴费' '未缴费'
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getUserContract", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Object> response(@RequestBody Map<String, Object> body) {
        List<Object> result = new ArrayList<>();
        //TODO
        return result;
    }
}
