/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.validation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Tech
 */
public class Validator 
{
    public HashMap<String, Validation> validationList;
    public HashMap<String, String> errors;
    
    public Validator()
    {
        validationList = new HashMap<>();
    }
    
    public static Validator using ()
    {
        return new Validator();
    }
    
    public void add(String field, Validation v)
    {
        validationList.put(field, v);
    }
    
    public boolean isValid(LinkedHashMap record)
    {
        this.errors = new HashMap<>();
                
        for (Map.Entry<String, Validation> entry : validationList.entrySet())
        {
            String f = entry.getKey();
            Validation validation = entry.getValue();
            
            if (record.containsKey(f))
            {
                Object val = record.get(f);
                if (val instanceof String)
                {
                    if (!validation.isValid( (String) val) )
                    {
                        invalidate(f, validation.getMessage());
                    }
                }
            }
        }
        
        return this.errors.isEmpty();
    }
    
    public void invalidate(String f, String msg)
    {
        this.errors.put(f, msg);
    }
    
    public LinkedHashMap getValidationErrors()
    {
        LinkedHashMap data = new LinkedHashMap<>();
        
        for (Map.Entry<String, String> entry : errors.entrySet())
        {
            String f = entry.getKey();
            
            LinkedHashMap old_errors = new LinkedHashMap();
            
            if (data.containsKey(f))
            {
                old_errors = (LinkedHashMap) data.get(f);
            }
                
            old_errors.put(old_errors.size(), entry.getValue());
            
            data.put(f, old_errors);
        }
        
        return data;
    }
}
