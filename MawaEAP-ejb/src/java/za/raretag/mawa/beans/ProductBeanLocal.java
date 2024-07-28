/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import javax.ejb.Local;
import za.raretag.mawa.generic.Item;
import za.raretag.mawa.generic.Request;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Local
public interface ProductBeanLocal {

    Response getProductList();

    Response addProduct(Item item);

    Response getProductListByCategory(String productCategory);

    String getProductAttribute(String productCode, String attribute);
    
}
