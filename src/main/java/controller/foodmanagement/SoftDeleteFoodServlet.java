package controller.foodmanagement;

import DAO.FoodManagementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SoftDeleteFoodServlet", urlPatterns = {"/SoftDeleteFood"})
public class SoftDeleteFoodServlet extends HttpServlet {

     private final FoodManagementDAO dao = new FoodManagementDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dishId = request.getParameter("id");
        
        if (dishId != null) {
            try {
                int id = Integer.parseInt(dishId);
                dao.softDeleteDish(id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid category ID: " + e.getMessage());
            }
        }
        
        response.sendRedirect("FoodManagement");
    }
}
