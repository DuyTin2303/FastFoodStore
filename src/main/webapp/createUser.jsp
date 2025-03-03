<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Create User</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script>
            function clearForm() {
                document.getElementById("createUserForm").reset();
            }
        </script>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="mb-4">Create New User</h1>
            <% String error = (String) request.getAttribute("error");
           if (error != null) { %>
            <p class="text-danger"><%= error %></p>
            <% } %>
            <form id="createUserForm" action="CreateUser" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" required>
                </div>

                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required>
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" class="form-control" id="address" name="address" required>
                </div>

                <div class="form-group">
                    <label for="role">Role</label>
                    <select class="form-control" id="role" name="role" required>
                        <% 
                            List<String> roles = (List<String>) request.getAttribute("roles");
                            if (roles != null) {
                                for (String role : roles) { %>
                        <option value="<%= role %>"><%= role %></option>
                        <%      }
                            }
                        %>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Create User</button>
                <button type="button" class="btn btn-secondary" onclick="clearForm()">Cancel</button>
            </form>
        </div>
    </body>
</html>
