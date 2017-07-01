package JavaTS.models.validation;

import java.util.Arrays;

/**
 * Created by Tech on 04-Aug-15.
 */

public class IsInList extends Validation
{
    String[] list;
    public IsInList(String error_msg, String[] list)
    {
        super(error_msg);
        this.list = list;
    }

    @Override
    public boolean isValid(String text) 
    {        
        boolean result = Arrays.asList(list).contains(text.trim());
        
        if ( !result )
        {
            this.errorMsg = this.errorMsg.replace("{v}", text);
        }
        
        return result;
    }
}
