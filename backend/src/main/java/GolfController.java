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
public class GolfController {
    private ArrayList<HashMap<String, String>> golf_map_array = new ArrayList();
    private HashMap<String, String> golf_map = new HashMap<String, String>();
    private String ownerAddress = null;
    private String cartMake = null;
    private String cartModel = null;
    private String labelNumber = null;
    private String serialNumber = null;
    private String cartId = null;
    private String manager = null;
    private Connection con = null;
    private Connection cons = null;
    private Connection con2 = null;

    private void setOwnerAddress(String ownerAddress)
    {
        this.ownerAddress = ownerAddress;
    }

    private void setCartMake(String cartMake)
    {
        this.cartMake = cartMake;
    }

    private void setCartModel(String cartModel)
    {
        this.cartModel = cartModel;
    }

    private void setLabelNumber(String labelNumber)
    {
        this.labelNumber = labelNumber;
    }

    private void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    private void setCartId(String cartId)
    {
        this.cartId = cartId;
    }

    private String getOwnerAddress()
    {
        return this.ownerAddress;
    }

    private String getCartMake()
    {
        return this.cartMake;
    }

    private String getCartModel()
    {
        return this.cartModel;
    }

    private String getLabelNumber()
    {
        return this.labelNumber;
    }

    private String getSerialNumber()
    {
        return this.serialNumber;
    }

    private String getCartId()
    {
        return this.cartId;
    }

    private String getManager()
    {
        return this.manager;
    }

    private void setManager(String manager)
    {
        this.manager = manager;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    public HashMap<String, String> addCart (@RequestBody HashMap<String, String> cart_list)
    {  try {
        this.setOwnerAddress(cart_list.get("ownerAddress"));
        this.setCartMake(cart_list.get("cartMake"));
        this.setCartModel(cart_list.get("cartModel"));
        this.setSerialNumber(cart_list.get("serialNumber"));
        this.setLabelNumber(cart_list.get("labelNumber"));

        HashMap<String, String> member_remap = new HashMap<String, String>();

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/carts", "test", "testtest");
        Statement stmt = con.createStatement();

        String queryString = "insert into Carts(ownerAddress, cartMake, cartModel, serialNumber, labelNumber)  values ('" + this.getOwnerAddress() + "', '" + this.getCartMake() +  "', '" + this.getCartModel() + "', '" + this.getSerialNumber() + "', '" + this.getLabelNumber() + "')";
        stmt.executeUpdate(queryString);

        HashMap<String, String> cart_maps = new HashMap<String, String>();
        cart_maps.put("Success", "Cart added succesfully!");
        con.close();
        return cart_maps;
    }
    catch(Exception exception)
    {
        HashMap<String, String> error_map = new HashMap<String, String>();
        error_map.put("Error", "Row not created: "+exception);
        return error_map;
    }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/cart/all")
    public ArrayList<HashMap<String, String>> cartsRequestAll()
    {  try {
        ArrayList<HashMap<String, String>> cart_array = new ArrayList();
        HashMap<String, String> cart_map = new HashMap<String, String>();
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/carts", "test", "testtest");
        Statement stmt=con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Carts");
        while(rs.next())
        {
            HashMap<String, String> cart_remap = new HashMap<String, String>();
            cart_remap.put("ownerAddress", rs.getString(1));
            cart_remap.put("cartMake", rs.getString(2));
            cart_remap.put("cartModel", rs.getString(3));
            cart_remap.put("serialNumber", rs.getString(4));
            cart_remap.put("labelNumber", rs.getString(5));
            cart_array.add(cart_remap);
        }
        con.close();
        return cart_array;
    }
    catch(Exception exception)
    {
        ArrayList<HashMap<String, String>> error_array = new ArrayList();
        HashMap<String, String> error_map = new HashMap<String, String>();

        error_map.put("Error", exception.toString());
        error_array.add(error_map);
        return error_array;
    }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/cart/delete", method = RequestMethod.POST)
    public String GuestsRequestDelete(@RequestBody HashMap<String, String> serialNumber_map)
    {  try {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://homes-ltoa-database.cebbknh24dty.us-west-2.rds.amazonaws.com/carts", "test", "testtest");
        Statement stmt=con.createStatement();
        String queryString = "delete from Carts where serialNumber = '" + serialNumber_map.get("serialNumber") + "'";
        stmt.executeUpdate(queryString);
        con.close();
        return "Successful Deletion of Row";
    }
    catch(Exception exception)
    {
        return exception.toString();
    }
    }

}