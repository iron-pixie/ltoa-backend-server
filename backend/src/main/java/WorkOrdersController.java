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
public class WorkOrdersController {
    private ArrayList<HashMap<String, String>> work_map_array = new ArrayList();
    private HashMap<String, String> work_map = new HashMap<String, String>();
    private String works = "";
    private String workId;
    private String workType;
    private String ResponsibleManager;
    private String CreationDate;
    private String Status;
    private String Notes;
    private String Address;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();
    private Connection con = null;
    private Connection cons = null;
    private Connection con2 = null;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/work/{id}")
    public HashMap<String, String> worksRequest(@PathVariable("id") String id)
    {  try {
        HashMap<String, String> work_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/work", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        WorkOrdersController obj = (WorkOrdersController) context.getBean("WorkBean");
        String queryString = "select * from Work where WorkId = " + id;
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        work_map.put("workId", rs.getString(1));
        work_map.put("workType", rs.getString(2));
        work_map.put("ResponsibleManager", rs.getString(3));
        work_map.put("CreationDate", rs.getString(4));
        work_map.put("Status", rs.getString(5));
        work_map.put("Notes", rs.getString(6));
        work_map.put("Address", rs.getString(7));
        con.close();
        return work_map;
    }
    catch(Exception exception)
    {
        work_map.put("Error", exception.toString());
        return work_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/work/manager-search", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<HashMap<String, String>> workRequestManager(@RequestBody HashMap<String, String> manager)
    {  try {
        ArrayList<HashMap<String, String>> work_map_array = new ArrayList();
        HashMap<String, String> work_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/work", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        WorkOrdersController obj = (WorkOrdersController) context.getBean("WorkBean");
        String queryString = "select * from Work where ResponsibleManager = '" + manager.get("ResponsibleManager") + "'";
        ResultSet rs = stmt.executeQuery(queryString);
        while(rs.next())
        {
            HashMap<String, String> work_remap = new HashMap<String, String>();
            work_remap.put("workId", rs.getString(1));
            work_remap.put("workType", rs.getString(2));
            work_remap.put("ResponsibleManager", rs.getString(3));
            work_remap.put("CreationDate", rs.getString(4));
            work_remap.put("Status", rs.getString(5));
            work_remap.put("Notes", rs.getString(6));
            work_remap.put("Address", rs.getString(7));
            work_map_array.add(work_remap);
        }
        con.close();
        return work_map_array;
    }
    catch(Exception exception)
    {
        work_map.put("Error", exception.toString());
        work_map_array.add(work_map);
        return work_map_array;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/work/add", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> worksRequestAdd(@RequestBody HashMap<String, String> workList)
    {  try {
        this.setworkType(workList.get("workType"));
        this.setResponsibleManager(workList.get("ResponsibleManager"));
        this.setCreationDate(dateFormat.format(date));
        this.setStatus(workList.get("Status"));
        this.setNotes(workList.get("Notes"));
        this.setAddress((workList.get("Address")));
        works = "";
        Class.forName("com.mysql.jdbc.Driver");

        String WorkReplace = new String();
        cons = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/MetaData", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryStrings = "select * from MetaData";
        ResultSet rs = stmts.executeQuery(queryStrings);
        rs.next();
        String WorkCounter = rs.getString(3);
        int WorkCounter_int = Integer.valueOf(WorkCounter);
        WorkCounter_int++;
        WorkReplace = Integer.toString(WorkCounter_int);
        this.setworkId(WorkReplace);
        Statement stmtss = cons.createStatement();
        String queryStringss = "update MetaData set WorkCount = "+ WorkReplace + " where meta = meta";
        stmtss.executeUpdate(queryStringss);

        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/work", "test", "testtest");
        Statement stmt = con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        WorkOrdersController obj = (WorkOrdersController) context.getBean("WorkBean");
        String queryString = "insert into Work(WorkId, WorkType, ResponsibleManager, CreationDate, Status, Notes, Address)  values ('" + this.getworkId() + "', '" + this.getworkType() +  "', '" + this.getResponsibleManager() + "', '" + this.getCreationDate() + "', '" + this.getStatus() + "', '" + this.getNotes() + "', " + this.getAddress() + "')";
        stmt.executeUpdate(queryString);
        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new Work orderhas been created with ID: " + this.getworkId() + ". The type of Work Order is " + this.getworkType() + ". The ticket was created at ";
        emailMessage += this.getCreationDate() + ". The current status of this ticket is: " + this.getStatus() + ". The manager responsible is ";
        emailMessage += this.getResponsibleManager() + ". Additional Notes: " + this.getNotes();
        emailServices.sendMailAccess(("New Work Order, ID: " + this.getworkId()), emailMessage, emailServices.selectMail(workList.get("userName")));
        HashMap<String, String> work_maps = new HashMap<String, String>();
        work_maps.put("id", this.getworkId());
        con.close();
        cons.close();
        return work_maps;
    }
    catch(Exception exception)
    {
        HashMap<String, String> work_map = new HashMap<String, String>();
        work_map.put("Error", "Row not created: "+exception);
        return work_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/work/update", method = RequestMethod.POST)
    @ResponseBody
    public String workUpdate(@RequestBody HashMap<String, String> workList)
    {  try {

        this.setworkId(workList.get("WorkId"));
        this.setResponsibleManager(workList.get("ResponsibleManager"));
        this.setStatus(workList.get("Status"));
        this.setNotes(workList.get("Notes"));
        this.setAddress(workList.get("Address"));

        Class.forName("com.mysql.jdbc.Driver");
        cons = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/work", "test", "testtest");
        Statement stmts = cons.createStatement();

        String queryString = "update Work set";

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
            if(getStatus() == null && getResponsibleManager() == null)
            {
                queryString += " Notes = '" + this.getNotes();
            }
            else
            {
                queryString += "', Notes = '" + this.getNotes();
            }

        }
        if(getAddress() != null)
        {
            if((getStatus() == null && getResponsibleManager() == null && getNotes() == null))
            {
                queryString += " Address = '" + this.getAddress();
            }
            else
            {
                queryString += "', Address = '" + this.getAddress();
            }
        }

        queryString += "' where WorkId = '" + this.getworkId() + "'";


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
    @RequestMapping(value = "/work/{id}", method = RequestMethod.DELETE)
    public String worksRequestDelete(@PathVariable("id") String id)
    {  try {
        works = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/work", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        WorkOrdersController obj = (WorkOrdersController) context.getBean("WorkBean");
        String queryString = "delete from Work where WorkId = " + id;
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
    @RequestMapping(value = "/work/all")
    public ArrayList<HashMap<String, String>> worksRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> work_map_array = new ArrayList();
        HashMap<String, String> work_map = new HashMap<String, String>();
        works = "";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/work", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        WorkOrdersController obj = (WorkOrdersController) context.getBean("WorkBean");
        ResultSet rs = stmt.executeQuery("select * from Work");
        while(rs.next())
        {
            HashMap<String, String> work_remap = new HashMap<String, String>();
            work_remap.put("workId", rs.getString(1));
            work_remap.put("workType", rs.getString(2));
            work_remap.put("ResponsibleManager", rs.getString(3));
            work_remap.put("CreationDate", rs.getString(4));
            work_remap.put("Status", rs.getString(5));
            work_remap.put("Notes", rs.getString(6));
            work_remap.put("Address", rs.getString(7));
            work_map_array.add(work_remap);
        }
        con.close();
        return work_map_array;
    }
    catch(Exception exception)
    {
        work_map.put("Error", exception.toString());
        work_map_array.add(work_map);
        return work_map_array;
    }
    }

    public String getAddress() {return Address;}

    public void setAddress(String address) {this.Address = address;}

    public String getCreationDate() {
        return CreationDate;
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

    public String getworkId() {
        return workId;
    }

    public String getworkType() {
        return workType;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
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

    public void setworkId(String WorkId) {
        workId = WorkId;
    }

    public void setworkType(String WorkType) {
        workType = WorkType;
    }

    public void setworks(String works){
        this.works = works;
    }

    public String getworks(){
        return works;
    }
}