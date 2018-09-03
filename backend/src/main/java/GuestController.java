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
public class GuestController {
    String guestName;
    String residentAdress;
    String carModel;
    String carMake;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/guest/{residentAdress}")
    public HashMap<String, String> guestsRequest(@PathVariable("residentAdress") String ra)
    {  try {
        HashMap<String, String> guest_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guest", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        GuestController obj = (GuestController) context.getBean("GuestBean");
        String queryString = "select * from Guests where residentAdress = " + ra;
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        guest_map.put("guestName", rs.getString(1));
        guest_map.put("residentAdress", rs.getString(2));
        guest_map.put("carModel", rs.getString(3));
        guest_map.put("carMake", rs.getString(4));
        return guest_map;
    }
    catch(Exception exception)
    {
        guest_map.put("Error", exception.toString());
        return guest_map;
    }
    }

    private String getGuestName()
    {
        return this.guestName;
    }

    private String getResidentAdress()
    {
        return this.residentAdress;
    }

    private String getCarModel()
    {
        return this.carModel;
    }

    private String getCarMake()
    {
        return this.carMake;
    }

    private void setGuestName(String GuestName)
    {
        this.guestName = GuestName;
    }

    private void setResidentAdress(String ResidentAdress)
    {
        this.residentAdress = ResidentAdress;
    }

    private void setCarModel(String CarModel)
    {
        this.carModel = CarModel;
    }

    private void setCarMake(String CarMake)
    {
        this.carMake = CarMake;
    }
}