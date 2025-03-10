package controller.shiftregistration;


import model.ShiftRegistration;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.ShiftRegistrationsDAO;

/**
 * Servlet để xem danh sách đơn đăng ký ca làm
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name = "ViewShiftRegistrationServlet", urlPatterns = {"/ShiftRegistration/viewShiftRegistration"})
public class ViewShiftRegistrationServlet extends HttpServlet {


@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    ShiftRegistrationsDAO dao = new ShiftRegistrationsDAO();
    List<ShiftRegistration> registrations = dao.getAllShiftRegistrations();

    request.setAttribute("registrations", registrations);
    request.getRequestDispatcher("/ShiftRegistration/viewShiftRegistration.jsp").forward(request, response);
}


    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách đơn đăng ký ca làm";
    }
}