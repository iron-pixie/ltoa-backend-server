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
public class MemberController {
    private ArrayList<HashMap<String, String>> member_map_array = new ArrayList();
    private HashMap<String, String> member_map = new HashMap<String, String>();
    private String members = "";
    private String specialInstructs = null;
    private String memberName = null;
    private String memberAddress = null;
    private String contactNumber = null;
    private String email = null;
    private Connection con = null;
    private Connection cons = null;
    private Connection con2 = null;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/member/search", method = RequestMethod.POST)
    public HashMap<String, String> membersRequest(@RequestBody HashMap<String, String> Name_map)
    {  try {
        String Address = Name_map.get("memberAddress");
        HashMap<String, String> member_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/members", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MemberController obj = (MemberController) context.getBean("memberBean");
        String queryString = "select * from Members where memberAddress = '" + Address + "'";
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        member_map.put("memberName", rs.getString(1));
        member_map.put("memberAddress", rs.getString(2));
        member_map.put("contactNumber", rs.getString(3));
        member_map.put("email", rs.getString(4));
        con.close();
        return member_map;
    }
    catch(Exception exception)
    {
        member_map.put("Error", exception.toString());;
        return member_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/member/instructions/search", method = RequestMethod.POST)
    public HashMap<String, String> membersRequestInstructions(@RequestBody HashMap<String, String> Name_map)
    {  try {
        String AddressS = Name_map.get("Address");

        HashMap<String, String> member_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/members", "test", "testtest");
        Statement stmt=con.createStatement();
        String queryString = "select * from Intructions where Address = '" + AddressS + "'";
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        member_map.put("Name", rs.getString(1));
        member_map.put("specialInstructions", rs.getString(2));
        con.close();
        return member_map;
    }
    catch(Exception exception)
    {
        member_map.put("Error", exception.toString());
        return member_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/member/add", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> membersRequestAdd(@RequestBody HashMap<String, String> memberList)
    {  try {
        this.setmemberName(memberList.get("memberName"));
        this.setmemberAddress(memberList.get("memberAddress"));
        this.setcontactNumber(memberList.get("contactNumber"));
        this.setemail(memberList.get("email"));
        members = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/members", "test", "testtest");
        Statement stmt = con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MemberController obj = (MemberController) context.getBean("memberBean");
        String queryString = "insert into Members(memberName, memberAddress, contactNumber, email)  values ('" + this.getmemberName() + "', '" + this.getmemberAddress() +  "', '" + this.getContactNumber() + "', '" + this.getemail() + "')";
        stmt.executeUpdate(queryString);

        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new member has been added with Name: " + this.getmemberName() + ". The Address of member is " + this.getmemberAddress();
        emailMessage += ". The current email of this member is: " + this.getemail();
        emailServices.sendMailAccess(("New member , Name: " + this.getmemberName()), emailMessage);
        HashMap<String, String> member_maps = new HashMap<String, String>();
        member_maps.put("Name", getmemberName());
        con.close();
        return member_maps;
    }
    catch(Exception exception)
    {
        HashMap<String, String> member_map = new HashMap<String, String>();
        member_map.put("Error", "Row not created: "+exception);
        return member_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/member/special-instructions/add", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> membersInstructionsAdd(@RequestBody HashMap<String, String> memberList)
    {  try {
        this.setmemberName(memberList.get("memberName"));
        this.setmemberAddress(memberList.get("memberAddress"));
        this.setSpecialInstructs(memberList.get("specialInstructions"));
        members = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/members", "test", "testtest");
        Statement stmt = con.createStatement();
        Statement stmtD = con.createStatement();
        Statement stmtM = con.createStatement();

        String queryStringM = "select * from Members where memberAddress = '" + getmemberAddress() + "'";
        ResultSet rs = stmt.executeQuery(queryStringM);
        rs.next();
        this.setmemberName(rs.getString(1));

        String queryStringD = "delete from Intructions where Address = '" + getmemberAddress() + "'";
        stmtD.executeUpdate(queryStringD);
        String queryString = "insert into Intructions(Name, specialInstructions, Address)  values('" + this.getmemberName() + "', '" + this.getSpecialInstructs() + "', '" + this.getmemberAddress() + "')";
        stmt.executeUpdate(queryString);
        HashMap<String, String> member_maps = new HashMap<String, String>();
        member_maps.put("Name", getmemberName());
        con.close();
        return member_maps;
    }
    catch(Exception exception)
    {
        HashMap<String, String> member_map = new HashMap<String, String>();
        member_map.put("Error", "Row not created: "+exception);
        return member_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/member/update", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> membersRequestUpdate(@RequestBody HashMap<String, String> memberList)
    {  try {
        this.setmemberName(memberList.get("memberName"));
        this.setmemberAddress(memberList.get("memberAddress"));
        this.setcontactNumber(memberList.get("contactNumber"));
        this.setemail(memberList.get("email"));
        members = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/members", "test", "testtest");
        Statement stmt = con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MemberController obj = (MemberController) context.getBean("memberBean");
        //Needs to be set for null values
        String queryString = "update Members set";

        if(getmemberAddress() != null)
        {
            queryString += " memberAddress = '" + this.getmemberAddress();
        }
        if(this.getContactNumber() != null)
        {
            if(getmemberAddress() == null)
            {
                queryString += " contactNumber = '" + this.getContactNumber();
            }
            else
            {
                queryString += "', contactNumber = '" + this.getContactNumber();
            }
        }
        if(this.getemail() != null)
        {
            if(getmemberAddress() == null && this.getContactNumber() == null)
            {
                queryString += " email = '" + this.getemail();
            }
            else
            {
                queryString += "', email = '" + this.getemail();
            }
        }
        queryString += "' where memberAddress = '" + this.getmemberAddress() + "'";
        stmt.executeUpdate(queryString);

        HashMap<String, String> member_maps = new HashMap<String, String>();
        member_maps.put("Name", getmemberName());
        con.close();
        return member_maps;
    }
    catch(Exception exception)
    {
        HashMap<String, String> member_map = new HashMap<String, String>();
        member_map.put("Error", "Row not updated: "+exception);
        return member_map;
    }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/member/delete", method = RequestMethod.DELETE)
    public String membersRequestDelete(@RequestBody HashMap<String, String> Name_map)
    {  try {
        String Name = Name_map.get("memberName");
        members = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/members", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MemberController obj = (MemberController) context.getBean("memberBean");
        String queryString = "delete from Members where memberName = " + Name;
        stmt.executeUpdate(queryString);
        con.close();
        return "Successful Deletion of Row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/member/all")
    public ArrayList<HashMap<String, String>> membersRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> member_map_array = new ArrayList();
        HashMap<String, String> member_map = new HashMap<String, String>();
        members = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/members", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MemberController obj = (MemberController) context.getBean("memberBean");
        ResultSet rs = stmt.executeQuery("select * from Members");
        while(rs.next())
        {
            HashMap<String, String> member_remap = new HashMap<String, String>();
            member_remap.put("memberName", rs.getString(1));
            member_remap.put("memberAddress", rs.getString(2));
            member_remap.put("contactNumber", rs.getString(3));
            member_remap.put("email", rs.getString(4));
            member_map_array.add(member_remap);
        }
        con.close();
        return member_map_array;
    }
    catch(Exception exception)
    {
        member_map.put("Error", exception.toString());
        member_map_array.add(member_map);
        return member_map_array;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/pending-users")
    public ArrayList<HashMap<String, String>> pendingUsers()
    {  try {
        ArrayList<HashMap<String, String>> member_map_array = new ArrayList();
        HashMap<String, String> member_map = new HashMap<String, String>();
        members = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/pending_users", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MemberController obj = (MemberController) context.getBean("memberBean");
        ResultSet rs = stmt.executeQuery("select * from Users");
        while(rs.next())
        {
            HashMap<String, String> member_remap = new HashMap<String, String>();
            member_remap.put("Name", rs.getString(1));
            member_remap.put("userLevel", rs.getString(2));
            member_remap.put("userName", rs.getString(3));
            member_map_array.add(member_remap);
        }
        con.close();
        return member_map_array;
    }
    catch(Exception exception)
    {
        member_map.put("Error", exception.toString());
        member_map_array.add(member_map);
        return member_map_array;
    }
    }


    public String getemail() {
        return email;
    }

    public String getmemberName() {
        return memberName;
    }

    public String getmemberAddress() {
        return memberAddress;
    }

    public String getContactNumber() {return contactNumber;}

    public String getSpecialInstructs() {return specialInstructs;}

    public void setcontactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public void setmemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setmemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public void setmembers(String members){
        this.members = members;
    }

    public void setSpecialInstructs(String specialInstructs) {this.specialInstructs = specialInstructs;}

    public String getmembers(){
        return members;
    }
}