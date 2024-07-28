/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.raretag.mawa.beans;

import java.util.List;
import javax.ejb.Local;
import za.raretag.mawa.entities.*;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Response;
import za.raretag.mawa.generic.UserWorkcenter;

/**
 *
 * @author tebogom
 */
@Local
public interface UserBeanLocal {

    Response authenticate(String username, String password);

    String getUserRole(String username);

    List<MessageContainer> createUser(String partner, String role, String email);   

    List<MessageContainer> addUserRole(String username, String role);

    User getUser(String username);

    Response changePassword(String userId, String oldPassword, String newPassword);

    Response resetPassword(String email);

    Response getUserWorkcenter(String user);
    
}
