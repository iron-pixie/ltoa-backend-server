package backend;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RestController
public class StatusController {
    private String status;

    @RequestMapping("/")
    public String StatusRequest()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        StatusController obj = (StatusController) context.getBean("StatusBean");
        return obj.getStatus();
    }

    public void setStatus(String status){
        this.status  = status;
    }
    public String getStatus(){
       return status;
    }
}