package controller.workschedules;

import DAO.StaffScheduleDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet xử lý cập nhật lịch làm việc cho nhân viên
 *
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name = "UpdateStaffScheduleServlet", urlPatterns = {"/updateStaffSchedule"})
public class UpdateStaffScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // Chuyển hướng tất cả GET requests thành POST requests
    }

    /**
     * Phương thức xử lý yêu cầu POST để cập nhật lịch làm việc của nhân viên.
     *
     * @param request Yêu cầu từ client
     * @param response Phản hồi từ server
     * @throws ServletException Nếu xảy ra lỗi servlet
     * @throws IOException Nếu xảy ra lỗi I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Kiểm tra nếu scheduleId không tồn tại hoặc rỗng
            String scheduleIdStr = request.getParameter("scheduleId");
            if (scheduleIdStr == null || scheduleIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("ID Lịch Làm Việc không hợp lệ.");
            }

            int scheduleId = Integer.parseInt(scheduleIdStr); // Chuyển đổi sang số nguyên

            String employeeName = request.getParameter("employeeName");
            String shiftDate = request.getParameter("shiftDate");
            String shiftTime = request.getParameter("shiftTime");
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");
            String managerName = request.getParameter("managerName");
            String replacementEmployeeName = request.getParameter("replacementEmployeeName");

            // Xử lý nếu replacementEmployeeName bị null hoặc rỗng
            if (replacementEmployeeName == null || replacementEmployeeName.trim().isEmpty()) {
                replacementEmployeeName = null;
            }

            // Gọi DAO để cập nhật lịch làm việc
            StaffScheduleDAO dao = new StaffScheduleDAO();
            boolean success = dao.updateSchedule(scheduleId, employeeName, shiftDate, shiftTime, status, notes, managerName, replacementEmployeeName);

            if (success) {
                request.setAttribute("message", "✅ Cập nhật lịch làm việc thành công!");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "❌ Cập nhật không thành công. Vui lòng kiểm tra lại!");
                request.setAttribute("messageType", "error");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("message", "❌ Lỗi: ID Lịch Làm Việc phải là số nguyên hợp lệ.");
            request.setAttribute("messageType", "error");
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "❌ " + e.getMessage());
            request.setAttribute("messageType", "error");
        } catch (Exception e) {
            request.setAttribute("message", "❌ Lỗi hệ thống: " + e.getMessage());
            request.setAttribute("messageType", "error");
        }

        // Trả về trang cập nhật với thông tin hiển thị
        request.getRequestDispatcher("/staffSchedule").forward(request, response);

    }
}
