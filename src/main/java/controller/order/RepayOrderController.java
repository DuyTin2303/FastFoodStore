package controller.order;

import DAO.OrderDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Orders;
import utils.VNPay;

@WebServlet(urlPatterns = {"/order/repay"})
public class RepayOrderController extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Orders order = orderDAO.getById(orderId);
            String paymentUrl = VNPay.getPaymentURL(order);
            response.sendRedirect(paymentUrl);
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Order not found");
            response.sendRedirect("/order");
        }
    }
}
