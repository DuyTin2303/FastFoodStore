package controller.rolemanagement;

import DAO.RoleManagementDAO;
import model.Users;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AssignRoleServlet", urlPatterns = {"/AssignRole"})
public class AssignRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId;
        try {
            userId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect("/RoleManagement");
            return;
        }

        RoleManagementDAO dao = new RoleManagementDAO();
        Users user = dao.getUserById(userId);
        List<String> roles = dao.getPredefinedRoles();

        if (user == null) {
            response.sendRedirect("/RoleManagement");
            return;
        }

        request.setAttribute("user", user);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("assignRole.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int userId = Integer.parseInt(request.getParameter("user_id"));
            String newRole = request.getParameter("role");
            RoleManagementDAO dao = new RoleManagementDAO();
            dao.assignRole(userId, newRole);

            response.sendRedirect("/FastFoodStore/RoleManagement");
        } catch (NumberFormatException e) {
            response.sendRedirect("/FastFoodStore/RoleManagement");
        }

    }
}
