/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.raretag.mawa.beans;

import javax.ejb.Local;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Local
public interface NumberRangeBeanLocal {

    String getNumber(String objectType);

    Response addRange(String object_type, String start, String end);
    
}
