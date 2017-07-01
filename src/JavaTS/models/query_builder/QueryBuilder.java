/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.query_builder;

/**
 *
 * @author Tech
 */
public class QueryBuilder implements Cloneable
{
    private String table;
    private Field field, group, order;
    private Where where, having;
    private int start, limit;
    
    public QueryBuilder(String table)
    {
        this.table = table;
        start = limit = -1;
    }
    
    @Override
    public QueryBuilder clone() throws CloneNotSupportedException
    {
        return (QueryBuilder) super.clone();        
    }
    
    public void setField(Field field)
    {
        this.field = field;
    }
    
    public Field getField()
    {
        return this.field;
    }
    
    public void setGroup(Field group)
    {
        this.group = group;
    }
    
    public Field getGroup()
    {
        return this.group;
    }
    
    public void setOrder(Field order)
    {
        this.order = order;
    }
    
    public Field getOrder()
    {
        return this.order;
    }
    
    public void setWhere(Where where)
    {
        this.where = where;
    }
    
    public Where getWhere()
    {
        return this.where;
    }
    
    public void setHaving(Where where)
    {
        this.having = where;
    }
    
    public Where getHaving()
    {
        return this.having;
    }
        
    public void setStart(int start)
    {
        this.start = start;
    }
    
    public void setLimit(int limit)
    {
        this.limit = limit;
    }
    
    public String get()
    {
        String query = "SELECT ";
        
        if (field != null)
        {
            String f = field.get();            
            if (f != null && f.length() > 0 )
            {
                query += field.get();
            }
            else
            {
                query += "*";
            }
        }
        else
        {
            query += "*";
        }
        
        query += " FROM " + table;
                
        if (where != null)
        {
            String w = where.get();            
            if (w != null && w.length() > 0)
            {
                query += " WHERE " + w;
            }
        }
        
        if (group != null)
        {
            String g = group.get();
            if (g != null && g.length() > 0)
            {
                query += " GROUP BY " + g;
            }
        }
        
        if (having != null)
        {
            String h = having.get();
            if (h != null && h.length() > 0)
            {
                query += " HAVING " + h;
            }
        }
        
        if (order != null)
        {
            String o = having.get();
            if (o != null && o.length() > 0)
            {
                query += " ORDER BY " + o;
            }
        }
        
        if (start >= 0 && limit > 0)
        {
            query += " LIMIT " + start + "," + limit;
        }
        else if (limit > 0)
        {
            query += " LIMIT " + limit;
        }
        
        return query;
    }
}
