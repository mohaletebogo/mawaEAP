    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.raretag.mawa.beans.TransactionBeanLocal;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Order;
import za.raretag.mawa.generic.OrderPayment;
import za.raretag.mawa.generic.Payment;
import za.raretag.mawa.generic.Policy;
import za.raretag.mawa.generic.Request;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
public class PaymentServlet extends HttpServlet {

    @EJB
    private TransactionBeanLocal transactionBean;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private GsonBuilder gsonBuilder;
    private String json;
    private final Gson gson = new Gson();
    private String operator;
    // List<MessageContainer> messageList;
    private Response resp;
    MessageContainer message;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            String userAction = request.getParameter("userAction");
            String process = request.getParameter("process");
            String callback = request.getParameter("callback");
            HttpSession session = request.getSession(true);
            String user = session.getAttribute("userId").toString();
            String amount = request.getParameter("amount");
            String terminalID = request.getParameter("terminalID");
            String paymentPeriod = request.getParameter("paymentPeriod");
            String employeeResponsible = request.getParameter("employeeResponsible");
            String externalReceipt = request.getParameter("receiptNumber");
            String receiptDate = request.getParameter("receiptDate");
            String referenceNo = request.getParameter("referenceNo");

            switch (userAction) {
                case "create":
                    OrderPayment orderPayment = new OrderPayment();
                    orderPayment.setOrderNo(referenceNo);
                    orderPayment.setPaymentDate(receiptDate);
                    orderPayment.setAmount(amount);
                    orderPayment.setReceiptNo(externalReceipt);
                    orderPayment.setReceivedBy(employeeResponsible);
                    orderPayment.setTerminalId(terminalID);
                    orderPayment.setPaymentPeriod(paymentPeriod);

                    Request paymentRequest = new Request();
                    Data<OrderPayment> requestData = new Data();
                    requestData.set(orderPayment);
                    paymentRequest.setData(requestData);
                    paymentRequest.setRequester(user);
                   
                    json = gson.toJson(transactionBean.addPayment(paymentRequest));
                    break;
                case "get":
                    break;
            }
            if (callback != null && callback.length() > 1) {
                out.println(callback + "(" + json + ");");
            } else {
                out.println(json);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
