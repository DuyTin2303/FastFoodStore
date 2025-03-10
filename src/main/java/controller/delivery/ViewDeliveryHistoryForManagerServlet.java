package controller.delivery;

import DAO.DeliveryDAO;
import model.Delivery;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewDeliveryHistoryForManagerServlet", urlPatterns = {"/Delivery/viewDeliveryHistoryForManager"})
public class ViewDeliveryHistoryForManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tạo đối tượng DeliveryDAO để lấy thông tin về các đơn hàng và trạng thái
        DeliveryDAO deliveryDAO = new DeliveryDAO();

        // Gọi phương thức để lấy tất cả các đơn hàng với trạng thái
        List<Delivery> deliveries = deliveryDAO.getAllOrdersWithStatus();

        // Chuyển danh sách các đơn hàng đến JSP để hiển thị
        request.setAttribute("deliveries", deliveries);

        // Chuyển hướng tới trang JSP nằm trong folder Delivery
        request.getRequestDispatcher("/Delivery/viewDeliveryHistoryForManager.jsp").forward(request, response);
    }
}
    