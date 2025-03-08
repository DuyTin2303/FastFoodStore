/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.foodcategory;

import DAO.FoodCategoryManagementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(name = "CreateFoodCategoryServlet", urlPatterns = {"/CreateFoodCategory"})
public class CreateFoodCategoryServlet extends HttpServlet {

    private final FoodCategoryManagementDAO dao = new FoodCategoryManagementDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String errorMessage = null;

        if (name != null && !name.trim().isEmpty()) {
            try {
                boolean success = dao.addCategory(name);
                if (!success) {
                    errorMessage = "Failed to add category. Please try again.";
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("Violation of UNIQUE KEY constraint")) {
                    errorMessage = "This category already exists. Please enter a different name.";
                } else {
                    errorMessage = "An error occurred while adding the category.";
                }
            }
        } else {
            errorMessage = "Category name cannot be empty.";
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/foodCategoryManagement/createFoodCategory.jsp").forward(request, response);
        } else {
            response.sendRedirect("FoodCategoryManagement");
        }
    }
}
