package JavaTS.models.validation;

/**
 * Created by Tech on 04-Aug-15.
 */

public class IsNumeric extends Validation
{
    public IsNumeric(String error_msg)
    {
        super(error_msg);
    }

    @Override
    public boolean isValid(String text) 
    {
        boolean result = text != null && text.matches("[-+]?\\d*\\.?\\d+");
        
        if ( !result )
        {
            this.errorMsg = this.errorMsg.replace("{v}", text);
        }
        
        return result;
    }
}
