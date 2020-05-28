package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetContract {
    /**
     * @param body {contractId} params 订单编号
     * @return {contractId, username, houseId, houseLocation, houseType, ownerTel, price, housestatus, contractStatus, rentType}
     * 订单编号, 账号, 房源编号, 房源具体地址, 房间类型, 户主手机号, 价格, 房源状态 ,订单状态, 租房类型
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getContract", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
