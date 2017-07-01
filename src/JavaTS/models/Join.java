/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models;

import JavaTS.models.query_builder.QueryBuilder;

/**
 *
 * @author Tech
 */
public class Join 
{
    public Model model;
    public String key;
    QueryBuilder queryBuilder;
    
    public Join(String key, Model model)
    {
        this(key, model, new QueryBuilder(model.getTableName()));
    }
    
    public Join(String key, Model model, QueryBuilder queryBuilder)
    {
        this.key = key;
        this.model = model;
        this.queryBuilder = queryBuilder;
    }
}
