<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create Food Category</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-4">
            <h1 class="mb-4">Create Food Category</h1>
            <form action="/CreateFoodCategory" method="post" id="categoryForm">
                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% if (errorMessage != null) { %>
                <div class="alert alert-danger"><%= errorMessage %></div>
                <% } %>

                <div class="form-group">
                    <label class="form-label">Category Name:</label>
                    <input type="text" name="name" class="form-control" required>
                </div>

                <div class="mt-3">
                    <button type="submit" class="btn btn-primary">Create</button>
                    <button type="button" class="btn btn-secondary" id="clearForm">Cancel</button>
                </div>
            </form> 
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById("clearForm").addEventListener("click", function () {
            document.getElementById("categoryForm").reset();
        });
    </script>     
</body>
</html>
