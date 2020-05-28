package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class GetUserComplaint {
    /**
     * @param body {username} params 账号
     * @return [{complaintId, houseId, status, adminId}]
     * 投诉编号, 房源编号, 状态, 处理人编号
     * 状态:'未处理' '未评价' '已评价'
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/gtUserComplaint", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public List<Object> reponse(@RequestBody Map<String, Object> body) {
        List<Object> result = new ArrayList<>();
        //TODO
        return result;
    }
}
