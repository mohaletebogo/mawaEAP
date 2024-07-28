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
import za.raretag.mawa.entities.ConfigFileLocation;
import za.raretag.mawa.entities.controllers.ConfigFileLocationJpaController;
import za.raretag.mawa.entities.controllers.ConfigSalesAreaJpaController;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LocationBean implements LocationBeanLocal {

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;
    ConfigFileLocationJpaController fileLocationController;

    private void initController() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        fileLocationController = new ConfigFileLocationJpaController(utx, emf);
    }

    @Override
    public String getPath(String locationName) {
        initController();
        ConfigFileLocation location = fileLocationController.findConfigFileLocation(locationName);
        return location.getFilePath();
    }

}
