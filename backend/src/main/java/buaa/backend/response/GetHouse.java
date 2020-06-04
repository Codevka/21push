package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetHouse {
    /**
     * @param body {houseId} params 房源编号
     * @return {houseId, province, city, area, address, rentType, houseType, intro, tel, price, housestatus, pic}
     * 房源编号,省份,城市,地区,具体地址,租房类型,房间类型,介绍,户主手机号,价格,房源状态, 图片
     * 图片是url数组
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getHouse", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
