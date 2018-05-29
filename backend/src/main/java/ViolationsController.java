package backend;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;

@RestController
public class ViolationsController {
    private HashMap<String, String> violation_map = new HashMap<>();
    private String violations = "";
    private String ViolationId;
    private String ViolationType;
    private String MemberAddress;
    private String ResponsibleManager;
    private String CreationDate;
    private String Fine;
    private String Status;
    private String Notes;


    @RequestMapping(value = "/violation/{id}")
    public HashMap<String, String> ViolationsRequest(@PathVariable("id") String id)
    {  try {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/violations", "test", "testtest");
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
        return violation_map;
    }
    catch(Exception exception)
    {
        //return exception;
        return null;
    }
    }

    @RequestMapping(value = "/violation/add", method = RequestMethod.POST)
    @ResponseBody
    public String ViolationsRequestAdd(@RequestBody HashMap violationList)
    {  try {
        String[] violationArray = new String[8];
        for(int i = 0; i < 8; i++)
        {
            violationArray[i] = "";
        }
        int string_counter = 0;
        if(violationList != null)
        {
            for(int i = 0; i < violationList.length(); i++)
            {
                if(violationList.charAt(i) != ';') {
                    violationArray[string_counter] += Character.toString(violationList.charAt(i));
                }
                else
                {
                    string_counter++;
                }
            }
                this.setViolationId(violationArray[0]);
                this.setViolationType(violationArray[1]);
                this.setMemberAddress(violationArray[2]);
                this.setResponsibleManager(violationArray[3]);
                this.setCreationDate(violationArray[4]);
                this.setFine(violationArray[5]);
                this.setStatus(violationArray[6]);
                this.setNotes(violationArray[7]);
        }
        violations = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        String queryString = "insert into Violations(ViolationId, ViolationType, MemberAddress, ResponsibleManager, CreationDate, Fine, Status, Notes)  values ('" + this.getViolationId() + "', '" + this.getViolationType() + "', '" + this.getMemberAddress() + "', '" + this.getResponsibleManager() + "', '" + this.getCreationDate() + "', '" + this.getFine() + "', '" + this.getStatus() + "', '" + this.getNotes()+ "')";
        stmt.executeUpdate(queryString);
        return "Successful addition of row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @RequestMapping(value = "/violation/{id}", method = RequestMethod.DELETE)
    public String ViolationsRequestDelete(@PathVariable("id") String id)
    {  try {
        violations = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        String queryString = "delete from Violations where ViolationId = " + id;
        stmt.executeUpdate(queryString);
        return "Successful Deletion of Row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @RequestMapping(value = "/violation/all")
    public String ViolationsRequestAll()
    {  try {
        violations = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        ResultSet rs = stmt.executeQuery("select * from Violations");
        while(rs.next())
        {
            violation_map.put("ViolationId", rs.getString(1));
            violation_map.put("ViolationType", rs.getString(2));
            violation_map.put("MemberAddress", rs.getString(3));
            violation_map.put("ResponsibleManager", rs.getString(4));
            violation_map.put("CreationDate", rs.getString(5));
            violation_map.put("Fine", rs.getString(6));
            violation_map.put("Status", rs.getString(7));
            violation_map.put("Notes", rs.getString(8));
        }
        return violations;
    }
    catch(Exception exception)
        {
            return exception.toString();
        }
    }

   /* @RequestMapping(value = "/violation/delete/{id}", method = GET)
    public String ViolationsRequest(@PathVariable("id") String id)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        StatusController obj = (StatusController) context.getBean("ViolationBean");
        return obj.getStatus();
    }*/

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