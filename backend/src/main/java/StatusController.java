package backend;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping("/mailtest")
    public String EmailRequest()
    {
        EmailServices emailServices = new EmailServices();
        return emailServices.sendTestMail();
    }

    @RequestMapping(value = "/mail-name-test", method = RequestMethod.POST)
    public String EmailNameRequest(@RequestBody String userName)
    {
        EmailServices emailServices = new EmailServices();
        return emailServices.selectEmail(userName);
    }

    public void setStatus(String status){
        this.status  = status;
    }
    public String getStatus(){
       return status;
    }
}