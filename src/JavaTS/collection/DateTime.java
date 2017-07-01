/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.collection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Tech
 */
public class DateTime 
{   
    public static Date get()
    {
        return new Date();
    }
    
    public static Date get(String str, String in_format)
    {
        Date date = new Date();
                
        if (str != null && in_format != null)
        {
            SimpleDateFormat inFormat = new SimpleDateFormat(in_format);
            try
            {
                date = inFormat.parse(str);                
            }
            catch (ParseException e)
            {
                System.err.println(e.getMessage());
                return null;
            }
        }     
        
        return date;
    }
    
    public static Date get(String str, ArrayList<String> format_list)
    {
        Date date = null;
        
        for (String format : format_list)
        {
            date = get(str, format);
            
            if (date != null)
            {
                return date;
            }
        }
        
        return date;
    }
    
    public static String format(Date date, String format)
    {
        try
        {
            SimpleDateFormat outFormat = new SimpleDateFormat(format);
            return outFormat.format(date);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());            
        }

        return null;
    }
}
