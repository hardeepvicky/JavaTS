/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import JavaTS.config.Constant;

/**
 *
 * @author Tech
 */
public class Mysql implements Datasource
{
    private Connection conn = null;
    private static Mysql instance;
    
    public Mysql(String server, String user, String pass, String db) throws Exception
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + db, user, pass);
        }
        catch (Exception e) 
        {
            throw e;
        }     
    }
    
    public static Mysql getInstance()
    {
        if (instance == null)
        {
            try
            {     
                instance = new Mysql(Constant.DataSource.Mysql.SERVER, Constant.DataSource.Mysql.USER, Constant.DataSource.Mysql.PASS, Constant.DataSource.Mysql.DB);
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        
        return instance;
    }
    
    @Override
    public LinkedHashMap select(String q) throws Exception
    {
        Statement st = conn.createStatement();

        if (Constant.DEBUG)
        {
            System.out.println(q);
        }
         
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(q);

        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount(); //number of column

        LinkedHashMap data = new LinkedHashMap();

        int a = 0;
        while (rs.next())
        {
            LinkedHashMap row = new LinkedHashMap();

            for (int i = 1; i <= count; i++)
            {
                row.put(metaData.getColumnName(i), rs.getString(i));
            }

            data.put(a, row);
            a++;
        }

        return data;
       
    }
    
    @Override
    public boolean query(String q) throws Exception
    {
        if (Constant.DEBUG)
        {
            System.out.println(q);
        }
        
        Statement st = conn.createStatement();      
        return st.execute(q);       
    }
    
    public int insertOrUpdate(String q) throws Exception
    {
        if (Constant.DEBUG)
        {
            System.out.println(q);
        }
        
        Statement st = conn.createStatement();      
        return st.executeUpdate(q); 
    }
}
