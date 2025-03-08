package controller.order;

import DAO.OrderDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Orders;
import model.Users;

@WebServlet(name = "GetOrderHistory", urlPatterns = {"/order"})
public class GetOrderHistoryController extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt((String) request.getSession().getAttribute("userId"));
            List<Orders> orders = orderDAO.getAllByUserId(userId);

            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/order/orderHistory.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }
}
