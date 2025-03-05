package controller.order;

import DAO.OrderDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Orders;

@WebServlet(name = "GetOrderDetail", urlPatterns = {"/order/detail"})
public class GetOrderDetailController extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Orders order = orderDAO.getById(orderId);
            
            request.setAttribute("order", order);
            request.getRequestDispatcher("order/orderDetail.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/order");
        }
    }
}
