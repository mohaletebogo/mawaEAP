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
//import za.raretag.crm.entities.Orderheader;
//
///**
// *
// * @author tebogom
// */
//public class OrderSerializer implements JsonSerializer<Order> {
//
//    @Override
//    public JsonElement serialize(Order order, Type type, JsonSerializationContext jsc) {
//        final JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("orderNo", order.getOrderNo());       
//        jsonObject.addProperty("salesArea", order.getSalesArea());
//        jsonObject.addProperty("orderType", order.getOrderType());
//        jsonObject.addProperty("status", order.getStatus());
//        jsonObject.addProperty("lastPremium", order.getLastPremium());
//        jsonObject.addProperty("monthlyPremium", order.getMonthlyPremium());
//        jsonObject.addProperty("idnumber", order.getIdNumber());
//        jsonObject.addProperty("customer", order.getCustomer());
//        
//        
////        final JsonElement jsonOrders = jsc.serialize(order.getOrderitemCollection());
////        jsonObject.add("items", jsonOrders);
////
////        final JsonElement jsonPayments = jsc.serialize(order.getOrderpaymentCollection());
////        jsonObject.add("payments", jsonPayments);
////
////        final JsonElement jsonPartners = jsc.serialize(order.getOrderpartnerCollection());
////        jsonObject.add("partners", jsonPartners);
//        return jsonObject;
//    }
//
//}
