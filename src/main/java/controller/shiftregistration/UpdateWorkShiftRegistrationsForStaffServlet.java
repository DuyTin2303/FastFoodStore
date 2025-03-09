package controller.shiftregistration;

import DAO.ShiftRegistrationsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ShiftRegistration;

import java.io.IOException;

@WebServlet(name = "UpdateWorkShiftRegistrationsForStaffServlet", urlPatterns = {"/ShiftRegistration/updateWorkShiftRegistrationsForStaff"})
public class UpdateWorkShiftRegistrationsForStaffServlet extends HttpServlet {

    // Xử lý yêu cầu GET: Lấy thông tin ca làm theo ID để hiển thị trên form cập nhật
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int registrationId = Integer.parseInt(idParam);
                ShiftRegistrationsDAO dao = new ShiftRegistrationsDAO();
                ShiftRegistration registration = dao.getShiftRegistrationById(registrationId);

                if (registration != null) {
                    request.setAttribute("shift", registration);  // Đưa đối tượng vào request
                    request.getRequestDispatcher("updateWorkShiftRegistrationsForStaff.jsp").forward(request, response);
                } else {
                    response.sendRedirect("viewWorkShiftRegistrationsForStaff.jsp?message=not_found");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("viewWorkShiftRegistrationsForStaff.jsp?message=invalid_id");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("viewWorkShiftRegistrationsForStaff.jsp?message=error");
            }
        } else {
            response.sendRedirect("viewWorkShiftRegistrationsForStaff.jsp?message=id_missing");
        }
    }

    // Xử lý yêu cầu POST: Cập nhật thông tin ca làm việc
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Lấy dữ liệu từ request
            int registrationId = Integer.parseInt(request.getParameter("id"));
            String employeeName = request.getParameter("employeeName");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String shiftTime = request.getParameter("shiftTime");
            String weekdays = request.getParameter("weekdays");
            String requestStatus = request.getParameter("requestStatus");
            String notes = request.getParameter("notes");
            String approvalDate = request.getParameter("approvalDate");

            // Gọi DAO để cập nhật dữ liệu (KHÔNG cần truyền managerName)
            ShiftRegistrationsDAO dao = new ShiftRegistrationsDAO();
            boolean updateSuccess = dao.updateShiftRegistration(registrationId, employeeName, startDate, endDate, shiftTime,
                    weekdays, requestStatus, notes, approvalDate);

            if (updateSuccess) {
                response.sendRedirect("/ShiftRegistration/viewWorkShiftRegistrationsForStaff");
            } else {
                response.sendRedirect("updateWorkShiftRegistrationsForStaff.jsp?id=" + registrationId + "&message=update_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("viewWorkShiftRegistrationsForStaff.jsp?message=invalid_data");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewWorkShiftRegistrationsForStaff.jsp?message=error");
        }
    }
}
