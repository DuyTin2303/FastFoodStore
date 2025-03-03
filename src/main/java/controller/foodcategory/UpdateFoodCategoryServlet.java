package controller.foodcategory;

import DAO.FoodCategoryManagementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateFoodCategoryServlet", urlPatterns = {"/UpdateFoodCategory"})
public class UpdateFoodCategoryServlet extends HttpServlet {
    
    private final FoodCategoryManagementDAO dao = new FoodCategoryManagementDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryIdParam = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String errorMessage = null;

        if (categoryIdParam != null && name != null && !name.trim().isEmpty() && description != null) {
            try {
                int categoryId = Integer.parseInt(categoryIdParam);
                boolean success = dao.updateCategory(categoryId, name, description);
                
                if (!success) {
                    errorMessage = "Failed to update category. Please try again.";
                }
            } catch (NumberFormatException e) {
                errorMessage = "Invalid category ID format.";
            } catch (Exception e) { 
                if (e.getMessage().contains("Violation of UNIQUE KEY constraint")) {
                    errorMessage = "This category name already exists. Please choose a different name.";
                } else {
                    errorMessage = "An unexpected error occurred: " + e.getMessage();
                }
            }
        } else {
            errorMessage = "Category name and description cannot be empty.";
        }

        if (errorMessage != null) {
            // Forward back to update page with error message
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/updateFoodCategory.jsp?id=" + categoryIdParam).forward(request, response);
        } else {
            // Redirect only on success
            response.sendRedirect("FoodCategoryManagement");
        }
    }
}
