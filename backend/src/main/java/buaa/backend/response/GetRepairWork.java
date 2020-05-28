package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetRepairWork {
    /**
     * @param body {repairWorkId} params 维修工单编号
     * @return {repairWorkId, repairId, houseId, location, content, pic, status, callback, evaluation, tel}
     * 工单编号, 报修编号, 房源编号, 房源地址, 报修内容, 图片url, 状态, 维修反馈, 评价, 租户手机号
     * 状态: 未完成, 已完成
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getRepairWork", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
