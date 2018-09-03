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
    String guests;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/guest/all")
    public ArrayList<HashMap<String, String>> worksRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> guest_map_array = new ArrayList();
        HashMap<String, String> guest_map = new HashMap<String, String>();
        guests = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guests", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        GuestController obj = (GuestController) context.getBean("GuestBean");
        ResultSet rs = stmt.executeQuery("select * from Guests");
        while(rs.next())
        {
            HashMap<String, String> guest_remap = new HashMap<String, String>();
            guest_remap.put("guestName", rs.getString(1));
            guest_remap.put("residentAddress", rs.getString(2));
            guest_remap.put("carModel", rs.getString(3));
            guest_remap.put("carMake", rs.getString(4));
            guest_map_array.add(guest_remap);
        }
        return guest_map_array;
    }
    catch(Exception exception)
    {
        guest_map.put("Error", exception.toString());
        guest_map_array.add(guest_map);
        return guest_map_array;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/guest/add", method = RequestMethod.POST)
    @ResponseBody
    public String LoginAdd(@RequestBody HashMap<String, String> guestList)
    {  try {

        this.setGuestName(guestList.get("guestName"));
        this.setResidentAdress(guestList.get("residentAdress"));
        this.setCarModel(guestList.get("carMake"));
        this.setCarMake(guestList.get("carModel"));

        Class.forName("com.mysql.jdbc.Driver");
        Connection cons = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guests", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryString = "insert into Guests(guestName, residentAddress, carModel, carMake)  values ('" + this.getGuestName() + "', '" + this.getResidentAdress() + "', '" + this.getCarModel() + "', '" + this.getCarMake() + "')";
        stmts.executeUpdate(queryString);
        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new guest has been added with name: " + this.getGuestName() + "They are associated with address: " + this.getResidentAdress();
        emailServices.sendMailAccess(("New Guest, Name: " + this.getGuestName()), emailMessage);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
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