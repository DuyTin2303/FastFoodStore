/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.foodmanagement;

import DAO.FoodCategoryManagementDAO;
import DAO.FoodManagementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 */
@WebServlet(name = "DeleteFoodServlet", urlPatterns = {"/DeleteFood"})
public class DeleteFoodServlet extends HttpServlet {

    private final FoodManagementDAO dao = new FoodManagementDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dishId = request.getParameter("id");
        
        if (dishId != null) {
            try {
                int id = Integer.parseInt(dishId);
                dao.deleteDish(id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid category ID: " + e.getMessage());
            }
        }
        
        response.sendRedirect("FoodManagement");
    }
}
