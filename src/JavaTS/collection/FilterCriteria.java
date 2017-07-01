/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.collection;

import java.util.HashMap;

/**
 *
 * @author Tech
 */
public class FilterCriteria 
{
    public HashMap<String, String> list;
    
    public FilterCriteria()
    {
        list = new HashMap<>();
    }
    
    public static FilterCriteria using ()
    {
        return new FilterCriteria();
    }
    
    public void set (HashMap<String, String> list)
    {
        this.list = list;
    }
    
    public void add(String field, String value)
    {
        list.put(field, value);
    }
}
