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
import model.Dishes;
import model.FoodCategories;

@WebServlet(name = "UpdateFoodServlet", urlPatterns = {"/UpdateFood"})
public class UpdateFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int dishId = Integer.parseInt(request.getParameter("id"));
            FoodManagementDAO dao = new FoodManagementDAO();
            Dishes dish = dao.getDishById(dishId);
            List<FoodCategories> categories = dao.getAllCategories();

            if (dish == null) {
                request.setAttribute("error", "Dish not found.");
            } else {
                request.setAttribute("dish", dish);
                request.setAttribute("categories", categories);
            }

            request.getRequestDispatcher("updateFood.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("foodManagement.jsp?error=Invalid dish ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            String dishName = request.getParameter("dishName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int categoryId = Integer.parseInt(request.getParameter("category"));

            FoodManagementDAO dao = new FoodManagementDAO();
            dao.updateDish(dishId, dishName, description, price, categoryId, LocalDateTime.now());

            response.sendRedirect("FoodManagement");
        } catch (NumberFormatException e) {
            response.sendRedirect("foodManagement.jsp?error=Invalid input");
        }
    }
}
