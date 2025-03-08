package controller.order;

import DAO.OrderDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.enums.OrderStatusEnum;

@WebServlet(name = "VerifyOrder", urlPatterns = {"/order/verify"})
public class VerifyOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            OrderDAO orderDAO = new OrderDAO();
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String status = request.getParameter("vnp_TransactionStatus");
            if (status.equals("00") && orderDAO.updateStatus(orderId, OrderStatusEnum.Confirmed)) {
                request.getSession().setAttribute("success", "Order #" + orderId + " is successfully paid");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Order is not paid");
        }
        request.getRequestDispatcher("/order").forward(request, response);
    }
}
