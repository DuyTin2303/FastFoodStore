package controller.foodcategory;

import DAO.FoodCategoryManagementDAO;
import model.FoodCategories;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FoodCategoryManagementServlet", urlPatterns = {"/FoodCategoryManagement"})
public class FoodCategoryManagementServlet extends HttpServlet {
    
    private final FoodCategoryManagementDAO dao = new FoodCategoryManagementDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<FoodCategories> categories = dao.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("foodCategoryManagement/foodCategoryManagement.jsp").forward(request, response);
    }
}
