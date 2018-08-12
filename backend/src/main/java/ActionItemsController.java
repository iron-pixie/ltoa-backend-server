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
public class ActionItemsController {
    private ArrayList<HashMap<String, String>> action_map_array = new ArrayList();
    private HashMap<String, String> action_map = new HashMap<String, String>();
    private String actions = "";
    private String actionId;
    private String actionType;
    private String ResponsibleManager;
    private String CreationDate;
    private String Status;
    private String Notes;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/action/{id}")
    public HashMap<String, String> actionsRequest(@PathVariable("id") String id)
    {  try {
        HashMap<String, String> action_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        String queryString = "select * from Actions where ActionId = " + id;
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        action_map.put("actionId", rs.getString(1));
        action_map.put("actionType", rs.getString(2));
        action_map.put("ResponsibleManager", rs.getString(3));
        action_map.put("CreationDate", rs.getString(4));
        action_map.put("Status", rs.getString(5));
        action_map.put("Notes", rs.getString(6));
        return action_map;
    }
    catch(Exception exception)
    {
        action_map.put("Error", exception.toString());
        return action_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/action/add", method = RequestMethod.POST)
    @ResponseBody
    public String actionsRequestAdd(@RequestBody HashMap<String, String> actionList)
    {  try {
        this.setactionType(actionList.get("actionType"));
        this.setResponsibleManager(actionList.get("ResponsibleManager"));
        this.setCreationDate(dateFormat.format(date));
        this.setStatus(actionList.get("Status"));
        this.setNotes(actionList.get("Notes"));
        actions = "";
        Class.forName("com.mysql.jdbc.Driver");

        String ActionReplace = new String();
        Connection cons = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/MetaData", "test", "testtest");
        Statement stmts = cons.createStatement();
        String queryStrings = "select * from MetaData";
        ResultSet rs = stmts.executeQuery(queryStrings);
        rs.next();
        String ActionCounter = rs.getString(1);
        int ActionCounter_int = Integer.valueOf(ActionCounter);
        ActionCounter_int++;
        ActionReplace = Integer.toString(ActionCounter_int);
        this.setactionId(ActionReplace);
        Statement stmtss = cons.createStatement();
        String queryStringss = "update MetaData set ActionCount = "+ ActionReplace + " where meta = meta";
        stmtss.executeUpdate(queryStringss);

        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt = con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        String queryString = "insert into Actions(ActionId, ActionType, ResponsibleManager, CreationDate, Status, Notes)  values ('" + this.getactionId() + "', '" + this.getactionType() +  "', '" + this.getResponsibleManager() + "', '" + this.getCreationDate() + "', '" + this.getStatus() + "', '" + this.getNotes() + "')";
        stmt.executeUpdate(queryString);

        EmailServices emailServices = new EmailServices();
        String emailMessage = "A new Action Item has been created with ID: " + this.getactionId() + ". The type of Action item is " + this.getactionType() + ". The Action Item ticket was created at ";
        emailMessage += this.getCreationDate() + ". The current status of this ticket is: " + this.getStatus() + ". The manager responsible is ";
        emailMessage += this.getResponsibleManager() + ". Additional Notes: " + this.getNotes();
        emailServices.sendMailAccess(("New Action Item, ID: " + this.getactionId()), emailMessage);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/action/manager-search", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<HashMap<String, String>> actionsRequestManager(@RequestBody HashMap<String, String> manager)
    {  try {
        ArrayList<HashMap<String, String>> action_map_array = new ArrayList();
        HashMap<String, String> action_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        String queryString = "select * from Actions where ResponsibleManager = '" + manager.get("ResponsibleManager") + "'";
        ResultSet rs = stmt.executeQuery(queryString);
        while(rs.next())
        {
            HashMap<String, String> action_remap = new HashMap<String, String>();
            action_remap.put("actionId", rs.getString(1));
            action_remap.put("actionType", rs.getString(2));
            action_remap.put("ResponsibleManager", rs.getString(3));
            action_remap.put("CreationDate", rs.getString(4));
            action_remap.put("Status", rs.getString(5));
            action_remap.put("Notes", rs.getString(6));
            action_map_array.add(action_remap);
        }
        return action_map_array;
    }
    catch(Exception exception)
    {
        action_map.put("Error", exception.toString());
        action_map_array.add(action_map);
        return action_map_array;
    }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/action/{id}", method = RequestMethod.DELETE)
    public String actionsRequestDelete(@PathVariable("id") String id)
    {  try {
        actions = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        String queryString = "delete from Actions where ActionId = " + id;
        stmt.executeUpdate(queryString);
        return "Successful Deletion of Row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/action/all")
    public ArrayList<HashMap<String, String>> actionsRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> action_map_array = new ArrayList();
        HashMap<String, String> action_map = new HashMap<String, String>();
        actions = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        ResultSet rs = stmt.executeQuery("select * from Actions");
        while(rs.next())
        {
            HashMap<String, String> action_remap = new HashMap<String, String>();
            action_remap.put("actionId", rs.getString(1));
            action_remap.put("actionType", rs.getString(2));
            action_remap.put("ResponsibleManager", rs.getString(3));
            action_remap.put("CreationDate", rs.getString(4));
            action_remap.put("Status", rs.getString(5));
            action_remap.put("Notes", rs.getString(6));
            action_map_array.add(action_remap);
        }
        return action_map_array;
    }
    catch(Exception exception)
    {
        action_map.put("Error", exception.toString());
        action_map_array.add(action_map);
        return action_map_array;
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

    public String getactionId() {
        return actionId;
    }

    public String getactionType() {
        return actionType;
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

    public void setactionId(String ActionId) {
        actionId = ActionId;
    }

    public void setactionType(String ActionType) {
        actionType = ActionType;
    }

    public void setactions(String actions){
        this.actions = actions;
    }

    public String getactions(){
        return actions;
    }
}