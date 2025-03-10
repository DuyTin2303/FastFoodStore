package controller.delivery;

import DAO.DeliveryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AssignDeliveryManagerServlet", urlPatterns = {"/assignDeliveryManager"})
public class AssignDeliveryManagerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId"); // Đổi thành "orderId" để khớp với AJAX

        if (orderIdStr != null) {
            try {
                int orderId = Integer.parseInt(orderIdStr);

                DeliveryDAO deliveryDAO = new DeliveryDAO();
                boolean isAssigned = deliveryDAO.assignSingleDelivery(orderId);

                response.setContentType("text/plain");
                PrintWriter out = response.getWriter();

                if (isAssigned) {
                    out.println("success"); // Phản hồi "success" để khớp với logic trên trang JSP
                } else {
                    out.println("Không tìm thấy ca làm việc phù hợp hoặc đơn hàng đã được phân công.");
                }
                out.flush();
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Mã đơn hàng không hợp lệ.");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Lỗi khi phân công đơn hàng: " + e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Thiếu tham số orderId.");
        }
    }
}
