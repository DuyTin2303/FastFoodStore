package controller.shiftregistration;

import DAO.ShiftRegistrationsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name = "DeleteWorkShiftRegistrationsForStaffServlet", urlPatterns = {"/ShiftRegistration/deleteWorkShiftRegistrationsForStaff"})
public class DeleteWorkShiftRegistrationsForStaffServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy registrationId từ request
        String idParam = request.getParameter("registrationId");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu registrationId");
            return;
        }

        try {
            int registrationId = Integer.parseInt(idParam);
            ShiftRegistrationsDAO dao = new ShiftRegistrationsDAO();
            boolean success = dao.deleteShiftRegistration(registrationId);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("✅ Xóa đăng ký ca làm thành công.");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("❌ Không thể xóa đăng ký ca làm. Có thể trạng thái không hợp lệ.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "registrationId không hợp lệ.");
        }
    }
}
