/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.foodcategory;

import DAO.FoodCategoryManagementDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteFoodCategoryServlet", urlPatterns = {"/DeleteFoodCategory"})
public class DeleteFoodCategoryServlet extends HttpServlet {
    
    private final FoodCategoryManagementDAO dao = new FoodCategoryManagementDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryIdParam = request.getParameter("id");
        
        if (categoryIdParam != null) {
            try {
                int categoryId = Integer.parseInt(categoryIdParam);
                dao.deleteCategory(categoryId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid category ID: " + e.getMessage());
            }
        }
        
        response.sendRedirect("FoodCategoryManagement");
    }
}