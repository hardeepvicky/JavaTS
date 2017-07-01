package JavaTS.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @created 01-07-2017
 * @author Tech
 */
public class Extract 
{
    private static LinkedHashMap result;
    public static LinkedHashMap get(LinkedHashMap records, String key, String value, int dim)
    {
        result = new LinkedHashMap();
        
        _get(records, key, value, dim);
        
        return result;
    }
    
    private static void _get(LinkedHashMap records, String key, String value, int dim)
    {
        for (Iterator it = records.entrySet().iterator(); it.hasNext();) 
        {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
            
            Object obj = (Object) entry.getValue();
            
            if (obj instanceof LinkedHashMap)
            {
                LinkedHashMap record = (LinkedHashMap) obj;
                
                if(dim == 2)
                {
                    String k;

                    if (record.containsKey(key))
                    {
                        k = String.valueOf(record.get(key));
                    }
                    else
                    {
                        k = String.valueOf(result.size());
                    }

                    Object v = null;
                    
                    if (record.containsKey(value))
                    {   
                        v = record.get(value);                        
                    }
                    
                    result.put(k, v);
                }
                else
                {
                    _get((LinkedHashMap) obj, key, value, dim - 1);
                }
            }
        }
    }
    
    public static LinkedHashMap get(LinkedHashMap records, int dim)
    {
        result = new LinkedHashMap();
        
        _get(records, dim, 0);
        
        return result;
    }
    
    private static void _get(LinkedHashMap records, int dim, int dimCount)
    {
        for (Iterator it = records.entrySet().iterator(); it.hasNext();) 
        {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
            
            Object obj = (Object) entry.getValue();
            
            if(dim == dimCount)
            {
                result.put(result.size(), obj);
            }
            else if (obj instanceof LinkedHashMap)
            {
                _get((LinkedHashMap) obj, dim, dimCount);                
            }
        }
    }
}
