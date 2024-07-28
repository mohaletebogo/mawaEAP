/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.*;

import za.raretag.mawa.entities.controllers.*;

import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;
import za.raretag.mawa.generic.Controllers;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.Dropdowns;
import za.raretag.mawa.generic.FieldOption;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class FieldOptionBean implements FieldOptionBeanLocal {

    @EJB
    private ControllerBeanLocal controllerBean;

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;

    @EJB
    private NumberRangeBeanLocal numberRangeBean;

    // SelectionOptionJpaController selOptController = Controllers.getSelectionOptionController();
    List<SelectionOption> selectOptions;

    ConfigSalesAreaJpaController salesAreaController;
    ConfigObjectTypeJpaController objectController;
    ConfigRoleTypeJpaController roleTypeController;
    PartnerRoleJpaController partnerRoleController;
    PartnerJpaController partnerController;
    ConfigOrderTypeJpaController orderTypeController;
    ConfigProductCategoryJpaController productCategoryController;
    ConfigProductAttributeJpaController productAttributeController;
//    ConfigTablesJpaController configTableController;
    ConfigProductPricingJpaController productPricingController;
    ConfigIdentityTypeJpaController identityController;
    ConfigPartnerFunctionJpaController partnerFunctionController;
    ConfigLocationTypeJpaController locationTypeController;
    ConfigTransactionTypeJpaController transactionTypeController;
    ConfigDateTypeJpaController dateTypeController;
    ConfigStatusTypeJpaController statusController;
    ConfigBankNameJpaController bankNameController;
    ConfigBankAccountTypeJpaController bankAccountTypeController;
    ConfigBankAccountUsageJpaController bankAccountUsageController;
    ConfigClaimTypeJpaController claimTypeController;
    ConfigPaymentTypeJpaController paymentTypeController;

    private void initController() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        statusController = new ConfigStatusTypeJpaController(utx, emf);
        salesAreaController = new ConfigSalesAreaJpaController(utx, emf);
        objectController = new ConfigObjectTypeJpaController(utx, emf);
        roleTypeController = new ConfigRoleTypeJpaController(utx, emf);
        partnerRoleController = new PartnerRoleJpaController(utx, emf);
        partnerController = new PartnerJpaController(utx, emf);
        orderTypeController = new ConfigOrderTypeJpaController(utx, emf);
        productCategoryController = new ConfigProductCategoryJpaController(utx, emf);
//        configTableController = new ConfigTablesJpaController(utx, emf);
        productPricingController = new ConfigProductPricingJpaController(utx, emf);
        identityController = new ConfigIdentityTypeJpaController(utx, emf);
        partnerFunctionController = new ConfigPartnerFunctionJpaController(utx, emf);
        locationTypeController = new ConfigLocationTypeJpaController(utx, emf);
        transactionTypeController = new ConfigTransactionTypeJpaController(utx, emf);
        productAttributeController = new ConfigProductAttributeJpaController(utx, emf);
        dateTypeController = new ConfigDateTypeJpaController(utx, emf);
        bankNameController = new ConfigBankNameJpaController(utx, emf);
        bankAccountTypeController = new ConfigBankAccountTypeJpaController(utx, emf);
        bankAccountUsageController = new ConfigBankAccountUsageJpaController(utx, emf);
        claimTypeController = new ConfigClaimTypeJpaController(utx, emf);
        paymentTypeController = new ConfigPaymentTypeJpaController(utx, emf);
    }

    @Override
    public Response getFieldOptions() {

        List<MessageContainer> messageList = new ArrayList<>();
        Data<List<FieldOption>> data = new Data();

        List<FieldOption> fieldOptions = new ArrayList<>();
        initController();
        List<ConfigSalesArea> salesAreaList = salesAreaController.findConfigSalesAreaEntities();
        for (ConfigSalesArea salesArea : salesAreaList) {
            FieldOption fieldOption = new FieldOption("salesArea", salesArea.getId(), salesArea.getDescription());
            fieldOptions.add(fieldOption);
            fieldOption = new FieldOption("collectionOffice", salesArea.getId(), salesArea.getDescription());
            fieldOptions.add(fieldOption);
            fieldOption = new FieldOption("submissionOffice", salesArea.getId(), salesArea.getDescription());
            fieldOptions.add(fieldOption);
            fieldOption = new FieldOption("serviceArea", salesArea.getId(), salesArea.getDescription());
            fieldOptions.add(fieldOption);

        }

//        List<ConfigTables> tableList = configTableController.findConfigTablesEntities();
//        for (ConfigTables table : tableList) {
//            FieldOption fieldOption = new FieldOption("tableName", table.getTableName(), table.getTableName());
//            fieldOptions.add(fieldOption);
//        }
        List<ConfigObjectType> objectTypeList = objectController.findConfigObjectTypeEntities();
        for (ConfigObjectType objectType : objectTypeList) {
            FieldOption fieldOption = new FieldOption("objectType", objectType.getObjectId(), objectType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigPaymentType> paymentTypeList = paymentTypeController.findConfigPaymentTypeEntities();
        for (ConfigPaymentType paymentType : paymentTypeList) {
            FieldOption fieldOption = new FieldOption("paymentPeriod", paymentType.getId(), paymentType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigBankName> bankNameList = bankNameController.findConfigBankNameEntities();
        for (ConfigBankName bankName : bankNameList) {
            FieldOption fieldOption = new FieldOption("bankName", bankName.getId(), bankName.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigBankAccountType> bankAccountTypeList = bankAccountTypeController.findConfigBankAccountTypeEntities();
        for (ConfigBankAccountType accountType : bankAccountTypeList) {
            FieldOption fieldOption = new FieldOption("accountType", accountType.getId(), accountType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigRoleType> roleTypeList = roleTypeController.findConfigRoleTypeEntities();
        for (ConfigRoleType roleType : roleTypeList) {
            FieldOption fieldOption = new FieldOption("roleType", roleType.getId(), roleType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigOrderType> orderTypeList = orderTypeController.findConfigOrderTypeEntities();
        for (ConfigOrderType orderType : orderTypeList) {
            FieldOption fieldOption = new FieldOption("orderType", orderType.getId(), orderType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigDateType> dateTypeList = dateTypeController.findConfigDateTypeEntities();
        for (ConfigDateType dateType : dateTypeList) {
            FieldOption fieldOption = new FieldOption("dateType", dateType.getId(), dateType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigLocationType> locationTypeList = locationTypeController.findConfigLocationTypeEntities();
        for (ConfigLocationType locationType : locationTypeList) {
            FieldOption fieldOption = new FieldOption("locationType", locationType.getId(), locationType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigTransactionType> transactionTypeList = transactionTypeController.findConfigTransactionTypeEntities();
        for (ConfigTransactionType transactionType : transactionTypeList) {
            FieldOption fieldOption = new FieldOption("transactionType", transactionType.getId(), transactionType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigProductCategory> productCategoryList = productCategoryController.findConfigProductCategoryEntities();
        for (ConfigProductCategory productCategory : productCategoryList) {
            FieldOption fieldOption = new FieldOption("productCategory", productCategory.getId(), productCategory.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigProductAttribute> productAttributeList = productAttributeController.findConfigProductAttributeEntities();
        for (ConfigProductAttribute productAttribute : productAttributeList) {
            FieldOption fieldOption = new FieldOption("productAttribute", productAttribute.getId(), productAttribute.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigProductPricing> productPricingList = productPricingController.findConfigProductPricingEntities();
        for (ConfigProductPricing productPricing : productPricingList) {
            FieldOption fieldOption = new FieldOption("productPricing", productPricing.getId(), productPricing.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigIdentityType> identityTypeList = identityController.findConfigIdentityTypeEntities();
        for (ConfigIdentityType identityType : identityTypeList) {
            FieldOption fieldOption = new FieldOption("identityType", identityType.getId(), identityType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigClaimType> claimTypeList = claimTypeController.findConfigClaimTypeEntities();
        for (ConfigClaimType claimType : claimTypeList) {
            FieldOption fieldOption = new FieldOption("claimType", claimType.getId(), claimType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigStatusType> statusTypeList = statusController.findConfigStatusTypeEntities();
        for (ConfigStatusType statusType : statusTypeList) {
            FieldOption fieldOption = new FieldOption("statusType", statusType.getId(), statusType.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<ConfigPartnerFunction> partnerFunctionList = partnerFunctionController.findConfigPartnerFunctionEntities();
        for (ConfigPartnerFunction partnerFunction : partnerFunctionList) {
            FieldOption fieldOption = new FieldOption("partnerFunction", partnerFunction.getId(), partnerFunction.getDescription());
            fieldOptions.add(fieldOption);
        }

        List<PartnerRole> partnerRoleList = partnerRoleController.findPartnerRoleEntities();
        for (PartnerRole partnerRole : partnerRoleList) {
            if ("EMPLOYEE".equals(partnerRole.getPartnerRolePK().getRoleId())) {
                Partner partner = partnerController.findPartner(partnerRole.getPartnerRolePK().getPartnerNo());
                FieldOption fieldOption = new FieldOption("employeeResponsible", partner.getPartnerNo(), Conversion.getPartnerName(partner));
                fieldOptions.add(fieldOption);
                fieldOption = new FieldOption("requestor", partner.getPartnerNo(), Conversion.getPartnerName(partner));
                fieldOptions.add(fieldOption);
                 fieldOption = new FieldOption("recipient", partner.getPartnerNo(), Conversion.getPartnerName(partner));
                fieldOptions.add(fieldOption);
            }
        }
        data.set(fieldOptions);
        return new Response(data, messageList);
    }

    @Override
    public Response createFieldOption(String tableName, String description) {

        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> data = new Data();
        initController();
        try {
            switch (tableName) {
                case "_config_sales_area":
                    ConfigSalesArea salesArea = new ConfigSalesArea();
                    salesArea.setId((numberRangeBean.getNumber("SALESAREA")));
                    salesArea.setDescription(description);
                    salesArea.setValidFrom(new Date());
                    salesArea.setValidTo(Conversion.stringToDate("9999-12-31"));
                    salesAreaController.create(salesArea);
                    break;
                case "_config_partner_function":
                    ConfigPartnerFunction partnerFunction = new ConfigPartnerFunction();
                    partnerFunction.setId((description.replaceAll("\\s", "")).toUpperCase());
                    partnerFunction.setDescription(description);
                    partnerFunction.setValidFrom(new Date());
                    partnerFunction.setValidTo(Conversion.stringToDate("9999-12-31"));
                    partnerFunctionController.create(partnerFunction);
                    break;

                case "_config_location_type":
                    ConfigLocationType locationType = new ConfigLocationType();
                    locationType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    locationType.setDescription(description);
                    locationType.setValidFrom(new Date());
                    locationType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    locationTypeController.create(locationType);
                    break;
                case "_config_claim_type":
                    ConfigClaimType claimType = new ConfigClaimType();
                    claimType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    claimType.setDescription(description);
                    claimType.setValidFrom(new Date());
                    claimType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    claimTypeController.create(claimType);
                    break;
                case "_config_status_type":
                    ConfigStatusType statusType = new ConfigStatusType();
                    statusType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    statusType.setDescription(description);
                    statusType.setValidFrom(new Date());
                    statusType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    statusController.create(statusType);
                    break;
                case "_config_transaction_type":
                    ConfigTransactionType transactionType = new ConfigTransactionType();
                    transactionType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    transactionType.setDescription(description);
                    transactionType.setValidFrom(new Date());
                    transactionType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    transactionTypeController.create(transactionType);
                    break;
                case "_config_object_type":
                    ConfigObjectType objectType = new ConfigObjectType();
                    objectType.setObjectId((description.replaceAll("\\s", "")).toUpperCase());
                    objectType.setDescription(description);
                    objectType.setValidFrom(new Date());
                    objectType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    objectController.create(objectType);
                    break;

                case "_config_role_type":
                    ConfigRoleType roleType = new ConfigRoleType();
                    roleType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    roleType.setDescription(description);
                    roleType.setValidFrom(new Date());
                    roleType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    roleTypeController.create(roleType);
                    break;
                case "_config_order_type":
                    ConfigOrderType orderType = new ConfigOrderType();
                    orderType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    orderType.setDescription(description);
                    orderType.setValidFrom(new Date());
                    orderType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    orderTypeController.create(orderType);
                    break;
                case "_config_product_category":
                    ConfigProductCategory productCategory = new ConfigProductCategory();
                    productCategory.setId((description.replaceAll("\\s", "")).toUpperCase());
                    productCategory.setDescription(description);
                    productCategory.setValidFrom(new Date());
                    productCategory.setValidTo(Conversion.stringToDate("9999-12-31"));
                    productCategoryController.create(productCategory);
                    break;
                case "_config_product_attribute":
                    ConfigProductAttribute productAttribute = new ConfigProductAttribute();
                    productAttribute.setId((description.replaceAll("\\s", "")).toUpperCase());
                    productAttribute.setDescription(description);
                    productAttribute.setValidFrom(new Date());
                    productAttribute.setValidTo(Conversion.stringToDate("9999-12-31"));
                    productAttributeController.create(productAttribute);
                    break;
                case "_config_product_pricing":
                    ConfigProductPricing productPricing = new ConfigProductPricing();
                    productPricing.setId((description.replaceAll("\\s", "")).toUpperCase());
                    productPricing.setDescription(description);
                    productPricing.setValidFrom(new Date());
                    productPricing.setValidTo(Conversion.stringToDate("9999-12-31"));
                    productPricingController.create(productPricing);
                    break;

                case "_config_identity_type":
                    ConfigIdentityType identityType = new ConfigIdentityType();
                    identityType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    identityType.setDescription(description);
                    identityType.setValidFrom(new Date());
                    identityType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    identityController.create(identityType);
                    break;
                case "_config_date_type":
                    ConfigDateType dateType = new ConfigDateType();
                    dateType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    dateType.setDescription(description);
                    dateType.setValidFrom(new Date());
                    dateType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    dateTypeController.create(dateType);
                    break;
                case "_config_bank_name":
                    ConfigBankName bankName = new ConfigBankName();
                    bankName.setId((description.replaceAll("\\s", "")).toUpperCase());
                    bankName.setDescription(description);
                    bankName.setValidFrom(new Date());
                    bankName.setValidTo(Conversion.stringToDate("9999-12-31"));
                    bankNameController.create(bankName);
                    break;
                case "_config_bank_account_type":
                    ConfigBankAccountType bankAccountType = new ConfigBankAccountType();
                    bankAccountType.setId((description.replaceAll("\\s", "")).toUpperCase());
                    bankAccountType.setDescription(description);
                    bankAccountType.setValidFrom(new Date());
                    bankAccountType.setValidTo(Conversion.stringToDate("9999-12-31"));
                    bankAccountTypeController.create(bankAccountType);
                    break;
                case "_config_bank_account_usage":
                    ConfigBankAccountUsage bankAccountUsage = new ConfigBankAccountUsage();
                    bankAccountUsage.setId((description.replaceAll("\\s", "")).toUpperCase());
                    bankAccountUsage.setDescription(description);
                    bankAccountUsage.setValidFrom(new Date());
                    bankAccountUsage.setValidTo(Conversion.stringToDate("9999-12-31"));
                    bankAccountUsageController.create(bankAccountUsage);
                    break;
                default:
                    data.set(Boolean.FALSE);
                    MessageContainer message = new MessageContainer("E", "Field option not supported");
                    messageList.add(message);
                    break;
            }
            data.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(FieldOptionBean.class.getName()).log(Level.SEVERE, null, ex);
            data.set(Boolean.FALSE);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(FieldOptionBean.class.getName()).log(Level.SEVERE, null, ex);
            data.set(Boolean.FALSE);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }

        return new Response(data, messageList);
    }

    private void create(String tableName, String description) {

//        String tabClass = controllerBean.getEntity(tableName);
//        Class<?> ClassType;
//        try {
//            ClassType = Class.forName(tabClass);
//            Data<> rec = new Data();
//
//            ClassType salesArea = new ClassType();
//            salesArea.setId((numberRangeBean.getNumber("SALESAREA")));
//            salesArea.setDescription(description);
//            salesArea.setValidFrom(new Date());
//            salesArea.setValidTo(Conversion.stringToDate("9999-12-31"));
//            salesAreaController.create(salesArea);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(FieldOptionBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
