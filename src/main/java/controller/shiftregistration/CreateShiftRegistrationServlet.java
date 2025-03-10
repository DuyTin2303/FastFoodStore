package controller.shiftregistration;

import DAO.ShiftRegistrationsDAO;
import DAO.StaffScheduleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet xử lý tạo đơn đăng ký ca mới
 *
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name = "CreateShiftRegistrationServlet", urlPatterns = {"/ShiftRegistration/createShiftRegistration"})
public class CreateShiftRegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/ShiftRegistration/createShiftRegistration.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu POST để tạo đơn đăng ký ca mới
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 🔹 Lấy dữ liệu từ form
        String employeeName = request.getParameter("employeeName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String shiftTime = request.getParameter("shiftTime");
        String weekdays = request.getParameter("weekdays");
        String notes = request.getParameter("notes");

 ShiftRegistrationsDAO dao = new ShiftRegistrationsDAO();
    boolean success = dao.insertShiftRegistration(employeeName, startDate, endDate, shiftTime, weekdays, notes);

        // 🔹 Điều hướng sau khi xử lý
        if (success) {
            request.setAttribute("message", "✅ Tạo đơn đăng ký ca thành công!");
        } else {
            request.setAttribute("message", "❌ Tạo đơn đăng ký ca thất bại! Vui lòng kiểm tra lại thông tin.");
        }

        // Chuyển hướng về trang hiển thị lịch làm việc hoặc trang tạo đơn đăng ký
         response.sendRedirect(request.getContextPath() + "/ShiftRegistration/createShiftRegistration");
    }
}
