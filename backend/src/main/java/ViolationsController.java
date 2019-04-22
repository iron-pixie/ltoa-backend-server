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
public class ViolationsController {
    private ArrayList<HashMap<String, String>> violation_map_array = new ArrayList();
    private HashMap<String, String> violation_map = new HashMap<String, String>();
    private String violations = "";
    private String ViolationId;
    private String ViolationType;
    private String MemberAddress;
    private String ResponsibleManager;
    private String CreationDate;
    private String Fine;
    private String Status;
    private String Notes;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();
    private Connection con = null;
    private Connection cons = null;
    private Connection con2 = null;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/violation/{id}")
    public HashMap<String, String> ViolationsRequest(@PathVariable("id") String id)
    {  try {
        HashMap<String, String> violation_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        String queryString = "select * from Violations where ViolationId = " + id;
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        violation_map.put("ViolationId", rs.getString(1));
        violation_map.put("ViolationType", rs.getString(2));
        violation_map.put("MemberAddress", rs.getString(3));
        violation_map.put("ResponsibleManager", rs.getString(4));
        violation_map.put("CreationDate", rs.getString(5));
        violation_map.put("Fine", rs.getString(6));
        violation_map.put("Status", rs.getString(7));
        violation_map.put("Notes", rs.getString(8));
        con.close();
        return violation_map;
    }
    catch(Exception exception)
    {
        violation_map.put("Error", exception.toString());
        return violation_map;
    }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/violation/manager-search", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<HashMap<String, String>> workRequestManager(@RequestBody HashMap<String, String> manager)
    {  try {
        ArrayList<HashMap<String, String>> work_map_array = new ArrayList();
        HashMap<String, String> work_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        String queryString = "select * from Violations where ResponsibleManager = '" + manager.get("ResponsibleManager") + "'";
        ResultSet rs = stmt.executeQuery(queryString);
        while(rs.next())
        {
            HashMap<String, String> violation_remap = new HashMap<String, String>();
            violation_remap.put("ViolationId", rs.getString(1));
            violation_remap.put("ViolationType", rs.getString(2));
            violation_remap.put("MemberAddress", rs.getString(3));
            violation_remap.put("ResponsibleManager", rs.getString(4));
            violation_remap.put("CreationDate", rs.getString(5));
            violation_remap.put("Fine", rs.getString(6));
            violation_remap.put("Status", rs.getString(7));
            violation_remap.put("Notes", rs.getString(8));
            violation_map_array.add(violation_remap);
        }
        con.close();
        return violation_map_array;
    }
    catch(Exception exception)
    {
        violation_map.put("Error", exception.toString());
        violation_map_array.add(violation_map);
        return violation_map_array;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/violation/member-search", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<HashMap<String, String>> workRequestAddress(@RequestBody HashMap<String, String> address)
    {  try {
        ArrayList<HashMap<String, String>> work_map_array = new ArrayList();
        HashMap<String, String> work_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        String queryString = "select * from Violations where MemberAddress = '" + address.get("MemberAddress") + "'";
        ResultSet rs = stmt.executeQuery(queryString);
        while(rs.next())
        {
            HashMap<String, String> violation_remap = new HashMap<String, String>();
            violation_remap.put("ViolationId", rs.getString(1));
            violation_remap.put("ViolationType", rs.getString(2));
            violation_remap.put("MemberAddress", rs.getString(3));
            violation_remap.put("ResponsibleManager", rs.getString(4));
            violation_remap.put("CreationDate", rs.getString(5));
            violation_remap.put("Fine", rs.getString(6));
            violation_remap.put("Status", rs.getString(7));
            violation_remap.put("Notes", rs.getString(8));
            violation_map_array.add(violation_remap);
        }
        con.close();
        return violation_map_array;
    }
    catch(Exception exception)
    {
        violation_map.put("Error", exception.toString());
        violation_map_array.add(violation_map);
        return violation_map_array;
    }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/violation/add", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> ViolationsRequestAdd(@RequestBody HashMap<String, String> violationList)
    {  try {
        this.setViolationType(violationList.get("ViolationType"));
        this.setMemberAddress(violationList.get("MemberAddress"));
        this.setResponsibleManager(violationList.get("ResponsibleManager"));
        this.setCreationDate(dateFormat.format(date));
        this.setFine(violationList.get("Fine"));
        this.setStatus(violationList.get("Status"));
        this.setNotes(violationList.get("Notes"));
        violations = "";
        Class.forName("com.mysql.jdbc.Driver");
        String ViolationReplace = new String();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");

        cons = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/MetaData", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryStrings = "select * from MetaData";
        ResultSet rs = stmts.executeQuery(queryStrings);
        rs.next();
        String ViolationCounter = rs.getString(1);
        int ViolationCounter_int = Integer.valueOf(ViolationCounter);
        ViolationCounter_int++;
        ViolationReplace = Integer.toString(ViolationCounter_int);
        this.setViolationId(ViolationReplace);
        Statement stmtss = cons.createStatement();
        String queryStringss = "update MetaData set ViolationCount = "+ ViolationReplace + " where meta = meta";
        stmtss.executeUpdate(queryStringss);

        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/violations", "test", "testtest");
        Statement stmt = con.createStatement();
        String queryString = "insert into Violations(ViolationId, ViolationType, MemberAddress, ResponsibleManager, CreationDate, Fine, Status, Notes)  values ('" + this.getViolationId() + "', '" + this.getViolationType() + "', '" + this.getMemberAddress() + "', '" + this.getResponsibleManager() + "', '" + this.getCreationDate() + "', '" + this.getFine() + "', '" + this.getStatus() + "', '" + this.getNotes() + "')";
        stmt.executeUpdate(queryString);
        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new Violation has been created with ID: " + this.getViolationId() + "The violation is a " + this.getViolationType() + ". This violation is for the residence at " + this.getMemberAddress() + ". The violation ticket was created at ";
        emailMessage += this.getCreationDate() + ". The fine for the violation is: " + this.getFine() + ". The current status of this violation ticket is: " + this.getStatus() + ". The manager responsible is ";
        emailMessage += this.getResponsibleManager() + ". Additional Notes: " + this.getNotes();
        emailServices.sendMailAccess(("New Violation, ID: " + this.getViolationId()), emailMessage, emailServices.selectMail(violationList.get("userName")));
        HashMap<String, String> violation_maps = new HashMap<String, String>();
        violation_maps.put("id", this.getViolationId());
        con.close();
        cons.close();
        return violation_maps;
    }
    catch(Exception exception)
    {
        HashMap<String, String> violation_map = new HashMap<String, String>();
        violation_map.put("Error", "Row not created: "+exception);
        return violation_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/violation/update", method = RequestMethod.POST)
    @ResponseBody
    public String violationUpdate(@RequestBody HashMap<String, String> violationList)
    {  try {

        this.setViolationId(violationList.get("ViolationId"));
        this.setResponsibleManager(violationList.get("ResponsibleManager"));
        this.setFine(violationList.get("Fine"));
        this.setStatus(violationList.get("Status"));
        this.setNotes(violationList.get("Notes"));
        this.setMemberAddress(violationList.get("MemberAddress"));

        Class.forName("com.mysql.jdbc.Driver");
        cons = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/violations", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryString = "update Violations set";

        if(getResponsibleManager() != null)
        {
            queryString += " ResponsibleManager = '" + this.getResponsibleManager();
        }
        if(getStatus() != null)
        {
            if(getResponsibleManager() == null)
            {
                queryString += " Status = '" + this.getStatus();
            }
            else
            {
                queryString += "', Status = '" + this.getStatus();
            }

        }
        if(getNotes() != null)
        {
            if(getResponsibleManager() == null && getStatus() == null)
            {
                queryString += " Notes = '" + this.getNotes();
            }
            else
            {
                queryString += "', Notes = '" + this.getNotes();
            }
        }
        if(getMemberAddress() != null)
        {
            if(getResponsibleManager() == null && getStatus() == null && getNotes() == null)
            {
                queryString += " MemberAddress = '" + this.getMemberAddress();
            }
            else
            {
                queryString += "', MemberAddress = '" + this.getMemberAddress();
            }
        }
        if(getFine() != null)
        {
            if(getMemberAddress() == null && getStatus() == null && getNotes() == null && getResponsibleManager() == null)
            {
                queryString += " Fine = '" + this.getFine();
            }
            else
            {
                queryString += "', Fine = '" + this.getFine();
            }
        }

        queryString += "' where ViolationId = '" + this.getViolationId() + "'";

        stmts.executeUpdate(queryString);
        EmailServices emailServices = new EmailServices();
        cons.close();
        return "Successful update of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/violation/{id}", method = RequestMethod.DELETE)
    public String ViolationsRequestDelete(@PathVariable("id") String id)
    {  try {
        violations = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        String queryString = "delete from Violations where ViolationId = " + id;
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
    @RequestMapping(value = "/violation/all")
    public ArrayList<HashMap<String, String>> ViolationsRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> violation_map_array = new ArrayList();
        HashMap<String, String> violation_map = new HashMap<String, String>();
        violations = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        ResultSet rs = stmt.executeQuery("select * from Violations");
        while(rs.next())
        {
            HashMap<String, String> violation_remap = new HashMap<String, String>();
            violation_remap.put("ViolationId", rs.getString(1));
            violation_remap.put("ViolationType", rs.getString(2));
            violation_remap.put("MemberAddress", rs.getString(3));
            violation_remap.put("ResponsibleManager", rs.getString(4));
            violation_remap.put("CreationDate", rs.getString(5));
            violation_remap.put("Fine", rs.getString(6));
            violation_remap.put("Status", rs.getString(7));
            violation_remap.put("Notes", rs.getString(8));
            violation_map_array.add(violation_remap);
        }
        con.close();
        return violation_map_array;
    }
    catch(Exception exception)
        {
            violation_map.put("Error", exception.toString());
            violation_map_array.add(violation_map);
            return violation_map_array;
        }
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public String getFine() {
        return Fine;
    }

    public String getMemberAddress() {
        return MemberAddress;
    }

    public String getNotes() {
        return Notes;
    }

    public String getResponsibleManager() {
        return ResponsibleManager;
    }

    public String getStatus() {
        return Status;
    }

    public String getViolationId() {
        return ViolationId;
    }

    public String getViolationType() {
        return ViolationType;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public void setFine(String fine) {
        Fine = fine;
    }

    public void setMemberAddress(String memberAddress) {
        MemberAddress = memberAddress;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public void setResponsibleManager(String responsibleManager) {
        ResponsibleManager = responsibleManager;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setViolationId(String violationId) {
        ViolationId = violationId;
    }

    public void setViolationType(String violationType) {
        ViolationType = violationType;
    }

    public void setViolations(String violations){
        this.violations = violations;
    }

    public String getViolations(){
        return violations;
    }
}