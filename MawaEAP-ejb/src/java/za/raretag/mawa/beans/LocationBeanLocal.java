/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import javax.ejb.Local;

/**
 *
 * @author tebogom
 */
@Local
public interface LocationBeanLocal {

    String getPath(String locationName);
    
}
