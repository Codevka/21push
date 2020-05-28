package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class GetUserRepairWork {
    /**
     * @param body {username} params 账号
     * @return [{repairWorkId, repairId, status, callback}]
     * 维修工单编号, 报修编号, 状态, 回复内容
     * 状态: '未处理' '已处理'
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getUserRepairWork", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Object> response(@RequestBody Map<String, Object> body) {
        List<Object> result = new ArrayList<>();
        //TODO
        return result;
    }
}
