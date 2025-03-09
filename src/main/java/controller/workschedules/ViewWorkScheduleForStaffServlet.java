package controller.workschedules;

import DAO.StaffScheduleDAO;
import model.StaffSchedule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name="ViewWorkScheduleForStaffServlet", urlPatterns={"/StaffSchedule/viewWorkScheduleForStaff"})
public class ViewWorkScheduleForStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the userId from the session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // If userId is not found in session, redirect to login page (user is not logged in)
            response.sendRedirect("loginView.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId"); // Retrieve userId from session

        // Instantiate the DAO to fetch schedules
        StaffScheduleDAO dao = new StaffScheduleDAO();

        // Get the list of schedules for the given userId
        List<StaffSchedule> schedules = dao.getAllSchedules(userId);

        // Set the schedules as a request attribute to pass to the JSP
        request.setAttribute("schedules", schedules);

        // Forward the request to the JSP for displaying the schedules
        request.getRequestDispatcher("/StaffSchedule/viewWorkScheduleForStaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests (for example, form submissions if needed)
        doGet(request, response);
    }
}
