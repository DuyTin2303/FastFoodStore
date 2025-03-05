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
        String assignmentIdStr = request.getParameter("assignmentId");
        
        if (assignmentIdStr != null) {
            int assignmentId = Integer.parseInt(assignmentIdStr);
            
            // Giả sử bạn có DAO hoặc service để xử lý logic này
            DeliveryDAO deliveryDAO = new DeliveryDAO();
            boolean isAssigned = deliveryDAO.assignSingleDelivery(assignmentId);
            
            // Cập nhật thông báo sau khi thực hiện
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            
            if (isAssigned) {
                out.println("Đơn hàng đã được gán thành công!");
            } else {
                out.println("Có lỗi xảy ra khi gán đơn hàng!");
            }
            out.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
