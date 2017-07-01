package JavaTS.config;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @created 28-06-2017
 * @author Tech
 */ 
public class Constant 
{
	public static boolean DEBUG = true;

    public static class Date
    {
        public static String FORMAT = "dd-MMM-yyyy";
        public static String SQL_FORMAT = "yyyy-MM-dd";
        public static String WEB_SERVICE_FORMAT = "yyyy-MM-dd";
    }

    public static class Datetime
    {
        public static String FORMAT = "dd-MM-yyyy hh:mm a";
        public static String SQL_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public static String WEB_SERVICE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    public static class Time
    {
        public static String FORMAT = "hh:mm a";
        public static String SQL_FORMAT = "HH:mm:ss";
        public static String WEB_SERVICE_FORMAT = "HH:mm:ss";
    }
    
    public static class DataSource
    {
        public static class Mysql
        {
            public static String SERVER = "locahost";
            public static String DB = "";
            public static String USER = "";
            public static String PASS = "";
        }
    }
    
    public static void load(String file) throws Exception
    {
        FileInputStream fis = new FileInputStream(file);
      
        Properties property = new Properties();
        property.load(fis);
        
        
        String p = property.getProperty("DEBUG".toLowerCase());
        if (p != null)
        {
            DEBUG = "TRUE".equals(p.toUpperCase());
        }
        
        /**----------------------- Date --------------------------*/
        p = property.getProperty("Date.FORMAT".toLowerCase());
        if (p != null)
        {
            Date.FORMAT = p;
        }
        
        p = property.getProperty("Date.SQL_FORMAT".toLowerCase());
        if (p != null)
        {
            Date.SQL_FORMAT = p;
        }
        
        p = property.getProperty("Date.WEB_SERVICE_FORMAT".toLowerCase());
        if (p != null)
        {
            Date.WEB_SERVICE_FORMAT = p;
        }
        
        /**----------------------- DateTime --------------------------*/
        p = property.getProperty("Datetime.FORMAT".toLowerCase());
        if (p != null)
        {
            Datetime.FORMAT = p;
        }
        
        p = property.getProperty("Datetime.SQL_FORMAT".toLowerCase());
        if (p != null)
        {
            Datetime.SQL_FORMAT = p;
        }
        
        p = property.getProperty("Datetime.WEB_SERVICE_FORMAT".toLowerCase());
        if (p != null)
        {
            Datetime.WEB_SERVICE_FORMAT = p;
        }
        
        /**----------------------- Time --------------------------*/
        p = property.getProperty("Time.FORMAT".toLowerCase());
        if (p != null)
        {
            Time.FORMAT = p;
        }
        
        p = property.getProperty("Time.SQL_FORMAT".toLowerCase());
        if (p != null)
        {
            Time.SQL_FORMAT = p;
        }
        
        p = property.getProperty("Time.WEB_SERVICE_FORMAT".toLowerCase());
        if (p != null)
        {
            Time.WEB_SERVICE_FORMAT = p;
        }
        
        /**----------------------- Data Source --------------------------*/
        p = property.getProperty("DataSource.Mysql.SERVER".toLowerCase());
        if (p != null)
        {
            DataSource.Mysql.SERVER = p;
        }
        
        p = property.getProperty("DataSource.Mysql.DB".toLowerCase());
        if (p != null)
        {
            DataSource.Mysql.DB = p;
        }
        
        p = property.getProperty("DataSource.Mysql.USER".toLowerCase());
        if (p != null)
        {
            DataSource.Mysql.USER = p;
        }
        
        p = property.getProperty("DataSource.Mysql.PASS".toLowerCase());
        if (p != null)
        {
            DataSource.Mysql.PASS = p;
        }
    }
}
