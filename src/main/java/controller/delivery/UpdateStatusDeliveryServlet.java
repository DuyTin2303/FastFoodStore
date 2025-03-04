package controller.delivery;

import DAO.DeliveryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Servlet xử lý cập nhật trạng thái đơn giao hàng bằng AJAX.
 * @author Vo Truong Qui
 */
@WebServlet(name="UpdateStatusDeliveryServlet", urlPatterns={"/updateStatusDelivery"})
public class UpdateStatusDeliveryServlet extends HttpServlet {
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String assignmentIdStr = request.getParameter("assignmentId");
        String newStatus = request.getParameter("status");

        try (PrintWriter out = response.getWriter()) {
            if (assignmentIdStr == null || newStatus == null || assignmentIdStr.isEmpty() || newStatus.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"message\": \"Dữ liệu không hợp lệ!\"}");
                return;
            }

            int assignmentId = Integer.parseInt(assignmentIdStr);
            DeliveryDAO deliveryDAO = new DeliveryDAO();
            boolean isUpdated = deliveryDAO.updateDeliveryStatus(assignmentId, newStatus);

            if (isUpdated) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("{\"message\": \"Cập nhật thành công!\", \"status\": \"" + newStatus + "\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"message\": \"Cập nhật thất bại!\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"ID không hợp lệ!\"}");
        }
    }
}
