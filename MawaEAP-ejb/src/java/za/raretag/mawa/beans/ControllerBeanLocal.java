/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.Set;
import javax.ejb.Local;
import javax.persistence.metamodel.EntityType;

/**
 *
 * @author tebogom
 */
@Local
public interface ControllerBeanLocal {

    String getEntity(String tableName);
    
}
