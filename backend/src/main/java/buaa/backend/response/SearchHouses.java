package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SearchHouses {
    /**
     * @param body {keyword} params 关键词
     * @return [{houseId, area, address, rentType, houseType}]
     * 房源编号, 地区, 具体地址, 租房类型, 房间类型
     * 租房类型: '短租' '长租'
     * 房间类型: '二人间' '三人间' '四人间'...
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/searchHouses", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Object> response(@RequestBody Map<String, Object> body) {
        List<Object> result = new ArrayList<>();
        //TODO
        return result;
    }
}
