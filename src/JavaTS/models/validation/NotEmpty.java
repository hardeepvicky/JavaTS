package JavaTS.models.validation;

/**
 * Created by Tech on 04-Aug-15.
 */

public class NotEmpty extends Validation
{
    public NotEmpty(String error_msg)
    {
        super(error_msg);
    }

    @Override
    public boolean isValid(String text) {
        return text.trim().length() > 0;
    }
}
