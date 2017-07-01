package JavaTS.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @created 01-07-2017
 * @author Tech
 */
public class Filter 
{
    HashMap<Integer, FilterCriteria> list;
    
    public Filter()
    {
        this.list = new HashMap<>();
    }
    
    public static Filter using ()
    {
        return new Filter();
    }
    
    public Filter add(Integer dim, FilterCriteria criteria)
    {
        list.put(dim, criteria);
        return this;
    }
    
    public LinkedHashMap get(LinkedHashMap records)
    {
        LinkedHashMap ret_records = new LinkedHashMap();
        
        for (Map.Entry<Integer, FilterCriteria> entry : list.entrySet())
        {
            Set set = records.entrySet();
            Iterator itr = set.iterator();

            while (itr.hasNext())
            {
                Map.Entry row = (Map.Entry) itr.next();
            }
        }
        
        return ret_records;
    }    
}