/*package backend;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViolationsController {

    private static final String template = "Violation id: %s!";
    private String id = "0";

    @RequestMapping("/violation")
    public Violation violation(@RequestParam(value=id, defaultValue="00000") String violation_id) {
        return new Violation(counter.incrementAndGet(),
                            String.format(template, violation_id));
    }

}*/
