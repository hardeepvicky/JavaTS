package JavaTS.models.validation;

import java.util.ArrayList;
import java.util.Date;
import JavaTS.collection.DateTime;

/**
 * Created by Tech on 04-Aug-15.
 */

public class IsDateTime extends Validation
{
    private ArrayList<String> formatList;
    
    public IsDateTime(String error_msg, ArrayList<String> format_list)
    {
        super(error_msg);
        this.formatList = format_list;
    }
    
    public IsDateTime(String error_msg, String format)
    {
        super(error_msg);
        this.formatList = new ArrayList<>();
        this.formatList.add(format);
    }

    @Override
    public boolean isValid(String text) 
    {
        Date date = DateTime.get(text.trim(), formatList);
        boolean result = date != null;
        
        if ( !result )
        {
            this.errorMsg = this.errorMsg.replace("{v}", text);
        }
        
        return result;
    }
}
