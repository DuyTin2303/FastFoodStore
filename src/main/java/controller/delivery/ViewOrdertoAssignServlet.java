package controller.delivery;

import DAO.DeliveryDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Delivery;

@WebServlet(name="ViewOrdertoAssignServlet", urlPatterns={"/Delivery/viewOrdertoAssign"})
public class ViewOrdertoAssignServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        List<Delivery> unassignedDeliveries = deliveryDAO.getUnassignedOrPendingDeliveries();

        // Đặt danh sách đơn hàng vào attribute để sử dụng trong JSP
        request.setAttribute("unassignedDeliveries", unassignedDeliveries);

        // Forward đến trang JSP để hiển thị danh sách
        request.getRequestDispatcher("/Delivery/viewOrdertoAssign.jsp").forward(request, response);
    }
}
