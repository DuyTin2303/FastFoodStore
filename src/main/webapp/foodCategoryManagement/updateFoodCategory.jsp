<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAO.FoodCategoryManagementDAO"%>
<%@page import="model.FoodCategories"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Food Category</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-4">
            <h1 class="mb-4">Update Food Category</h1>

            <% 
                String categoryIdParam = request.getParameter("id");
                FoodCategories category = null;
                String errorMessage = (String) request.getAttribute("errorMessage");

                if (errorMessage != null) {
            %>
            <div class="alert alert-danger"><%= errorMessage %></div>
            <%
                }

                if (categoryIdParam != null) {
                    try {
                        int categoryId = Integer.parseInt(categoryIdParam);
                        FoodCategoryManagementDAO dao = new FoodCategoryManagementDAO();
                        category = dao.getCategoryById(categoryId);
                    } catch (NumberFormatException e) {
            %>
            <div class="alert alert-danger">Invalid category ID format.</div>
            <%
                    }
                }

                if (category != null) {
            %>

            <form action="/UpdateFoodCategory" method="post" id="categoryForm">
                <input type="hidden" name="id" value="<%= category.getCategoryId() %>">

                <div class="form-group">
                    <label class="form-label">Category Name:</label>
                    <input type="text" name="name" class="form-control" value="<%= category.getCategoryName() %>" required>
                </div>

                <div class="mt-3">
                    <button type="submit" class="btn btn-primary">Update</button>
                    <button type="button" class="btn btn-secondary" id="clearForm">Cancel</button>
                </div>
            </form>

            <%
                } else {
            %>
            <div class="alert alert-warning">Category not found.</div>
            <%
                }
            %>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            document.getElementById("clearForm").addEventListener("click", function () {
                document.getElementById("categoryForm").reset();
            });
        </script>
    </body>
</html>
