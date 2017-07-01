/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.converter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Tech
 */
public class Converter 
{
    public HashMap<String, ConverterInterface> converterList;
    
    public Converter ()
    {
        converterList = new HashMap<>();
    }
    
    public static Converter using()
    {
        return new Converter();
    }
    
    public Converter add(String field, ConverterInterface c)
    {
        converterList.put(field, c);
        return this;
    }
    
    public void convert(LinkedHashMap record)
    {
        for (Map.Entry<String, ConverterInterface> entry : converterList.entrySet())
        {
            String f = entry.getKey();
            ConverterInterface converter = entry.getValue();
            
            if (record.containsKey(f))
            {
                converter.convert(record, f);
            }
        }
    }
}
