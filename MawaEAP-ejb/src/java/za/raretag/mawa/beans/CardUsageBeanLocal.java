/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import javax.ejb.Local;
import za.raretag.mawa.generic.Response;
import za.raretag.mawa.generic.Swipe;

/**
 *
 * @author tebogom
 */
@Local
public interface CardUsageBeanLocal {

    Response addSwipe(Swipe swipe);
    
}
