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
public class LoginServiceController {

    String Name;
    String userLevel;
    String userName;
    String Password;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, String> LoginRequest(@RequestBody HashMap<String, String> loginData) throws Exception
    {
        HashMap<String, String> login_map = new HashMap<String, String>();
        HashMap<String, String> login_response = new HashMap<String, String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/users", "test", "testtest");
            Statement stmt = con.createStatement();
            String queryString = "select * from Users where userName = '" + loginData.get("username") + "'";
            ResultSet rs = stmt.executeQuery(queryString);
            rs.next();
            login_map.put("Name", rs.getString(1));
            login_map.put("userLevel", rs.getString(2));
            login_map.put("userName", rs.getString(3));
            login_map.put("password", rs.getString(4));
        }
        catch(Exception e)
        {
            login_response.put("Auth", "ERROR: Username not Found!");
            login_response.put("Error", e.toString());
            return login_response;
        }
        if(!(loginData.get("password").equals(login_map.get("password"))))
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

        this.setName(loginList.get("Name"));
        this.setUserLevel(loginList.get("userLevel"));
        this.setUserName(loginList.get("userName"));
        this.setPassword(loginList.get("Password"));

        Class.forName("com.mysql.jdbc.Driver");
        Connection cons = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/managers", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryString = "insert into Users(Name, userLevel, userName, password)  values ('" + this.getName() + "', '" + this.getUserLevel() + "', '" + this.getUserName() + "', '" + this.getPassword() + "')";
        stmts.executeUpdate(queryString);
        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new user has been added with name: " + this.getName() + "The user has a level of:  " + this.getUserLevel();
        emailServices.sendMailAccess(("New User, Name: " + this.getName()), emailMessage);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    private void setName(String name)
    {
        this.Name = name;
    }

    private void setUserLevel(String UserLevel)
    {
        this.userLevel = UserLevel;
    }

    private void setUserName(String UserName)
    {
        this.userName = UserName;
    }

    private void setPassword(String password)
    {
        this.Password = password;
    }

    private String getName()
    {
        return this.Name;
    }

    private String getUserLevel()
    {
        return this.userLevel;
    }

    private String getUserName()
    {
        return this.userName;
    }

    private String getPassword()
    {
        return this.Password;
    }

}
