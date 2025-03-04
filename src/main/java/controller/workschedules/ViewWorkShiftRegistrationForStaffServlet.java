package controller.workschedules;

import DAO.StaffScheduleDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ShiftRegistration;

/**
 * Servlet để xem danh sách đăng ký ca làm cho nhân viên cụ thể
 */
@WebServlet(name = "ViewWorkShiftRegistrationForStaffServlet", urlPatterns = {"/ManageWorkScheduleforStaff/viewWorkShiftRegistrationForStaff"})
public class ViewWorkShiftRegistrationForStaffServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi Servlet
     * @throws IOException nếu có lỗi I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String employeeName = request.getParameter("employeeName");

        if (employeeName == null || employeeName.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập tên nhân viên.");
            request.getRequestDispatcher("/ManageWorkScheduleforStaff/viewWorkShiftRegistrationForStaff.jsp")
                    .forward(request, response);
            return;
        }

        StaffScheduleDAO dao = new StaffScheduleDAO();
        List<ShiftRegistration> registrations = dao.getShiftRegistrationsByEmployeeName(employeeName);

        request.setAttribute("registrations", registrations);
        request.setAttribute("employeeName", employeeName);

        request.getRequestDispatcher("/ManageWorkScheduleforStaff/viewWorkShiftRegistrationForStaff.jsp")
                .forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách đăng ký ca làm cho nhân viên cụ thể";
    }
}
