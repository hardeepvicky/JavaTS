/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Tech
 */
public class Intersection 
{
    public LinkedHashMap main, other;
    
    public interface callback
    {
        public void onMatch(LinkedHashMap main, LinkedHashMap other, String mainIndex, String otherIndex);
    }
    
    public Intersection(LinkedHashMap main, LinkedHashMap other)
    {
        this.main = main;
        this.other = other;
    }
    
    public static Intersection using(LinkedHashMap main, LinkedHashMap other)
    {
        return new Intersection(main, other);
    }
    
    public void match(String mainKey, String otherKey, Intersection.callback callbk)
    {
        for (Iterator mainIt = main.entrySet().iterator(); mainIt.hasNext();) 
        {
            Map.Entry<Object, Object> mainEntry = (Map.Entry<Object, Object>) mainIt.next();
            
            String main_index = String.valueOf(mainEntry.getKey());
            LinkedHashMap main_record = (LinkedHashMap) mainEntry.getValue();
            
            if (main_record.containsKey(mainKey))
            {
                String main_key_value = String.valueOf(main_record.get(mainKey));
                
                if (main_key_value != null)
                {
                    for (Iterator it = other.entrySet().iterator(); it.hasNext();) 
                    {
                        Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();

                        String other_index = String.valueOf(entry.getKey());
                        LinkedHashMap other_record = (LinkedHashMap) entry.getValue();

                        if (other_record.containsKey(otherKey))
                        {
                            String other_key_value = String.valueOf(other_record.get(otherKey));

                            if (other_key_value != null && other_key_value.equals(main_key_value))
                            {
                                callbk.onMatch(main_record, other_record, main_index, other_index); 
                            }
                        }
                    }
                }
            }
        }
    }
}
