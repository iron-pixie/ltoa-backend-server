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
    private ArrayList<HashMap<String, String>> guest_map_array = new ArrayList();
    private HashMap<String, String> guest_map = new HashMap<String, String>();
    private String guestName;
    private String residentName;
    private String allowedStartTime;
    private String allowedEndTime;
    private String residentAddress;
    private String carModel;
    private String carMake;
    private String guests;
    private String entryTime;
    private String reason;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/guest/all")
    public ArrayList<HashMap<String, String>> guestRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> guest_map_array = new ArrayList();
        HashMap<String, String> guest_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guests", "test", "testtest");
        Statement stmt=con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Guests");
        while(rs.next())
        {
            HashMap<String, String> guest_remap = new HashMap<String, String>();
            guest_remap.put("guestName", rs.getString(1));
            guest_remap.put("residentAddress", rs.getString(2));
            guest_remap.put("carModel", rs.getString(3));
            guest_remap.put("carMake", rs.getString(4));
            guest_remap.put("residentName", rs.getString(5));
            guest_remap.put("allowedStartTime", rs.getString(6));
            guest_remap.put("allowedEndTime", rs.getString(7));
            guest_remap.put("reason", rs.getString(8));
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
    public String guestAdd(@RequestBody HashMap<String, String> guestList)
    {  try {

        this.setGuestName(guestList.get("guestName"));
        this.setResidentAddress(guestList.get("residentAddress"));
        this.setCarModel(guestList.get("carMake"));
        this.setCarMake(guestList.get("carModel"));
        this.setResidentName("residentName");
        this.setAllowedStartTime("allowedStartTime");
        this.setAllowedEndTime("allowedEndTime");
        this.setReason("reason");

        Class.forName("com.mysql.jdbc.Driver");
        Connection cons = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guests", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryString = "insert into Guests(guestName, residentAddress, carModel, carMake, residentName, allowedStartTime, allowedEndTime, reason)  values ('" + this.getGuestName() + "', '" + this.getResidentAddress() + "', '" + this.getCarModel() + "', '" + this.getCarMake() +  "', '" + this.getResidentName() + "', '" + this.getAllowedStartTime() +  "', '" + this.getAllowedEndTime() +  "', '" + this.getReason() + "')";
        stmts.executeUpdate(queryString);
        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new guest has been added with name: " + this.getGuestName() + "They are associated with address: " + this.getResidentAddress();
        emailServices.sendMailAccess(("New Guest, Name: " + this.getGuestName()), emailMessage);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String guestRegister(@RequestBody HashMap<String, String> registerMap)
    {  try {

        this.setGuestName(registerMap.get("guestName"));
        this.setResidentAddress(registerMap.get("residentAddress"));
        this.setResidentName(registerMap.get("residentName"));
        this.setEntryTime(dateFormat.format(date));

        Class.forName("com.mysql.jdbc.Driver");
        Connection cons = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guests", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryString = "insert into Registry(guestName, residentAddress, residentName, entryTime)  values ('" + this.getGuestName() + "', '" + this.getResidentAddress() + "', '" + this.getResidentName() + "', '" + this.getEntryTime() + "')";
        stmts.executeUpdate(queryString);
        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new guest has passed the gate and registered with the guards with name: " + this.getGuestName() + "They are associated with address: " + this.getResidentAddress() + ". And the time of entry was: "+this.getEntryTime();
        emailServices.sendMailAccess(("Guest, Name: " + this.getGuestName()), emailMessage);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/register/all")
    public ArrayList<HashMap<String, String>> registerRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> guest_map_array = new ArrayList();
        HashMap<String, String> guest_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guests", "test", "testtest");
        Statement stmt=con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Registry");
        while(rs.next())
        {
            HashMap<String, String> guest_remap = new HashMap<String, String>();
            guest_remap.put("guestName", rs.getString(1));
            guest_remap.put("residentAddress", rs.getString(2));
            guest_remap.put("residentName", rs.getString(2));
            guest_remap.put("entryTime", rs.getString(4));
            guest_map_array.add(guest_remap);
        }
        return guest_map_array;
    }
    catch(Exception exception)
    {
        guest_map.put("Error: ", exception.toString());
        guest_map_array.add(guest_map);
        return guest_map_array;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/guest/delete/{guestName}", method = RequestMethod.DELETE)
    public String GuestsRequestDelete(@PathVariable("guestName") String guestName)
    {  try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/guests", "test", "testtest");
        Statement stmt=con.createStatement();
        String queryString = "delete from Guests where guestName = " + guestName;
        stmt.executeUpdate(queryString);
        return "Successful Deletion of Row";
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

    private String getResidentAddress()
    {
        return this.residentAddress;
    }

    private String getCarModel()
    {
        return this.carModel;
    }

    private String getCarMake()
    {
        return this.carMake;
    }

    private String getResidentName() {return this.residentName;}

    private String getAllowedStartTime() {return this.allowedStartTime;}

    private String getAllowedEndTime() {return this.allowedEndTime;}

    private String getEntryTime() {return this.entryTime;}

    private String getReason() {return this.reason;}

    private void setReason(String Reason) {this.reason = Reason;}

    private void setResidentName(String ResidentName) {this.residentName = ResidentName;}

    private void setAllowedStartTime(String AllowedStartTime) {this.allowedStartTime = AllowedStartTime;}

    private void setAllowedEndTime(String AllowedEndTime) {this.allowedEndTime = AllowedEndTime;}

    private void setEntryTime(String EntryTime) {this.entryTime = EntryTime;}

    private void setGuestName(String GuestName)
    {
        this.guestName = GuestName;
    }

    private void setResidentAddress(String ResidentAddress)
    {
        this.residentAddress = ResidentAddress;
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