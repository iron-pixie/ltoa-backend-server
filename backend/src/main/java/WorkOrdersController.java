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
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();

    @RequestMapping(value = "/work/{id}")
    public HashMap<String, String> worksRequest(@PathVariable("id") String id)
    {  try {
        HashMap<String, String> work_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/work", "test", "testtest");
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
        return work_map;
    }
    catch(Exception exception)
    {
        work_map.put("Error", exception.toString());
        return work_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/work/add", method = RequestMethod.POST)
    @ResponseBody
    public String worksRequestAdd(@RequestBody HashMap<String, String> workList)
    {  try {
        this.setworkType(workList.get("workType"));
        this.setResponsibleManager(workList.get("ResponsibleManager"));
        this.setCreationDate(dateFormat.format(date));
        this.setStatus(workList.get("Status"));
        this.setNotes(workList.get("Notes"));
        works = "";
        Class.forName("com.mysql.jdbc.Driver");

        String WorkReplace = new String();
        Connection cons = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/MetaData", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryStrings = "select * from MetaData";
        ResultSet rs = stmts.executeQuery(queryStrings);
        rs.next();
        String WorkCounter = rs.getString(1);
        int WorkCounter_int = Integer.valueOf(WorkCounter);
        WorkCounter_int++;
        WorkReplace = Integer.toString(WorkCounter_int);
        this.setworkId(WorkReplace);
        Statement stmtss = cons.createStatement();
        String queryStringss = "update MetaData set WorkCount = "+ WorkReplace + " where meta = meta";
        stmtss.executeUpdate(queryStringss);

        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/work", "test", "testtest");
        Statement stmt = con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        WorkOrdersController obj = (WorkOrdersController) context.getBean("WorkBean");
        String queryString = "insert into Work(WorkId, WorkType, ResponsibleManager, CreationDate, Status, Notes)  values ('" + this.getworkId() + "', '" + this.getworkType() +  "', '" + this.getResponsibleManager() + "', '" + this.getCreationDate() + "', '" + this.getStatus() + "', '" + this.getNotes() + "')";
        stmt.executeUpdate(queryString);
        return "Successful addition of row";
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
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/work", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        WorkOrdersController obj = (WorkOrdersController) context.getBean("WorkBean");
        String queryString = "delete from Work where WorkId = " + id;
        stmt.executeUpdate(queryString);
        return "Successful Deletion of Row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @RequestMapping(value = "/work/all")
    public ArrayList<HashMap<String, String>> worksRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> work_map_array = new ArrayList();
        HashMap<String, String> work_map = new HashMap<String, String>();
        works = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/work", "test", "testtest");
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
            work_map_array.add(work_remap);
        }
        return work_map_array;
    }
    catch(Exception exception)
    {
        work_map.put("Error", exception.toString());
        work_map_array.add(work_map);
        return work_map_array;
    }
    }


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