package controller.foodmanagement;

import DAO.FoodManagementDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.FoodCategories;
import static utils.HandleImage.saveImage;

@WebServlet(name = "CreateFoodServlet", urlPatterns = {"/CreateFood"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class CreateFoodServlet extends HttpServlet {

    private final FoodManagementDAO dao = new FoodManagementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<FoodCategories> categories = dao.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/foodManagement/createFood.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dishName = request.getParameter("dishName");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String categoryIdStr = request.getParameter("category");
        String errorMessage = null;

        if (dishName == null || dishName.trim().isEmpty()) {
            errorMessage = "Dish name cannot be empty.";
        } else if (description == null || description.trim().isEmpty()) {
            errorMessage = "Description cannot be empty.";
        } else if (priceStr == null || priceStr.trim().isEmpty()) {
            errorMessage = "Price cannot be empty.";
        } else if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            errorMessage = "Category must be selected.";
        }

        double price = 0;
        int categoryId = 0;
        if (errorMessage == null) {
            try {
                price = Double.parseDouble(priceStr);
                categoryId = Integer.parseInt(categoryIdStr);
                if (price <= 0) {
                    errorMessage = "Price must be greater than zero.";
                }
            } catch (NumberFormatException e) {
                errorMessage = "Invalid price or category format.";
            }
        }

        Part imagePart = null;
        String imageName = null;
        if (errorMessage == null) {
            try {
                imagePart = request.getPart("dishImage");
                imageName = saveImage(imagePart, request);
                if (imageName == null) {
                    errorMessage = "Failed to upload image. Please try again.";
                }
            } catch (Exception e) {
                errorMessage = "An error occurred while processing the image.";
            }
        }

        if (errorMessage == null) {
            try {
                boolean success = dao.addDish(dishName, description, price, categoryId, LocalDateTime.now(), LocalDateTime.now(), imageName);
                if (!success) {
                    errorMessage = "Failed to add dish. Please try again.";
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("Violation of UNIQUE KEY constraint")) {
                    errorMessage = "A dish with this name already exists. Please choose a different name.";
                } else {
                    errorMessage = "An error occurred while adding the dish.";
                }
            }
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            doGet(request, response);
        } else {
            response.sendRedirect("FoodManagement");
        }
    }
}
