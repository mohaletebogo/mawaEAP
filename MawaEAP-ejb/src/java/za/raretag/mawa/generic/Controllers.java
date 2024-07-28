/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.controllers.*;

/**
 *
 * @author tebogom
 */
public final class Controllers {
  @PersistenceContext(unitName = "MawaEAP-ejbPU")
    private static EntityManager em;    
    @Resource
    private static EJBContext context;
    private final static EntityManagerFactory emf;
    private final static UserTransaction utx;

    static {
             
            emf = em.getEntityManagerFactory();
            utx = context.getUserTransaction();
            userController = new UserJpaController(utx, emf);
            userRoleController = new UserRoleJpaController(utx, emf);
            partnerController = new PartnerJpaController(utx, emf);
            partnerIdentityController = new PartnerIdentityJpaController(utx, emf);
            partnerContactController = new PartnerContactJpaController(utx, emf);
            partnerAddressController = new PartnerAddressJpaController(utx, emf);
            partnerRoleController = new PartnerRoleJpaController(utx, emf);
            numRangeController = new NumRangeJpaController(utx, emf);
            selectionOptionController = new SelectionOptionJpaController(utx, emf);
        

    }

    private static UserJpaController userController;

    /**
     * Get the value of userController
     *
     * @return the value of userController
     */
    public static UserJpaController getUserController() {
        return userController;
    }

    /**
     * Set the value of userController
     *
     * @param userController new value of userController
     */
    public static void setUserController(UserJpaController userController) {
        Controllers.userController = userController;
    }

    private static PartnerJpaController partnerController;

    /**
     * Get the value of partnerController
     *
     * @return the value of partnerController
     */
    public static PartnerJpaController getPartnerController() {
        return partnerController;
    }

    /**
     * Set the value of partnerController
     *
     * @param partnerController new value of partnerController
     */
    public static void setPartnerController(PartnerJpaController partnerController) {
        Controllers.partnerController = partnerController;
    }

    private static UserRoleJpaController userRoleController;

    /**
     * Get the value of userRolerController
     *
     * @return the value of userRolerController
     */
    public static UserRoleJpaController getUserRoleController() {
        return userRoleController;
    }

    /**
     * Set the value of userRolerController
     *
     * @param userRoleController new value of userRolerController
     */
    public static void setUserRoleController(UserRoleJpaController userRoleController) {
        Controllers.userRoleController = userRoleController;
    }

    private static NumRangeJpaController numRangeController;

    /**
     * Get the value of numRangeController
     *
     * @return the value of numRangeController
     */
    public static NumRangeJpaController getNumRangeController() {
        return numRangeController;
    }

    /**
     * Set the value of numRangeController
     *
     * @param numRangeController new value of numRangeController
     */
    public static void setNumRangeController(NumRangeJpaController numRangeController) {
        Controllers.numRangeController = numRangeController;
    }

    private static PartnerIdentityJpaController partnerIdentityController;

    /**
     * Get the value of partnerIdentityController
     *
     * @return the value of partnerIdentityController
     */
    public static PartnerIdentityJpaController getPartnerIdentityController() {
        return partnerIdentityController;
    }

    /**
     * Set the value of partnerIdentityController
     *
     * @param partnerIdentityController new value of partnerIdentityController
     */
    public static void setPartnerIdentityController(PartnerIdentityJpaController partnerIdentityController) {
        Controllers.partnerIdentityController = partnerIdentityController;
    }

    private static PartnerAddressJpaController partnerAddressController;

    /**
     * Get the value of partnerAddressController
     *
     * @return the value of partnerAddressController
     */
    public static PartnerAddressJpaController getPartnerAddressController() {
        return partnerAddressController;
    }

    /**
     * Set the value of partnerAddressController
     *
     * @param partnerAddressController new value of partnerAddressController
     */
    public static void setPartnerAddressController(PartnerAddressJpaController partnerAddressController) {
        Controllers.partnerAddressController = partnerAddressController;
    }

    private static PartnerContactJpaController partnerContactController;

    /**
     * Get the value of partnerContactController
     *
     * @return the value of partnerContactController
     */
    public static PartnerContactJpaController getPartnerContactController() {
        return partnerContactController;
    }

    /**
     * Set the value of partnerContactController
     *
     * @param partnerContactController new value of partnerContactController
     */
    public static void setPartnerContactController(PartnerContactJpaController partnerContactController) {
        Controllers.partnerContactController = partnerContactController;
    }

    private static PartnerRoleJpaController partnerRoleController;

    /**
     * Get the value of partnerRoleController
     *
     * @return the value of partnerRoleController
     */
    public static PartnerRoleJpaController getPartnerRoleController() {
        return partnerRoleController;
    }

    /**
     * Set the value of partnerRoleController
     *
     * @param partnerRoleController new value of partnerRoleController
     */
    public static void setPartnerRoleController(PartnerRoleJpaController partnerRoleController) {
        Controllers.partnerRoleController = partnerRoleController;
    }

    private static SelectionOptionJpaController selectionOptionController;

    /**
     * Get the value of selOptController
     *
     * @return the value of selOptController
     */
    public static SelectionOptionJpaController getSelectionOptionController() {
        return selectionOptionController;
    }

    /**
     * Set the value of selOptController
     *
     * @param selectionOptionController new value of selectionOptionController
     */
    public static void setSelectionOptionController(SelectionOptionJpaController selectionOptionController) {
        Controllers.selectionOptionController = selectionOptionController;
    }

}
