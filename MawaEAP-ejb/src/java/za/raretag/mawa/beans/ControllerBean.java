/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.UserTransaction;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ControllerBean implements ControllerBeanLocal {

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
    public String getEntity(String tableName) {

        Metamodel metamodel = em.getMetamodel();
        for (EntityType<?> e : metamodel.getEntities()) {
            Class<?> entityClass = e.getJavaType();
            String entityTableName = entityClass.getAnnotation(Table.class).name();
            if (tableName.equals(entityTableName)){
                return entityClass.getName();
            }            
        }
        return null;
    }

}
