///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package za.raretag.mawa.generic;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import java.lang.reflect.Type;
//
///**
// *
// * @author tebogom
// */
//public class PolicySerializer implements JsonSerializer<Policy> {
//
//    @Override
//    public JsonElement serialize(Policy policy, Type type, JsonSerializationContext jsc) {
//        final JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("orderNo", policy.getOrderNo());
//        jsonObject.addProperty("salesArea", policy.getSalesArea());
//        jsonObject.addProperty("orderType", policy.getOrderType());
//        jsonObject.addProperty("status", policy.getStatus());
//        jsonObject.addProperty("statusReason", policy.getStatusReason());
//        jsonObject.addProperty("idNumber", policy.getIdNumber());
//        jsonObject.addProperty("customer", policy.getCustomer());
//        jsonObject.addProperty("funeralPlan", policy.getFuneralPlan());
//        jsonObject.addProperty("salesRepresentative", policy.getSalesRepresentative());
//        jsonObject.addProperty("lastPremium", policy.getLastPremium());
//        jsonObject.addProperty("monthlyPremium", policy.getMonthlyPremium());
//        jsonObject.addProperty("monthsOwing", policy.getMonthsOwing());
//        jsonObject.addProperty("amountDue", policy.getAmountDue());
//        jsonObject.addProperty("arrears", policy.getArrears());
//        jsonObject.addProperty("creationDate", policy.getCreationDate().toString());
//        jsonObject.addProperty("effectiveDate", policy.getEffectiveDate().toString());
//        jsonObject.addProperty("joiningDate", policy.getJoiningDate().toString());
//        jsonObject.addProperty("nextPaymentDate", policy.getNextPaymentDate().toString());
//        jsonObject.addProperty("lastPaymentDate", policy.getLastPaymentDate().toString());
//        jsonObject.addProperty("statusDate", policy.getStatusDate().toString());
//        jsonObject.addProperty("lapseDate", policy.getLapseDate().toString());
//
//        final JsonElement jsonPartners = jsc.serialize(policy.getDependants());
//        jsonObject.add("dependants", jsonPartners);
//
//        final JsonElement jsonPayments = jsc.serialize(policy.getPayments());
//        jsonObject.add("payments", jsonPayments);
//        return jsonObject;
//    }
//}
