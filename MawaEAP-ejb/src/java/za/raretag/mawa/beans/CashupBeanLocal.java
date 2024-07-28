/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.List;
import javax.ejb.Local;
import za.raretag.mawa.generic.Cashup;
import za.raretag.mawa.generic.CheckingQuery;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Local
public interface CashupBeanLocal {

    Response create(Cashup cashup);

    Response get(String checkingId);

    Response search(CheckingQuery checkingQuery);
    
}
