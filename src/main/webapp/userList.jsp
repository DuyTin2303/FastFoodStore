<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Users" %>
<%@ page import="utils.FormatTime" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User List</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }
            .action-buttons {
                justify-content: center;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="text-center">User Management</h1>
            <a href="/FastFoodStore/CreateUser" class="btn btn-success mb-3">+ Add New User</a>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th>User ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Full Name</th>
                            <th>Phone Number</th>
                            <th>Address</th>
                            <th>Role</th>
                            <th>Created At</th>
                            <th>Updated At</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% List<Users> users = (List<Users>) request.getAttribute("users");
                           users.sort((u1, u2) -> Integer.compare(u2.getUserId(), u1.getUserId()));
                           String pageSizeParam = request.getParameter("pageSize");
                           int pageSize = (pageSizeParam != null) ? Integer.parseInt(pageSizeParam) : 10;
                           int totalRecords = (users != null) ? users.size() : 0;
                           int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
                           String pageParam = request.getParameter("page");
                           int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
                           if (currentPage < 1) currentPage = 1;
                           if (currentPage > totalPages) currentPage = totalPages;
                           int startIndex = (currentPage - 1) * pageSize;
                           int endIndex = Math.min(startIndex + pageSize, totalRecords);
                           if (users != null && !users.isEmpty()) {
                               for (int i = startIndex; i < endIndex; i++) {
                                   Users user = users.get(i);
                        %>
                        <tr>
                            <td><%= user.getUserId() %></td>
                            <td><%= user.getUsername() %></td>
                            <td><%= user.getEmail() %></td>
                            <td><%= user.getFullName() %></td>
                            <td><%= user.getPhoneNumber() %></td>
                            <td><%= user.getAddress() %></td>
                            <td><%= user.getRole() %></td>
                            <td><%= FormatTime.formatTimestamp(user.getCreatedAt()) %></td>
                            <td><%= FormatTime.formatTimestamp(user.getUpdatedAt()) %></td>
                            <td class="action-buttons">
                                <a href="/FastFoodStore/AssignRole?id=<%= user.getUserId() %>" class="btn btn-warning btn-sm">Assign Role</a>
                            </td>
                        </tr>
                        <% } } else { %>
                        <tr>
                            <td colspan="10" class="text-center">No users found.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            <div class="mb-3">
                <label for="pageSizeSelect">Items per page:</label>
                <select id="pageSizeSelect" class="form-select">
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="15">15</option>
                </select>
            </div>
            <div class="d-flex justify-content-center mt-3">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <% if (currentPage > 1) { %>
                        <li class="page-item">
                            <a class="page-link" href="?page=<%= currentPage - 1 %>&pageSize=<%= pageSize %>">Previous</a>
                        </li>
                        <% } %>
                        <% for (int i = 1; i <= totalPages; i++) { %>
                        <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                            <a class="page-link" href="?page=<%= i %>&pageSize=<%= pageSize %>"><%= i %></a>
                        </li>
                        <% } %>
                        <% if (currentPage < totalPages) { %>
                        <li class="page-item">
                            <a class="page-link" href="?page=<%= currentPage + 1 %>&pageSize=<%= pageSize %>">Next</a>
                        </li>
                        <% } %>
                    </ul>
                </nav>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const pageSizeSelect = document.getElementById("pageSizeSelect");
                pageSizeSelect.value = "<%= pageSize %>";
                pageSizeSelect.addEventListener("change", function () {
                    const selectedSize = this.value;
                    window.location.href = "?page=1&pageSize=" + selectedSize;
                });
            });
        </script>
    </body>
</html>
