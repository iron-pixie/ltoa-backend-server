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
    public HashMap<String, String> LoginRequest(@RequestBody HashMap<String, String> loginData) throws Exception
    {
        HashMap<String, String> login_map = new HashMap<String, String>();
        HashMap<String, String> login_response = new HashMap<String, String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/managers", "test", "testtest");
            Statement stmt = con.createStatement();
            String queryString = "select * from Managers where Username = " + loginData.get("username");
        }
        catch(Exception e)
        {
            login_response.put("Auth", "ERROR: Username not Found!");
            login_response.put("Error", e);
            return login_response;
        }
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        login_map.put("Name", rs.getString(1));
        login_map.put("userLevel", rs.getString(2));
        login_map.put("userName", rs.getString(3));
        login_map.put("password", rs.getString(4));
        if(!(loginData.get("password").equals("passw0rd")))
        {
            login_response.put("Auth", "ERROR: Invalid Password!");
            return login_response;
        }

        login_response.put("Auth", "Success");
        login_response.put("userLevel", login_map.get("userLevel"));
        return login_response;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login/add", method = RequestMethod.POST)
    @ResponseBody
    public String LoginAdd(@RequestBody HashMap<String, String> loginList)
    {  try {

        this.setName(violationList.get("ViolationType"));
        this.setUserLevel(violationList.get("MemberAddress"));
        this.setUserName(violationList.get("ResponsibleManager"));
        this.setPassword(dateFormat.format(date));

        Class.forName("com.mysql.jdbc.Driver");
        Connection cons = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/managers", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryString = "insert into Managers(Name, userLevel, userName, password)  values ('" + this.getName() + "', '" + this.getUserLevel() + "', '" + this.getUserName() + "', '" + this.getPassword() + "')";
        ResultSet rs = stmts.executeQuery(queryStrings);
        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new manager has been added with name: " + this.getName() + "The manager has a user level of:  " + this.getUserLevel();
        emailServices.sendMailAccess(("New Manager, Name: " + this.getName()), emailMessage);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

}