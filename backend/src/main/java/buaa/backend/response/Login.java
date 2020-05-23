package buaa.backend.response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Login {
    @RequestMapping("/login")
    public boolean login(@RequestBody Map<String, Object> body) {
        Object[] objects = body.values().toArray();
        for (Object o : objects) {
            System.out.println(o);
        }
        return true;
    }
}
