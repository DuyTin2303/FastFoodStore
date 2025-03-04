package controller.food;

import DAO.foodDAO;
import model.FoodItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a
 */
@WebServlet(name = "FoodManagementController", urlPatterns = {"/FoodManagementController"})
public class FoodManagementServlet extends HttpServlet {

    private final foodDAO foodDao = new foodDAO();
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30 days in seconds

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Always load these lists first
            List<FoodItem> foodList = foodDao.getAllFood();
            List<String> categories = foodDao.getAllCategories();

            // Debug output
            System.out.println("Debug: Found " + (foodList == null ? "null" : foodList.size()) + " food items");
            System.out.println("Debug: Found " + (categories == null ? "null" : categories.size()) + " categories");

            // Always set these attributes
            request.setAttribute("foodList", foodList);
            request.setAttribute("categories", categories);

            // Get user's last viewed category from cookie if available
            String lastViewedCategory = getCookieValue(request, "lastViewedCategory");
            if (lastViewedCategory != null && !lastViewedCategory.isEmpty()) {
                request.setAttribute("lastViewedCategory", lastViewedCategory);
            }

            // Process specific actions if present
            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case "filterCategory":
                        String category = request.getParameter("category");
                        if (category != null && !category.isEmpty()) {
                            foodList = foodDao.getFoodByCategory(category);
                            request.setAttribute("foodList", foodList);

                            // Save the selected category in a cookie
                            Cookie categoryCookie = new Cookie("lastViewedCategory", category);
                            categoryCookie.setMaxAge(COOKIE_MAX_AGE);
                            response.addCookie(categoryCookie);
                        }
                        break;
                    case "filterPrice":
                        String filterType = request.getParameter("filterType");

                        if (filterType != null) {
                            switch (filterType) {
                                case "range":
                                    String minPriceStr = request.getParameter("minPrice");
                                    String maxPriceStr = request.getParameter("maxPrice");

                                    if (minPriceStr != null && !minPriceStr.isEmpty()
                                            && maxPriceStr != null && !maxPriceStr.isEmpty()) {
                                        try {
                                            double minPrice = Double.parseDouble(minPriceStr);
                                            double maxPrice = Double.parseDouble(maxPriceStr);

                                            if (minPrice > maxPrice) {
                                                // Swap values if min > max
                                                double temp = minPrice;
                                                minPrice = maxPrice;
                                                maxPrice = temp;
                                                request.setAttribute("message", "Giá tối thiểu lớn hơn giá tối đa. Đã tự động điều chỉnh giá trị.");
                                            }

                                            foodList = foodDao.getFoodByPriceRange(minPrice, maxPrice);
                                            request.setAttribute("foodList", foodList);
                                            request.setAttribute("minPrice", minPrice);
                                            request.setAttribute("maxPrice", maxPrice);

                                            // Save the price range preferences in cookies
                                            Cookie minPriceCookie = new Cookie("minPrice", String.valueOf(minPrice));
                                            Cookie maxPriceCookie = new Cookie("maxPrice", String.valueOf(maxPrice));
                                            minPriceCookie.setMaxAge(COOKIE_MAX_AGE);
                                            maxPriceCookie.setMaxAge(COOKIE_MAX_AGE);
                                            response.addCookie(minPriceCookie);
                                            response.addCookie(maxPriceCookie);
                                        } catch (NumberFormatException e) {
                                            request.setAttribute("error", "Vui lòng nhập giá trị hợp lệ cho khoảng giá.");
                                        }
                                    }
                                    break;
                                case "asc":
                                    foodList = foodDao.getFoodByPriceAscending();
                                    request.setAttribute("foodList", foodList);
                                    request.setAttribute("sortOrder", "asc");

                                    // Save sorting preference in cookie
                                    Cookie sortOrderCookie = new Cookie("sortOrder", "asc");
                                    sortOrderCookie.setMaxAge(COOKIE_MAX_AGE);
                                    response.addCookie(sortOrderCookie);
                                    break;
                                case "desc":
                                    foodList = foodDao.getFoodByPriceDescending();
                                    request.setAttribute("foodList", foodList);
                                    request.setAttribute("sortOrder", "desc");

                                    // Save sorting preference in cookie
                                    sortOrderCookie = new Cookie("sortOrder", "desc");
                                    sortOrderCookie.setMaxAge(COOKIE_MAX_AGE);
                                    response.addCookie(sortOrderCookie);
                                    break;
                                default:
                                    // Default case - handle simple max price filter
                                    String maxPriceParam = request.getParameter("maxPrice");
                                    if (maxPriceParam != null && !maxPriceParam.isEmpty()) {
                                        try {
                                            double maxPrice = Double.parseDouble(maxPriceParam);
                                            foodList = foodDao.getFoodByPrice(maxPrice);
                                            request.setAttribute("foodList", foodList);
                                            request.setAttribute("maxPrice", maxPrice);

                                            // Save max price preference in cookie
                                            Cookie maxPriceCookie = new Cookie("maxPrice", String.valueOf(maxPrice));
                                            maxPriceCookie.setMaxAge(COOKIE_MAX_AGE);
                                            response.addCookie(maxPriceCookie);
                                        } catch (NumberFormatException e) {
                                            request.setAttribute("error", "Vui lòng nhập giá trị hợp lệ cho giá tối đa.");
                                        }
                                    }
                                    break;
                            }
                        } else {
                            // Handle the case when filterType is null but maxPrice is provided
                            String maxPriceStr = request.getParameter("maxPrice");
                            if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
                                try {
                                    double maxPrice = Double.parseDouble(maxPriceStr);
                                    foodList = foodDao.getFoodByPrice(maxPrice);
                                    request.setAttribute("foodList", foodList);
                                    request.setAttribute("maxPrice", maxPrice);

                                    // Save max price preference in cookie
                                    Cookie maxPriceCookie = new Cookie("maxPrice", String.valueOf(maxPrice));
                                    maxPriceCookie.setMaxAge(COOKIE_MAX_AGE);
                                    response.addCookie(maxPriceCookie);
                                } catch (NumberFormatException e) {
                                    request.setAttribute("error", "Vui lòng nhập giá trị hợp lệ cho giá tối đa.");
                                }
                            }
                        }
                        break;
                    case "search":
                        String name = request.getParameter("name");
                        if (name != null && !name.isEmpty()) {
                            List<FoodItem> foundItems = foodDao.searchFoodByName(name);
                            if (foundItems != null && !foundItems.isEmpty()) {
                                request.setAttribute("foodList", foundItems);
                                request.setAttribute("searchTerm", name);

                                // Save last search term in cookie
                                Cookie searchCookie = new Cookie("lastSearchTerm", name);
                                searchCookie.setMaxAge(COOKIE_MAX_AGE);
                                response.addCookie(searchCookie);
                            } else {
                                request.setAttribute("error", "Không tìm thấy món ăn với tên: " + name);
                            }
                        }
                        break;
                    case "viewReview":
                        String foodIdStr = request.getParameter("id");
                        if (foodIdStr != null && !foodIdStr.isEmpty()) {
                            try {
                                int foodId = Integer.parseInt(foodIdStr);
                                FoodItem selectedFood = foodDao.getFoodById(foodId);
                                if (selectedFood != null) {
                                    request.setAttribute("selectedFood", selectedFood);

                                    // Save last viewed food in cookie
                                    Cookie lastViewedFoodCookie = new Cookie("lastViewedFood", String.valueOf(foodId));
                                    lastViewedFoodCookie.setMaxAge(COOKIE_MAX_AGE);
                                    response.addCookie(lastViewedFoodCookie);
                                } else {
                                    request.setAttribute("error", "Không tìm thấy món ăn với ID: " + foodId);
                                }
                            } catch (NumberFormatException e) {
                                request.setAttribute("error", "ID món ăn không hợp lệ.");
                            }
                        }
                        break;
                    case "clearCookies":
                        // Clear all cookies related to food management
                        clearCookies(request, response);
                        request.setAttribute("message", "Đã xóa tất cả cookie thành công.");
                        break;
                }
            } else {
                // If no action parameter, try to restore previous state from cookies
                restoreStateFromCookies(request, response);
            }
        } catch (Exception e) {
            System.out.println("Debug - Error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error processing request: " + e.getMessage());
        }

        request.getRequestDispatcher("foodManagementView.jsp").forward(request, response);
    }

    /**
     * Helper method to get a cookie value by name
     *
     * @param request The HTTP request
     * @param name The name of the cookie
     * @return The cookie value or null if not found
     */
    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Helper method to clear all food management related cookies
     *
     * @param request The HTTP request
     * @param response The HTTP response
     */
    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("lastViewedCategory") || name.equals("minPrice")
                        || name.equals("maxPrice") || name.equals("sortOrder")
                        || name.equals("lastSearchTerm") || name.equals("lastViewedFood")) {
                    cookie.setMaxAge(0); // Set age to 0 to delete
                    cookie.setValue("");
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * Helper method to restore state from cookies when no action is specified
     *
     * @param request The HTTP request
     * @param response The HTTP response
     */
    private void restoreStateFromCookies(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FoodItem> foodList = null;
        foodDAO dao = new foodDAO();

        // Check for category filter
        String category = getCookieValue(request, "lastViewedCategory");
        if (category != null && !category.isEmpty()) {
            foodList = dao.getFoodByCategory(category);
            request.setAttribute("lastViewedCategory", category);
        }

        // Check for sort order
        String sortOrder = getCookieValue(request, "sortOrder");
        if (sortOrder != null) {
            if (sortOrder.equals("asc")) {
                foodList = dao.getFoodByPriceAscending();
                request.setAttribute("sortOrder", "asc");
            } else if (sortOrder.equals("desc")) {
                foodList = dao.getFoodByPriceDescending();
                request.setAttribute("sortOrder", "desc");
            }
        }

        // Check for price range
        String minPrice = getCookieValue(request, "minPrice");
        String maxPrice = getCookieValue(request, "maxPrice");
        if (minPrice != null && maxPrice != null) {
            try {
                double min = Double.parseDouble(minPrice);
                double max = Double.parseDouble(maxPrice);
                foodList = dao.getFoodByPriceRange(min, max);
                request.setAttribute("minPrice", min);
                request.setAttribute("maxPrice", max);
            } catch (NumberFormatException e) {
                // Ignore invalid price format in cookies
            }
        } else if (maxPrice != null) {
            try {
                double max = Double.parseDouble(maxPrice);
                foodList = dao.getFoodByPrice(max);
                request.setAttribute("maxPrice", max);
            } catch (NumberFormatException e) {
                // Ignore invalid price format in cookies
            }
        }

        // Set the food list if we've filtered it
        if (foodList != null) {
            request.setAttribute("foodList", foodList);
        }
    }
}
