/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.validation;

/**
 *
 * @author Tech
 */
public class Validation implements ValidationInterface
{
    protected String errorMsg;
    public Validation(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    @Override
    public boolean isValid(String value)
    {
        return true;
    }
    
    public String getMessage()
    {
        return this.errorMsg;
    }
}
