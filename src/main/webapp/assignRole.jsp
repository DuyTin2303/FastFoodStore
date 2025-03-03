<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Users, java.util.List" %>

<%
    Users user = (Users) request.getAttribute("user"); // User object from servlet
    List<String> roles = (List<String>) request.getAttribute("roles"); // Role list from servlet
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Assign Role</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="mb-4">Assign Role</h1>

            <% if (error != null) { %>
            <div class="alert alert-danger"><%= error %></div>
            <% } %>

            <% if (message != null) { %>
            <div class="alert alert-success"><%= message %></div>
            <% } %>

            <% if (user != null) { %>
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">User Details</h5>
                    <div class="row">
                        <div class="col-md-4"><p><strong>ID:</strong> <%= user.getUserId() %></p></div>
                        <div class="col-md-4"><p><strong>Username:</strong> <%= user.getUsername() %></p></div>
                        <div class="col-md-4"><p><strong>Email:</strong> <%= user.getEmail() %></p></div>
                    </div>
                    <div class="row">
                        <div class="col-md-4"><p><strong>Full Name:</strong> <%= user.getFullName() %></p></div>
                        <div class="col-md-4"><p><strong>Phone:</strong> <%= user.getPhoneNumber() %></p></div>
                        <div class="col-md-4"><p><strong>Address:</strong> <%= user.getAddress() %></p></div>
                    </div>
                </div>
            </div>

            <form method="POST" action="AssignRole">
                <input type="hidden" name="user_id" value="<%= user.getUserId() %>">

                <div class="form-group">
                    <label for="role">Role</label>
                    <select class="form-control" name="role" id="role" required>
                        <% if (roles != null && !roles.isEmpty()) { 
                            for (String role : roles) { %>
                        <option value="<%= role %>" <%= role.equals(user.getRole()) ? "selected" : "" %>><%= role %></option>
                        <% } } else { %>
                        <option disabled>No roles available</option>
                        <% } %>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Update Role</button>
            </form>
            <% } %>
        </div>
    </body>
</html>
