package buaa.backend;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Test2 {

    @Scheduled(cron = "0/3 * * * * *")
    private void testTimer() {
        System.out.println(System.currentTimeMillis());
    }
}
