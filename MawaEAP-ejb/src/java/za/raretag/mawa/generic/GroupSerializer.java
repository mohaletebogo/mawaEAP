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
//public class GroupSerializer implements JsonSerializer<Group> {
//
//    @Override
//    public JsonElement serialize(Group group, Type type, JsonSerializationContext jsc) {
//        final JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("contractNo", group.getOrderNo());
//        jsonObject.addProperty("salesArea", group.getSalesArea());
//        jsonObject.addProperty("transactionType", group.getOrderType());
//        jsonObject.addProperty("status", group.getStatus());
//        jsonObject.addProperty("creationDate", group.getCreationDate().toString());
//        jsonObject.addProperty("effectiveDate", group.getEffectiveDate().toString());
//        jsonObject.addProperty("customer", group.getCustomer());
//        jsonObject.addProperty("funeralPlan", group.getFuneralPlan());
//        jsonObject.addProperty("salesRepresentative", group.getSalesRepresentative());
//        //jsonObject.addProperty("lastPremium", group.getLastPremium());
//        //jsonObject.addProperty("monthlyPremium", group.getMonthlyPremium());
//        
//
////        final JsonElement jsonPartners = jsc.serialize(group.getDependants());
////        jsonObject.add("dependants", jsonPartners);        
//
//        final JsonElement jsonPayments = jsc.serialize(group.getPayments());
//        jsonObject.add("payments", jsonPayments);
//        return jsonObject;
//    }
//}
