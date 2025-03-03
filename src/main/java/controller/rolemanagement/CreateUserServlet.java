package controller.rolemanagement;

import DAO.RoleManagementDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateUserServlet", urlPatterns = {"/CreateUser"})
public class CreateUserServlet extends HttpServlet {

    private final RoleManagementDAO dao = new RoleManagementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> roles = dao.getPredefinedRoles();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("createUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String role = request.getParameter("role");

        if (username == null || password == null || email == null || role == null
                || username.isEmpty() || password.isEmpty() || email.isEmpty() || role.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            doGet(request, response);
            return;
        }

        try {
            boolean success = dao.createUser(username, password, email, fullName, phoneNumber, address, role);
            if (success) {
                response.sendRedirect("RoleManagement");
            }
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }

}
