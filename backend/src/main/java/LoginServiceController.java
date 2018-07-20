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

@RestController
public class LoginServiceController {

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, String> LoginRequest(@RequestBody HashMap<String, String> loginData)
    {
        HashMap<String, String> login_map = new HashMap<String, String>();
        HashMap<String, String> login_response = new HashMap<String, String>();
        if(!(loginData.get("username").equals("user1")))
        {
            login_response.put("Auth", "ERROR: Username not Found!");
            return login_response;
        }
        else if(!(loginData.get("password").equals("passw0rd")))
        {
            login_response.put("Auth", "ERROR: Invalid Password!");
            return login_response;
        }

        login_response.put("Auth", "Success");
        return login_response;
    }

}