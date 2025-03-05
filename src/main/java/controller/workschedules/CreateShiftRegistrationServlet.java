package controller.workschedules;

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
@WebServlet(name = "CreateShiftRegistrationServlet", urlPatterns = {"/createShiftRegistration"})
public class CreateShiftRegistrationServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.getRequestDispatcher("createShiftRegistration.jsp").forward(request, response);
}

    /**
     * Xử lý yêu cầu POST để tạo đơn đăng ký ca mới
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 🔹 Lấy dữ liệu từ form
        String employeeName = request.getParameter("employeeName");
        String shiftDate = request.getParameter("shiftDate");
        String shiftTime = request.getParameter("shiftTime");
        String notes = request.getParameter("notes");

        // 🔹 Gọi phương thức DAO để chèn dữ liệu
        StaffScheduleDAO dao = new StaffScheduleDAO();
        boolean success = dao.insertShiftRegistration(employeeName, shiftDate, shiftTime, notes);

        // 🔹 Điều hướng sau khi xử lý
        if (success) {
            request.setAttribute("message", "✅ Tạo đơn đăng ký ca thành công!");
        } else {
            request.setAttribute("message", "❌ Tạo đơn đăng ký ca thất bại! Vui lòng kiểm tra lại thông tin.");
        }

        // Chuyển hướng về trang hiển thị lịch làm việc hoặc trang tạo đơn đăng ký
        request.getRequestDispatcher("staffView.jsp").forward(request, response);
    }
}
