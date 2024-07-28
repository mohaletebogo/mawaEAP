///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package za.raretag.mawa.generic;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import za.raretag.crm.entities.Orderheader;
//import za.raretag.crm.entities.Orderitem;
//import za.raretag.crm.entities.Orderpartner;
//import za.raretag.crm.entities.Orderpayment;
//
///**
// *
// * @author tebogom
// */
//public class Group extends Order {
//
//    public Group() {
//    }
//
//    public Group(Orderheader orderHeader) {
//        this.setOrderNo(orderHeader.getOrderId());
//        this.setSalesArea(orderHeader.getSalesArea());
//        this.setStatus(orderHeader.getStatus());
//        //this.setCreationDate(orderHeader.getValidFrom());
//        this.setCustomer(orderHeader.getDescription());
//        //this.setSalesRepresentative(funeralPlan);
//
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
////        this.setEffectiveDate(orderHeader.getEffectiveDate());
////
////        this.setCreationDate(orderHeader.getValidFrom());
//
//        List<Dependant> dependants = new ArrayList<>();
//        for (Orderpartner partner : orderHeader.getOrderpartnerCollection()) {
//            switch (partner.getOrderpartnerPK().getPartnerFunction()) {
//                case "SREP":
//                    this.setSalesRepresentative(partner.getOrderpartnerPK().getPartnerNo());
//                    break;
//                case "CUST":
//                    this.setCustomer(partner.getPartner().getName1() + " " + partner.getPartner().getName2());
//                    break;
//                case "DEPE":
//                    Dependant dependant = new Dependant(partner);
//                    dependants.add(dependant);
//                    break;
//            }
//        }
//
//        this.setDependants(dependants);
//
//        List<Payment> payments = new ArrayList<>();
//        Collection orders = orderHeader.getOrderpaymentCollection();
////        Integer lastPrem = 0;
////        Integer premium = 0;
////        Integer newPremium = 0;
//        for (Orderpayment payment : orderHeader.getOrderpaymentCollection()) {
//            Payment pay = new Payment(payment);
//            payments.add(pay);
////            if (!"New".equals(payment.getPaymentType().getId())) {
////                premium = Integer.parseInt(payment.getPaymentType().getId());
////            }
////
////            if (premium > newPremium) {
////                newPremium = premium;
////            }
//        }
////        if (newPremium > 0) {
////            newPremium = newPremium + 1;
////            String monthString = newPremium.toString();
////            monthString = monthString.substring(4,6);
////            Integer month = Integer.parseInt(monthString);
////            if (month > 12) {
////               String yearString = newPremium.toString().substring(0,4) ; 
////               Integer year = Integer.parseInt(yearString) + 1;
////               String newMonth = "01";
////               newPremium = Integer.parseInt(year +""+newMonth);
////            }
////        }
////        this.lastPremium = newPremium.toString();
//        this.setPayments(payments);
//
//        for (Orderitem orderItem : orderHeader.getOrderitemCollection()) {
//            this.setFuneralPlan(orderItem.getProductId().getProductCode());
//            //this.setMonthlyPremium(orderItem.getUnitPrice());
//        }
//
//    }
//
//    private String funeralPlan;
//
//    /**
//     * Get the value of funeralPlan
//     *
//     * @return the value of funeralPlan
//     */
//    public String getFuneralPlan() {
//        return funeralPlan;
//    }
//
//    /**
//     * Set the value of funeralPlan
//     *
//     * @param funeralPlan new value of funeralPlan
//     */
//    public void setFuneralPlan(String funeralPlan) {
//        this.funeralPlan = funeralPlan;
//    }
//
//    private List<Dependant> dependants;
//
//    /**
//     * Get the value of dependants
//     *
//     * @return the value of dependants
//     */
//    public List<Dependant> getDependants() {
//        return dependants;
//    }
//
//    /**
//     * Set the value of dependants
//     *
//     * @param dependants new value of dependants
//     */
//    public void setDependants(List<Dependant> dependants) {
//        this.dependants = dependants;
//    }
//
//    private List<Payment> payments;
//
//    /**
//     * Get the value of payments
//     *
//     * @return the value of payments
//     */
//    public List<Payment> getPayments() {
//        return payments;
//    }
//
//    /**
//     * Set the value of payments
//     *
//     * @param payments new value of payments
//     */
//    public void setPayments(List<Payment> payments) {
//        this.payments = payments;
//    }
//
//    private String lastPremium;
//
//    /**
//     * Get the value of lastPremium
//     *
//     * @return the value of lastPremium
//     */
//    public String getLastPremium() {
//        return lastPremium;
//    }
//
//    /**
//     * Set the value of lastPremium
//     *
//     * @param lastPremium new value of lastPremium
//     */
//    public void setLastPremium(String lastPremium) {
//        this.lastPremium = lastPremium;
//    }
//
//    private Double monthlyPremium;
//
//    /**
//     * Get the value of monthlyPremium
//     *
//     * @return the value of monthlyPremium
//     */
//    public Double getMonthlyPremium() {
//        return monthlyPremium;
//    }
//
//    /**
//     * Set the value of monthlyPremium
//     *
//     * @param monthlyPremium new value of monthlyPremium
//     */
//    public void setMonthlyPremium(Double monthlyPremium) {
//        this.monthlyPremium = monthlyPremium;
//    }
//
//}
