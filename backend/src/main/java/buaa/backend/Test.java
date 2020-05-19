package buaa.backend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Test {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
