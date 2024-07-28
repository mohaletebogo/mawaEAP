/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigProductAttribute;
import za.raretag.mawa.entities.ConfigProductPricing;
import za.raretag.mawa.entities.PartnerIdentity;
import za.raretag.mawa.entities.Product;
import za.raretag.mawa.entities.ProductAttribute;
import za.raretag.mawa.entities.ProductCategory;
import za.raretag.mawa.entities.ProductCategoryPK;
import za.raretag.mawa.entities.ProductPricing;
import za.raretag.mawa.entities.ProductPricingPK;
import za.raretag.mawa.entities.controllers.ConfigProductAttributeJpaController;
import za.raretag.mawa.entities.controllers.ConfigProductCategoryJpaController;
import za.raretag.mawa.entities.controllers.ConfigProductPricingJpaController;
import za.raretag.mawa.entities.controllers.ProductAttributeJpaController;
import za.raretag.mawa.entities.controllers.ProductCategoryJpaController;
import za.raretag.mawa.entities.controllers.ProductJpaController;
import za.raretag.mawa.entities.controllers.ProductPricingJpaController;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Customer;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.Item;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Request;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProductBean implements ProductBeanLocal {

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;
    ConfigProductCategoryJpaController productCategoryConfigController;
    ProductCategoryJpaController productCategoryController;
    ProductJpaController productController;
    ProductPricingJpaController productPricingController;
    ConfigProductPricingJpaController pricingTypeController;
    ProductAttributeJpaController productAttributeController;
    ConfigProductAttributeJpaController prodAttrTypeController;

    private void init() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        productCategoryConfigController = new ConfigProductCategoryJpaController(utx, emf);
        productCategoryController = new ProductCategoryJpaController(utx, emf);
        productController = new ProductJpaController(utx, emf);
        productPricingController = new ProductPricingJpaController(utx, emf);
        pricingTypeController = new ConfigProductPricingJpaController(utx, emf);
        productAttributeController = new ProductAttributeJpaController(utx, emf);
        prodAttrTypeController = new ConfigProductAttributeJpaController(utx, emf);
    }

    @Override
    public Response getProductList() {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        Data<List<Item>> data = new Data();
        List<ProductCategory> prodCatList = productCategoryController.findProductCategoryEntities();
        for (ProductCategory prodCat : prodCatList) {
            Item item = new Item();
            item.setProductCode(prodCat.getProductCategoryPK().getProductCode());
            item.setProductCategory(prodCat.getProductCategoryPK().getProductCategory());
            item.setValidFrom(Conversion.dateToString(prodCat.getValidFrom()));
            item.setValidTo(Conversion.dateToString(prodCat.getValidTo()));
            item.setDescription(productController.findProduct(prodCat.getProductCategoryPK().getProductCode()).getDescription());
            ProductPricingPK prodPricePK = new ProductPricingPK(prodCat.getProductCategoryPK().getProductCode(), "UNITPRICE");
            ProductPricing prodPrice = productPricingController.findProductPricing(prodPricePK);
            if (prodPrice != null) {
                item.setPrice(prodPrice.getValue());
                itemList.add(item);
            }

        }
        data.set(itemList);
        return new Response(data, messageList);
    }

    @Override
    public Response addProduct(Item item) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> data = new Data();
        try {
            Product product = new Product();
            product.setProductCode(item.getProductCode().toUpperCase());
            product.setDescription(item.getDescription().toUpperCase());
            product.setValidFrom(new Date());
            product.setValidTo(Conversion.stringToDate("9999-12-31"));
            productController.create(product);

            ProductCategoryPK prodCatPK = new ProductCategoryPK(item.getProductCode(), item.getProductCategory());
            ProductCategory prodCat = new ProductCategory();
            prodCat.setProductCategoryPK(prodCatPK);
            prodCat.setValidFrom(new Date());
            prodCat.setValidTo(Conversion.stringToDate("9999-12-31"));
            productCategoryController.create(prodCat);

            ProductPricingPK prodPricePK = new ProductPricingPK(item.getProductCode(), "UNITPRICE");
            ProductPricing prodPrice = new ProductPricing();
            prodPrice.setProductPricingPK(prodPricePK);
            ConfigProductPricing configProductPricing = pricingTypeController.findConfigProductPricing("UNITPRICE");
            prodPrice.setConfigProductPricing(configProductPricing);
            prodPrice.setProduct(product);
            prodPrice.setValue(item.getPrice());
            prodPrice.setValidFrom(new Date());
            prodPrice.setValidTo(Conversion.stringToDate("9999-12-31"));
            productPricingController.create(prodPrice);

            List<ConfigProductAttribute> prodAttrTypeList = prodAttrTypeController.findConfigProductAttributeEntities();
            for (ConfigProductAttribute prodAttrType : prodAttrTypeList) {
                ProductAttribute prodAttr = new ProductAttribute();
                prodAttr.setAttributeType(prodAttrType);
                prodAttr.setProductCode(product);
                prodAttr.setAttributeValue("0");
                prodAttr.setValidFrom(new Date());
                prodAttr.setValidTo(Conversion.stringToDate("9999-12-31"));
                productAttributeController.create(prodAttr);
            }

            data.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.SEVERE, null, ex);
            data.set(Boolean.FALSE);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.SEVERE, null, ex);
            data.set(Boolean.FALSE);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }

    @Override
    public Response getProductListByCategory(String productCategory) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        Data<List<Item>> data = new Data();
        List<ProductCategory> prodCatList = productCategoryController.findProductCategoryEntities();
        for (ProductCategory prodCat : prodCatList) {
            if (productCategory.equals(prodCat.getProductCategoryPK().getProductCategory())) {
                Item item = new Item();
                item.setProductCode(prodCat.getProductCategoryPK().getProductCode());
                item.setProductCategory(prodCat.getProductCategoryPK().getProductCategory());
                item.setValidFrom(Conversion.dateToString(prodCat.getValidFrom()));
                item.setValidTo(Conversion.dateToString(prodCat.getValidTo()));
                item.setDescription(productController.findProduct(prodCat.getProductCategoryPK().getProductCode()).getDescription());
                ProductPricingPK prodPricePK = new ProductPricingPK(prodCat.getProductCategoryPK().getProductCode(), "UNITPRICE");
                item.setPrice(productPricingController.findProductPricing(prodPricePK).getValue());
                itemList.add(item);
            }
        }
        data.set(itemList);
        return new Response(data, messageList);
    }

    @Override
    public String getProductAttribute(String productCode, String attribute) {
//        Data<ProductAttribute> productData = request.getData();
//        ProductAttribute productAttribute = productData.get();
        init();
        List<ProductAttribute> productAttributeList = productAttributeController.findProductAttributeEntities();
        for (ProductAttribute prodAttr : productAttributeList) {
            if (prodAttr.getAttributeType().getId().equals(attribute) && prodAttr.getProductCode().getProductCode().equals(productCode)) {
                return prodAttr.getAttributeValue();
            }
        }
        return null;
    }

}
