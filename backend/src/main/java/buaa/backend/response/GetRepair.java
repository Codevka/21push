package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetRepair {
    /**
     * @param body {repairId} params 报修编号
     * @return {repairId, houseId, content, status, evaluation, score}
     * 报修编号, 房源编号, 报修内容, 处理状态, 评价内容, 评分
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getRepair", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
