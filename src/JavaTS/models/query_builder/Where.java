/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.query_builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

/**
 *
 * @author Tech
 */
public class Where 
{
    private String op;
    private HashMap<String, String> conditions;
    private ArrayList<Where> whereList;
    
    public Where(String op)
    {
        this.op = op;
        conditions = new HashMap<>();
        whereList = new ArrayList<>();
    }
    
    public static Where using (String op)
    {
        return new Where(op);
    }
    
    public Where add(String field, String value)
    {
        conditions.put(field, value);
        return this;
    }
    
    public Where add(String field, ArrayList<String> list)
    {
        conditions.put(field + " IN ", "(" + String.join(",", list) + ")");
        return this;
    }
    
    public Where add(String field, Object[] list)
    {
        ArrayList<String> arrList = new ArrayList<>();
                
        for (Object obj : list)
        {
            arrList.add( String.valueOf(obj) );
        }
        
        return add(field, arrList);
    }
    
    public Where add(Where where)
    {
        whereList.add(where);
        return this;
    }
    
    public String get()
    {
        String f, v;
        
        String[] op_list = {"=", ">", "<", "!", "IN", "in"};
        
        StringJoiner joiner = new StringJoiner(" " + op + " ");
        
        for (HashMap.Entry pair : conditions.entrySet()) 
        {
            f = (String) pair.getKey();
            v = (String) pair.getValue();
            
            boolean op_found = false;
            
            for (String s : op_list)
            {
                if (f.contains(s))
                {
                    op_found = true;
                }
            }
            
            if (!op_found)
            {
                f += " = ";
            }
            
            joiner.add(f + v);
        }
        
        whereList.forEach((where) -> 
        {
            joiner.add(where.get());
        });
        
        return "(" + joiner.toString() + ")";
    }
}
