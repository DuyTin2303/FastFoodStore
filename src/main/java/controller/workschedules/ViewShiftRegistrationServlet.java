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
 * Servlet để xem danh sách đơn đăng ký ca làm
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name = "ViewShiftRegistrationServlet", urlPatterns = {"/viewShiftRegistration"})
public class ViewShiftRegistrationServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffScheduleDAO dao = new StaffScheduleDAO();
      List<ShiftRegistration> registrations = dao.getAllShiftRegistrations();


        request.setAttribute("registrations", registrations);
        request.getRequestDispatcher("viewShiftRegistration.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách đơn đăng ký ca làm";
    }
}
