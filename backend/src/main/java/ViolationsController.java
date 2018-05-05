package backend;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.*;

@RestController
public class ViolationsController {
   private String violations = "";


    @RequestMapping(value = "/violation/{id}")
    public String ViolationsRequest(@PathVariable("id") String id)
    {  try {
        violations = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        String queryString = "select * from Violations where ViolationId = " + id;
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
            violations += ("Violation ID: " + rs.getString(1) + "; ");
            violations += ("Violation Type: " + rs.getString(2) + "; ");
            violations += ("Member Address: " + rs.getString(3) + "; ");
            violations += ("Responsible Manager: " + rs.getString(4) + "; ");
            violations += ("Creation Date: " + rs.getString(5) + "; ");
            violations += ("Fine: " + rs.getString(6) + "; ");
            violations += ("Status: " + rs.getString(7) + "; ");
            violations += ("Notes: " + rs.getString(8) + "; ");
            violations += "\n ";
        return violations;
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

    @RequestMapping(value = "/violation/all")
    public String ViolationsRequestAll()
    {  try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://backend-test.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/violations", "test", "testtest");
        Statement stmt=con.createStatement();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ViolationsController obj = (ViolationsController) context.getBean("ViolationBean");
        ResultSet rs = stmt.executeQuery("select * from Violations");
        while(rs.next())
        {
            violations += ("Violation ID: " + rs.getString(1) + "; ");
            violations += ("Violation Type: " + rs.getString(2) + "; ");
            violations += ("Member Address: " + rs.getString(3) + "; ");
            violations += ("Responsible Manager: " + rs.getString(4) + "; ");
            violations += ("Creation Date: " + rs.getString(5) + "; ");
            violations += ("Fine: " + rs.getString(6) + "; ");
            violations += ("Status: " + rs.getString(7) + "; ");
            violations += ("Notes: " + rs.getString(8) + "; ");
            violations += "\n ";
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
    }

    @RequestMapping(value = "/violation/add", method = POST)
    @ResponseBody
    public String ViolationsRequest() String id)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        StatusController obj = (StatusController) context.getBean("ViolationBean");
        return obj.getStatus();
    }*/

    public void setViolations(String violations){
        this.violations = violations;
    }
    public String getViolations(){
        return violations;
    }
}