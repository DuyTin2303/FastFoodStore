/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.foodmanagement;

import DAO.FoodManagementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Dishes;

/**
 *
 */
@WebServlet(name = "FoodManagementServlet", urlPatterns = {"/FoodManagement"})
public class FoodManagementServlet extends HttpServlet {
    
    private final FoodManagementDAO dao = new FoodManagementDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Dishes> dishes = dao.getAllDishes();
        request.setAttribute("dishes", dishes);
        request.getRequestDispatcher("foodManagement.jsp").forward(request, response);
    }
}