/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.controllers.ConfigProductCategoryJpaController;
import za.raretag.mawa.entities.controllers.ProductCategoryJpaController;
import za.raretag.mawa.entities.controllers.ProductJpaController;
import za.raretag.mawa.entities.controllers.ProductPricingJpaController;
import za.raretag.mawa.generic.Order;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderBean implements OrderBeanLocal {

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;

    private void init() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();

    }

    @Override
    public Response create(Order order) {
        return null;
    }
    
    
}
