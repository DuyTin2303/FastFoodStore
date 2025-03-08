package controller.orderManagement;

import DAO.OrderDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import model.Orders;
import model.enums.OrderStatusEnum;

@WebServlet(name = "OrderManagementController", urlPatterns = {"/orderManagement"})
public class OrderManagementController extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String status = request.getParameter("status");
            int pageSize = request.getParameter("pageSize") != null && !request.getParameter("pageSize").isEmpty() ? Integer.parseInt(request.getParameter("pageSize")) : 10;
            int currentPage = request.getParameter("currentPage") != null ? Integer.parseInt(request.getParameter("currentPage")) : 1;

            List<Orders> orders;
            if (status != null && !status.isEmpty()) {
                orders = orderDAO.getAllByStatus(status);
            } else {
                orders = orderDAO.getAll();
            }

            int endPage = (int) Math.ceil((double) orders.size() / pageSize);
            currentPage = Math.max(1, Math.min(currentPage, endPage));

            orders = orders.subList((currentPage - 1) * pageSize, Math.min(currentPage * pageSize, orders.size()));

            request.setAttribute("listStatus", Arrays.asList(OrderStatusEnum.values()));
            request.setAttribute("choosenStatus", status);
            request.setAttribute("choosenPageSize", pageSize);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("endPage", endPage);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/orderManagement/orderManagement.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }
}
