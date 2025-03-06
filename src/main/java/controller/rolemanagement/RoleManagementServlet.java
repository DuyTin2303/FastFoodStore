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

@WebServlet(name = "RoleManagementServlet", urlPatterns = {"/RoleManagement"})
public class RoleManagementServlet extends HttpServlet {

    private final RoleManagementDAO dao = new RoleManagementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Users> userList = dao.getUsers();

        request.setAttribute("users", userList);

        request.getRequestDispatcher("/roleManagement/userList.jsp").forward(request, response);
    }
}
