/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.datasource;

import java.util.LinkedHashMap;

/**
 *
 * @author Tech
 */
public interface Datasource 
{
    public LinkedHashMap select(String q) throws Exception;
    public boolean query(String q) throws Exception;
}
