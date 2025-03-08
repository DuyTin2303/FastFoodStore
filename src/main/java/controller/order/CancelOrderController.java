package controller.order;

import DAO.OrderDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.enums.OrderStatusEnum;

@WebServlet(name = "CancelOrder", urlPatterns = {"/order/cancel"})
public class CancelOrderController extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            boolean isSuccess = orderDAO.updateStatus(orderId, OrderStatusEnum.Cancelled);
            if (!isSuccess) {
                throw new Exception();
            }

            request.getSession().setAttribute("success", "Order #" + orderId + " successfully cancelled");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Order can't be cancelled");
        }
        request.getRequestDispatcher("/order").forward(request, response);
    }
}
