/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.List;
import javax.ejb.Local;
import za.raretag.mawa.generic.FieldOption;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Local
public interface FieldOptionBeanLocal {

    Response getFieldOptions();

    Response createFieldOption(String tableName, String description);
    
}
