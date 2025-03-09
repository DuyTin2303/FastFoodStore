package controller.workschedules;

import DAO.StaffScheduleDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.StaffSchedule;

@WebServlet(name = "StaffScheduleServlet", urlPatterns = {"/StaffSchedule/staffSchedule"})
public class StaffScheduleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffScheduleDAO dao = new StaffScheduleDAO();
        // Tự động cập nhật trạng thái ca làm việc
        dao.autoUpdateShiftStatus();
        
        List<StaffSchedule> schedules = dao.getAllSchedules();

        request.setAttribute("schedules", schedules);
        request.getRequestDispatcher("/StaffSchedule/staffSchedule.jsp").forward(request, response);
    }
}
