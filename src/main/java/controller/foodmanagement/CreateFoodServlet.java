package controller.foodmanagement;

import DAO.FoodManagementDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.FoodCategories;

@WebServlet(name = "CreateFoodServlet", urlPatterns = {"/CreateFood"})
public class CreateFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FoodManagementDAO dao = new FoodManagementDAO();
        List<FoodCategories> categories = dao.getAllCategories();

        request.setAttribute("categories", categories);
        request.getRequestDispatcher("createFood.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String dishName = request.getParameter("dishName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int categoryId = Integer.parseInt(request.getParameter("category"));

            FoodManagementDAO dao = new FoodManagementDAO();
            boolean success = dao.addDish(dishName, description, price, categoryId, LocalDateTime.now(), LocalDateTime.now());

            if (success) {
                response.sendRedirect("FoodManagement");
            } else {
                response.getWriter().write("Failed to add dish. Check server logs for details.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.getWriter().write("Invalid input format: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
