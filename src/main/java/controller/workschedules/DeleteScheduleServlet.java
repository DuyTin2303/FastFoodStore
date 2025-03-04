package controller.workschedules;

import DAO.StaffScheduleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet xử lý xóa ca làm việc
 */
@WebServlet(name = "DeleteScheduleServlet", urlPatterns = {"/DeleteSchedule"})
public class DeleteScheduleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String scheduleId = request.getParameter("scheduleId");
        String message = "Xóa ca làm thất bại!";

        if (scheduleId != null) {
            StaffScheduleDAO scheduleDAO = new StaffScheduleDAO();
            try {
                int shiftId = Integer.parseInt(scheduleId);
                scheduleDAO.deleteSchedule(shiftId);
                message = "Xóa ca làm thành công!";
            } catch (NumberFormatException e) {
                Logger.getLogger(DeleteScheduleServlet.class.getName()).log(Level.SEVERE, "ID không hợp lệ", e);
                message = "ID ca làm không hợp lệ!";
            } catch (SQLException e) {
                Logger.getLogger(DeleteScheduleServlet.class.getName()).log(Level.SEVERE, "Lỗi khi xóa ca làm", e);
                message = "Lỗi hệ thống khi xóa ca làm!";
            }
        } else {
            message = "ID ca làm không được để trống!";
        }

        // Sử dụng query parameter để hiển thị thông báo
        response.sendRedirect("/staffSchedule");
    }
}
