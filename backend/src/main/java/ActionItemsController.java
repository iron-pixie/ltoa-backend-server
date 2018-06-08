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
import java.util.Date;
import java.util.ArrayList;
import java.util.*;
import java.time.LocalDateTime;

@RestController
public class ActionItemsController {
    private ArrayList<HashMap<String, String>> action_map_array = new ArrayList();
    private HashMap<String, String> action_map = new HashMap<String, String>();
    private String actions = "";
    private String ActionId;
    private String ActionType;
    private String ResponsibleManager;
    private String CreationDate;
    private String Status;
    private String Notes;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();


    @RequestMapping(value = "/action/{id}")
    public HashMap<String, String> ActionsRequest(@PathVariable("id") String id)
    {  try {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        String queryString = "select * from Actions where ActionId = " + id;
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        action_map.put("ActionId", rs.getString(1));
        action_map.put("ActionType", rs.getString(2));
        action_map.put("ResponsibleManager", rs.getString(4));
        action_map.put("CreationDate", rs.getString(5));
        action_map.put("Status", rs.getString(7));
        action_map.put("Notes", rs.getString(8));
        return action_map;
    }
    catch(Exception exception)
    {
        //return exception;
        return null;
    }
    }

    @RequestMapping(value = "/action/add", method = RequestMethod.POST)
    @ResponseBody
    public String ActionsRequestAdd(@RequestBody HashMap<String, String> actionList)
    {  try {
        this.setActionId(actionList.get("ActionId"));
        this.setActionType(actionList.get("ActionType"));
        this.setResponsibleManager(actionList.get("ResponsibleManager"));
        this.setCreationDate(dateFormat.format(date));
        this.setStatus(actionList.get("Status"));
        this.setNotes(actionList.get("Notes"));
        actions = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt = con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        String queryString = "insert into Actions(ActionId, ActionType, ResponsibleManager, CreationDate, Status, Notes)  values ('" + this.getActionId() + "', '" + this.getActionType() + "', '" + this.getResponsibleManager() + "', '" + this.getCreationDate() + "', '" + this.getStatus() + "', '" + this.getNotes() + "')";
        stmt.executeUpdate(queryString);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @RequestMapping(value = "/action/{id}", method = RequestMethod.DELETE)
    public String ActionssRequestDelete(@PathVariable("id") String id)
    {  try {
        actions = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
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

    @RequestMapping(value = "/action/all")
    public ArrayList<HashMap<String, String>> ActionsRequestAll()
    {  try {
        actions = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/actions", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ActionItemsController obj = (ActionItemsController) context.getBean("ActionBean");
        ResultSet rs = stmt.executeQuery("select * from Actions");
        while(rs.next())
        {
            HashMap<String, String> action_remap = new HashMap<String, String>();
            action_remap.put("ActionId", rs.getString(1));
            action_remap.put("ActionType", rs.getString(2));
            action_remap.put("ResponsibleManager", rs.getString(4));
            action_remap.put("CreationDate", rs.getString(5));
            action_remap.put("Status", rs.getString(7));
            action_remap.put("Notes", rs.getString(8));
            action_map_array.add(action_remap);
        }
        return action_map_array;
    }
    catch(Exception exception)
    {
        return null;
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

    public String getActionId() {
        return ActionId;
    }

    public String getActionType() {
        return ActionType;
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

    public void setActionId(String actionId) {
        ActionId = actionId;
    }

    public void setActionType(String actionType) {
        ActionType = actionType;
    }

    public void setActions(String actions){
        this.actions = actions;
    }

    public String getActions(){
        return actions;
    }
}