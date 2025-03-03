<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.FoodCategories" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Create Food</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="mb-4">Create Food</h1>
            <form action="CreateFood" method="post" id="foodForm">
                <div class="form-group">
                    <label for="dishName">Dish Name</label>
                    <input type="text" class="form-control" id="dishName" name="dishName" required>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea class="form-control" id="description" name="description" required></textarea>
                </div>

                <div class="form-group">
                    <label for="price">Price</label>
                    <input type="number" step="0.01" class="form-control" id="price" name="price" required>
                </div>

                <div class="form-group">
                    <label for="category">Category</label>
                    <select class="form-control" id="category" name="category" required>
                        <%
                            List<FoodCategories> categories = (List<FoodCategories>) request.getAttribute("categories");
                            if (categories != null && !categories.isEmpty()) {
                                for (FoodCategories category : categories) {
                        %>
                        <option value="<%= category.getCategoryId() %>"><%= category.getCategoryName() %></option>
                        <%
                                }
                            } else {
                        %>
                        <option value="">No categories available</option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Create</button>
                <button type="button" class="btn btn-secondary" id="clearForm">Cancel</button>
            </form>
        </div>
        <script>
            document.getElementById("clearForm").addEventListener("click", function () {
                document.getElementById("foodForm").reset();
            });
        </script>
    </body>
</html>
