package buaa.backend.response;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetToken {
    /**
     * @return token 七牛云token
     * AK: K96MCAU7eCnSWz4XUbxIBe9Q9PUm_gBHfacmsAEf
     * SK: g0eagx-yjztmAo0iVi-Nj8QrsCRGrKhdGKIjpVr9
     * bucketName: 21push
     * 官方文档: https://developer.qiniu.com/kodo/manual/1208/upload-token
     */
    @CrossOrigin("http://localhost:8080")
    @RequestMapping(value = "/getToken", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Map<String, Object> response(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        //TODO
        return result;
    }
}
