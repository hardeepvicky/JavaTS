/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaTS.models.converter;

import java.util.LinkedHashMap;

/**
 *
 * @author Tech
 */
public interface ConverterInterface {
    public void convert(LinkedHashMap record, String field);
}
