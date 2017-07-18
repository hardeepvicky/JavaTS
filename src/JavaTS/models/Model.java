/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import JavaTS.collection.DateTime;
import JavaTS.collection.Extract;
import JavaTS.collection.Intersection;

import JavaTS.models.converter.Converter;
import JavaTS.models.datasource.Mysql;
import JavaTS.models.query_builder.QueryBuilder;
import JavaTS.models.query_builder.Where;
import JavaTS.models.validation.Validator;
import java.util.TreeSet;

/**
 *
 * @author Tech
 */
public abstract class Model 
{
    protected String FIELD_PRIMARY = "id";
    protected String FIELD_CREATED = "created";
    protected String FIELD_MODIFIED = "modified";
    
    protected String table;
    protected LinkedHashMap Record;
    public int ID;
    
    protected Mysql mysql;
    
    protected Validator validator;
    protected Converter converterBeforeValidate, converterAfterSelect;
    
    protected ArrayList<Join> children, parents;
    
    public Model(String table)
    {
        this.table = table;
        this.ID = 0;
        this.parents = new ArrayList<>();
        this.children = new ArrayList<>();
        
        mysql = Mysql.getInstance();
    }
    
    public String getTableName()
    {
        return table;
    }
    
    protected boolean beforeSave()
    {
        if (this.ID == 0)
        {
            if (FIELD_CREATED != null && FIELD_CREATED != "" && !this.Record.containsKey(FIELD_CREATED))
            {
                Record.put(FIELD_CREATED, DateTime.format(DateTime.get(), "yyyy-MM-dd HH:mm:ss"));
            }
        }
        
        if (FIELD_MODIFIED != null && FIELD_MODIFIED != "" && !this.Record.containsKey(FIELD_MODIFIED))
        {
            Record.put(FIELD_MODIFIED, DateTime.format(DateTime.get(), "yyyy-MM-dd HH:mm:ss"));
        }
        
        
        return true;
    }
    
    public void convertBeforeValidate(LinkedHashMap record)
    {
        if (converterBeforeValidate != null)
        {
            converterBeforeValidate.convert(record);
        }
    }
    
    public boolean validate(LinkedHashMap record)
    {
        if (validator != null)
        {
            return validator.isValid(record);
        }
        
        return true;
    }
    
    public LinkedHashMap getValidationErrors()
    {
        if (validator == null)
        {
            return new LinkedHashMap();
        }
        
        LinkedHashMap data = new LinkedHashMap<>();
        
        for (Map.Entry<String, String> entry : validator.errors.entrySet())
        {
            data.put(entry.getKey(), entry.getValue());
        }
        
        return data;
    }
    
    public boolean save(LinkedHashMap record, boolean callback) throws Exception
    {
        if (record.containsKey(FIELD_PRIMARY))
        {
            ID = Integer.parseInt( (String) record.get(FIELD_PRIMARY));
            update(record, callback);
        }
        else if (ID > 0)
        {
            update(record, callback);
        }
        else
        {
            insert(record, callback);
        }
        
        return true;
    }
    
    protected boolean insert(LinkedHashMap record, boolean callback) throws Exception
    {
        this.Record = record;

        if (callback) 
        {
            convertBeforeValidate(this.Record);
            
            if (!this.validate(this.Record))
            {
                return false;
            }
            
            if (!this.beforeSave()) 
            {
                return false;
            }
        }

        String q = "INSERT INTO " + table;
        String q_fields = "", q_values = "";

        Set set = Record.entrySet();

        Iterator itr = set.iterator();

        while (itr.hasNext())
        {
            Map.Entry row = (Map.Entry) itr.next();

            q_fields += row.getKey().toString() + ",";
            q_values += "'" + sanitize(row.getValue().toString()) + "',";

        }

        if (q_fields.length() == 0) {
            return false;
        }

        q_fields = q_fields.substring(0, q_fields.length() - 1);
        q_values = q_values.substring(0, q_values.length() - 1);

        q += "(" + q_fields + ")VALUES(" + q_values + ")";

        int affectedRows = mysql.insertOrUpdate(q);
        if (affectedRows == 0)
        {
            return false;
        }

        if (callback)
        {
            ID = getLastInsertId();
            
            afterInsert();
        }

        return true;
    }
    
    public boolean insertMany(LinkedHashMap records) throws Exception
    {
        ArrayList<String> fieldList = new ArrayList<>();
        StringJoiner fields = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");
        
        Set set = records.entrySet();
        Iterator itr = set.iterator();

        int row_count = 0;
        while(itr.hasNext())
        {
            Map.Entry record_row = (Map.Entry)itr.next();
            
            LinkedHashMap record = (LinkedHashMap) record_row.getValue();
            
            if (row_count == 0)
            {
                Set record_set = record.entrySet();
                Iterator record_itr = record_set.iterator();
                while (record_itr.hasNext())
                {
                    Map.Entry row = (Map.Entry) record_itr.next();
                    String f = String.valueOf(row.getKey());
                    fieldList.add( f );
                    fields.add( f );
                }
            }
            
            StringJoiner vals = new StringJoiner(",");
            
            for (String f : fieldList)
            {
                String v = "";
                if (record.containsKey(f))
                {
                    v = sanitize( String.valueOf(record.get(f)));
                }
                
                vals.add("'" + v + "'");
            }
            
            values.add( "(" + vals.toString() + ")");
            
            row_count++;
        }

        if (fieldList.isEmpty()) 
        {
            return false;
        }

        String q = "INSERT INTO " + table + "(" + fields.toString() + ")VALUES" + values.toString();
                
        int affectedRows = mysql.insertOrUpdate(q);

        return affectedRows == row_count;
    }

    public void afterInsert()
    {
    }
    
    public int getLastInsertId() throws Exception
    {
        String q = "select last_insert_id() as last_id from " + table;

        LinkedHashMap records = mysql.select(q);

        LinkedHashMap record = (LinkedHashMap) records.get(0);

        return Integer.parseInt((String) record.get("last_id"));
    }

    public boolean update(LinkedHashMap record, String where, boolean callback)  throws Exception
    {
        Record = record;

        String values = "";

        Set set = Record.entrySet();
        Iterator itr = set.iterator();

        while(itr.hasNext())
        {
            Map.Entry row = (Map.Entry)itr.next();

            values += row.getKey().toString() + "=" + "'" + sanitize(row.getValue().toString()) + "',";
        }

        if (values.length() == 0)
        {
            return false;
        }

        values = values.substring(0, values.length() - 1);

        String q = "UPDATE " + table + " SET " + values + where;

        int affectedRows = mysql.insertOrUpdate(q);
        return affectedRows > 0;
    }

    protected boolean update(LinkedHashMap record, boolean callback) throws Exception
    {
        this.Record = record;
        
        if(callback)
        {
            convertBeforeValidate(this.Record);
            
            if (!this.validate(this.Record))
            {
                return false;
            }
            
            if (!beforeSave())
            {
                return false;
            }
        }
                
        String where = " WHERE id=" + ID;

        update(record, where, callback);

        if(callback)
        {
            afterUpdate();
        }

        return true;
    }
    
    public void afterUpdate()
    {
    }
    
    /*-------------------------- Start ---------------------------------------*/
    /*-------------------------- Select --------------------------------------*/
    /*------------------------------------------------------------------------*/
    public Model addParent(Join join)
    {
        this.parents.add(join);
        return this;
    }
    
    public Model addChild(Join join)
    {
        this.children.add(join);
        return this;
    }
    public void beforeSelect(QueryBuilder query)
    {
        
    }
    
    public LinkedHashMap select(String q) throws Exception
    {
        LinkedHashMap data = mysql.select(q);        
        return data;
    }
    
    public LinkedHashMap select(QueryBuilder query, boolean callback) throws Exception
    {
        if (callback)
        {
            beforeSelect(query);
        }
        
        LinkedHashMap data = mysql.select(query.get()); 
        selectAssociate(data);
        
        if (callback)
        {
            afterSelect(data);
            convertAfterSelect(data);
        }
        
        return data;
    }
    
    public void selectAssociate(LinkedHashMap records) throws Exception
    {        
        if (parents.isEmpty() && children.isEmpty())
        {
            return;
        }
        
        LinkedHashMap parent_set = new LinkedHashMap();
        
        for (Join join : parents)
        {
            String foreginKey = join.key;
            
            LinkedHashMap field_data = Extract.using().get(records, this.FIELD_PRIMARY, foreginKey, 2);
            TreeSet<String> id_list = new TreeSet<>(field_data.values());            
            
            QueryBuilder qB = join.queryBuilder.clone();
            Where where = qB.getWhere();
            if (where == null)
            {
                where = new Where("AND");
            }
            
            where.add(join.model.FIELD_PRIMARY, id_list.toArray());
            qB.setWhere(where);
            
            LinkedHashMap related_records = join.model.select(qB, true);
            
            Intersection intersection = Intersection.using(records, related_records);
            intersection.match(join.key, join.model.FIELD_PRIMARY, new Intersection.callback() 
            {
                @Override
                public void onMatch(LinkedHashMap main, LinkedHashMap other, String mainIndex, String otherIndex) 
                {
                    LinkedHashMap parent_data = new LinkedHashMap();
                    if (main.containsKey("parents"))
                    {
                        parent_data = (LinkedHashMap) main.get("parents");
                    }
                    
                    parent_data.put(join.model.getTableName(), other);  
                    main.put("parents", parent_data);
                }
            });
        }
        
        for (Join join : children)
        {
            String foreginKey = join.key;
            
            LinkedHashMap field_data = Extract.using().get(records, "", this.FIELD_PRIMARY, 2);
            TreeSet<String> id_list = new TreeSet<>(field_data.values());            
            
            QueryBuilder qB = join.queryBuilder.clone();
            Where where = qB.getWhere();
            if (where == null)
            {
                where = new Where("AND");
            }
            
            where.add(foreginKey, id_list.toArray());
            qB.setWhere(where);
            
            LinkedHashMap related_records = join.model.select(qB, true);
            
            Intersection intersection = Intersection.using(records, related_records);
            intersection.match(this.FIELD_PRIMARY, foreginKey, new Intersection.callback() 
            {
                @Override
                public void onMatch(LinkedHashMap main, LinkedHashMap other, String mainIndex, String otherIndex) 
                {
                    LinkedHashMap children_data = new LinkedHashMap();
                    LinkedHashMap old_records = new LinkedHashMap();
                    String tablename = join.model.getTableName();
                    if (main.containsKey("children"))
                    {
                        children_data = (LinkedHashMap) main.get("children");
                        
                        if (children_data.containsKey(tablename))
                        {
                            old_records = (LinkedHashMap) children_data.get(tablename);
                        }
                    }
                    
                    old_records.put(old_records.size(), other);
                    children_data.put(tablename, old_records);  
                    main.put("children", children_data);
                }
            });
        }
    }
    
    public void afterSelect(LinkedHashMap data)
    {
    }
    
    public void convertAfterSelect(LinkedHashMap data)
    {
        if (converterAfterSelect != null)
        {
            Set set = data.entrySet();
            Iterator itr = set.iterator();

            while(itr.hasNext())
            {
                Map.Entry row = (Map.Entry)itr.next();
                
                converterAfterSelect.convert( (LinkedHashMap) row.getValue() );
            }
        }
    }
    
    /*-------------------------- Select --------------------------------------*/
    /*--------------------------- END ----------------------------------------*/
    /*------------------------------------------------------------------------*/
    
    
    
    public String sanitize(String str)
    {
        return str.replaceAll("[']", "");
    }
}
