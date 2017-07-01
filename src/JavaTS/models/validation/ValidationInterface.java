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
public interface ValidationInterface 
{
    public boolean isValid(String value);
    public String getMessage();
}
