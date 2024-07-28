/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BankNotificationBean implements BankNotificationBeanLocal {

   
}
