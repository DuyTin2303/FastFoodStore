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
 *
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name="DeleteScheduleServlet", urlPatterns={"/DeleteSchedule"})
public class DeleteScheduleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String scheduleId = request.getParameter("scheduleId");

        if (scheduleId != null) {
            StaffScheduleDAO scheduleDAO = new StaffScheduleDAO();
            try {
                scheduleDAO.deleteSchedule(Integer.parseInt(scheduleId));
                request.setAttribute("message", "Xóa ca làm thành công!");
            } catch (SQLException e) {
                request.setAttribute("message", "Xóa ca làm thất bại!");
            }
        }

        response.sendRedirect("staffSchedule"); // Load lại trang sau khi xóa
    }
}
