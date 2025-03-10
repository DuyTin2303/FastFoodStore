package controller.shiftregistration;

import DAO.ShiftRegistrationsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ApproveShiftRegistrationServlet", urlPatterns = {"/ShiftRegistration/approveShiftRegistration"})
public class ApproveShiftRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the registration ID from the request parameters
        int registrationId = Integer.parseInt(request.getParameter("registrationId"));

        // Set the registration ID as a request attribute for use in the JSP
        request.setAttribute("registrationId", registrationId);

        // Forward the request to the JSP for approval
        request.getRequestDispatcher("/ShiftRegistration/approveShiftRegistration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the registration ID from the request parameters
        int registrationId = Integer.parseInt(request.getParameter("registrationId"));

        // Get the managerId from the session (assuming the user is logged in as Manager)
        HttpSession session = request.getSession(false);
        Integer managerId = (Integer) session.getAttribute("userId"); // Get manager's ID from session

        if (managerId == null) {
            // Handle case where managerId is not found (e.g., user is not logged in as manager)
            System.out.println("Manager not logged in, redirecting to login page.");
            response.sendRedirect(request.getContextPath() + "/loginView.jsp");
            return;
        }

        ShiftRegistrationsDAO shiftRegistrationsDAO = new ShiftRegistrationsDAO();
        boolean isApproved = shiftRegistrationsDAO.approveShiftRegistration(registrationId, request.getSession());

        // Redirect based on whether the approval was successful
        if (isApproved) {
            // Log the success
            System.out.println("Shift registration with ID " + registrationId + " approved successfully.");
            // Redirect to the view shift registration page if approved
            response.sendRedirect(request.getContextPath() + "/ShiftRegistration/viewShiftRegistration");
        } else {
            // Log the failure
            System.out.println("Failed to approve shift registration with ID " + registrationId + ".");
            // Redirect back to the approval page with an error message if something went wrong
            response.sendRedirect(request.getContextPath() + "/ShiftRegistration/approveShiftRegistration.jsp?message=Đã+có+lỗi+xảy+ra+khi+duyệt+đơn+đăng+ký.");
        }
    }
}
