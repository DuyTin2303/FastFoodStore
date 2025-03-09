package controller.delivery;

import DAO.DeliveryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Delivery;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewDeliveryDetailsForStaffServlet", urlPatterns = {"/Delivery/viewDeliveryDetailsForStaff"})
public class ViewDeliveryDetailsForStaffServlet extends HttpServlet {

    private DeliveryDAO deliveryDAO;

    @Override
    public void init() throws ServletException {
        deliveryDAO = new DeliveryDAO(); // Khởi tạo DeliveryDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy userId từ session (được lưu khi đăng nhập)
        Integer deliveryStaffId = (Integer) session.getAttribute("userId");

        if (deliveryStaffId == null) {
            response.sendRedirect("../loginView.jsp"); // Đường dẫn đầy đủ đến loginView.jsp
            return;
        }

        // Lấy danh sách đơn hàng giao cho nhân viên
        List<Delivery> deliveries = deliveryDAO.getOrderDetailsForStaff(deliveryStaffId);

        // Đặt danh sách đơn hàng vào request để hiển thị trên trang JSP
        request.setAttribute("deliveries", deliveries);

        // Chuyển hướng đến trang JSP hiển thị chi tiết đơn hàng
        request.getRequestDispatcher("/Delivery/viewDeliveryDetailsForStaff.jsp").forward(request, response);
    }
}
