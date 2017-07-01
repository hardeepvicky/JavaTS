/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.query_builder;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 *
 * @author Tech
 */
public class Field {
    
    public ArrayList<String> list;
     
    public Field()
    {
        list = new ArrayList<>();
    }
    
    public static Field using()
    {
        return new Field();
    }
    
    public Field add(String name)
    {
        list.add(name);
        return this;
    }
    
    public String get()
    {
        StringJoiner joiner = new StringJoiner(",");
        
        list.forEach((s) -> {
            joiner.add(s);
        });
        
        return joiner.toString();
    }
}
