package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetComplaint {
    /**
     * @param body {complaintId} params 投诉编号
     * @return {complaintId,houseId,content,status,adminId,reply,evaluation,score}
     * 投诉编号, 房源编号, 投诉内容, 状态, 客服编号, 回复, 评价内容, 评分
     * 状态: '未处理' '未评价' '已评价'
     * 评分: range[1,5]
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getComplaint", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
