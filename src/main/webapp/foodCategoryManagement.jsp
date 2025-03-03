<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Comparator"%>
<%@page import="model.FoodCategories"%>
<%@page import="utils.FormatTime"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Food Category Management</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }
            .action-buttons {
                display: flex;
                justify-content: center;
                gap: 5px;
                white-space: nowrap;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="text-center">Food Category Management</h1>
            <a href="createFoodCategory.jsp" class="btn btn-success mb-3">+ Add New Category</a>

            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Created At</th>
                            <th>Updated At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<FoodCategories> categoryList = (List<FoodCategories>) request.getAttribute("categories");
                            if (categoryList != null) {
                                categoryList.sort(Comparator.comparing(FoodCategories::getCreatedAt).reversed());
                            }

                            String pageSizeParam = request.getParameter("pageSize");
                            int pageSize = (pageSizeParam != null) ? Integer.parseInt(pageSizeParam) : 10;
                            int totalRecords = (categoryList != null) ? categoryList.size() : 0;
                            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
                            if (totalPages == 0) totalPages = 1;

                            String pageParam = request.getParameter("page");
                            int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
                            if (currentPage < 1) currentPage = 1;
                            if (currentPage > totalPages) currentPage = totalPages;

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, totalRecords);

                            if (categoryList != null && !categoryList.isEmpty()) {
                                for (int i = startIndex; i < endIndex; i++) {
                                    FoodCategories category = categoryList.get(i);
                        %>
                        <tr>
                            <td><%= category.getCategoryId() %></td>
                            <td><%= category.getCategoryName() %></td>
                            <td><%= category.getDescription() %></td>
                            <td><%= FormatTime.formatTimestamp(category.getCreatedAt()) %></td>
                            <td><%= FormatTime.formatTimestamp(category.getUpdatedAt()) %></td>
                            <td class="action-buttons">
                                <a href="updateFoodCategory.jsp?id=<%= category.getCategoryId() %>" class="btn btn-warning btn-sm">Update</a>
                                <button class="btn btn-danger btn-sm" data-bs-toggle="modal" 
                                        data-bs-target="#deleteModal" data-category-id="<%= category.getCategoryId() %>">
                                    Delete
                                </button>
                            </td>
                        </tr>
                        <% } } else { %>
                        <tr>
                            <td colspan="6" class="text-center text-muted">No categories available.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

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
        </div>

        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">Are you sure you want to delete this category?</div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <a href="#" id="confirmDeleteBtn" class="btn btn-danger">Yes</a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const deleteModal = document.getElementById("deleteModal");
                const confirmDeleteBtn = document.getElementById("confirmDeleteBtn");

                deleteModal.addEventListener("show.bs.modal", function (event) {
                    const button = event.relatedTarget;
                    const categoryId = button.getAttribute("data-category-id");
                    confirmDeleteBtn.href = "DeleteFoodCategory?id=" + categoryId;
                });

            });
            document.addEventListener("DOMContentLoaded", function () {
                // Get page size select element
                const pageSizeSelect = document.getElementById("pageSizeSelect");

                // Keep selected page size in dropdown
                pageSizeSelect.value = "<%= pageSize %>";

                // Redirect on page size change
                pageSizeSelect.addEventListener("change", function () {
                    const selectedSize = this.value;
                    window.location.href = "?page=1&pageSize=" + selectedSize;
                });
            });
        </script>
    </body>
</html>