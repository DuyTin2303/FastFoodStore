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
            <form action="CreateFood" method="post" id="foodForm"  enctype="multipart/form-data">
                <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
                %>
                <div class="alert alert-danger"><%= errorMessage %></div>
                <%
                    }
                %>

                <div class="form-group text-center">
                    <label for="dishImage">Dish Image</label>
                    <input type="file" class="form-control" id="dishImage" name="dishImage" required>

                    <!-- Centered Image Preview -->
                    <div class="d-flex justify-content-center mt-3">
                        <img id="imagePreview" src="#" alt="Image Preview" 
                             style="display: none; width: 200px; height: auto; border: 1px solid #ddd; padding: 5px; border-radius: 10px;">
                    </div>
                </div>



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
            document.getElementById("dishImage").addEventListener("change", function (event) {
                var reader = new FileReader();
                reader.onload = function () {
                    var preview = document.getElementById("imagePreview");
                    preview.src = reader.result;
                    preview.style.display = "block";
                };
                reader.readAsDataURL(event.target.files[0]);
            });

            document.getElementById("clearForm").addEventListener("click", function () {
                document.getElementById("foodForm").reset();
                document.getElementById("imagePreview").style.display = "none";
            });
        </script>
    </body>
</html>
