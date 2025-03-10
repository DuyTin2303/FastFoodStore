package controller.delivery;

import DAO.DeliveryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateStatusOderforStaffServlet", urlPatterns = {"/Delivery/updateStatusOderforStaff"})
public class UpdateStatusOderforStaffServlet extends HttpServlet {

    private DeliveryDAO deliveryDAO;

    @Override
    public void init() throws ServletException {
        deliveryDAO = new DeliveryDAO(); // Khởi tạo DeliveryDAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy thông tin từ form gửi lên
        String orderIdParam = request.getParameter("orderId");
        String newStatus = request.getParameter("newStatus");
        
        try {
            int orderId = Integer.parseInt(orderIdParam);

            // Gọi method DAO để cập nhật trạng thái đơn hàng
            boolean isUpdated = deliveryDAO.updateOrderStatus(orderId, newStatus);
            
            if (isUpdated) {
                response.getWriter().write("Cập nhật trạng thái đơn hàng thành công!");
            } else {
                response.getWriter().write("Không thể cập nhật trạng thái đơn hàng.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("Mã đơn hàng không hợp lệ.");
        }
    }
}
