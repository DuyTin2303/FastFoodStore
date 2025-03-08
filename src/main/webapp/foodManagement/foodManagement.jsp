<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Dishes" %>
<%@ page import="java.util.Collections, java.util.Comparator" %>
<%@page import="utils.FormatTime"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Food Management</title>
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
            <h1 class="text-center">Food Management</h1>
            <a href="/CreateFood" class="btn btn-success mb-3">+ Add New Dish</a>

            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th>ID</th>
                            <th>Dish Name</th>
                            <th>Dish Image</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Category</th>
                            <th>Created At</th>
                            <th>Updated At</th>
                            <th>Availability</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <%
                        List<Dishes> dishesList = (List<Dishes>) request.getAttribute("dishes");

                        if (dishesList != null) {
                            dishesList.sort(Comparator.comparing(Dishes::getCreatedAt).reversed());
                        }

                        String pageSizeParam = request.getParameter("pageSize");
                        int pageSize = (pageSizeParam != null) ? Integer.parseInt(pageSizeParam) : 10;

                        int totalRecords = (dishesList != null) ? dishesList.size() : 0;
                        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

                        String pageParam = request.getParameter("page");
                        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
                        if (currentPage < 1) currentPage = 1;
                        if (currentPage > totalPages) currentPage = totalPages;

                        int startIndex = (currentPage - 1) * pageSize;
                        int endIndex = Math.min(startIndex + pageSize, totalRecords);
                    %>

                    <tbody>
                        <% if (dishesList != null && !dishesList.isEmpty()) {
                               for (int i = startIndex; i < endIndex; i++) {
                                   Dishes dish = dishesList.get(i);
                        %>
                        <tr>
                            <td><%= dish.getDishId() %></td>
                            <td><%= dish.getDishName() %></td>
                            <td>
                                <img src="<%= dish.getImageUrl() %>" alt="Dish Image" width="100" height="100" class="img-thumbnail">
                            </td>

                            <td><%= dish.getDescription() %></td>
                            <td><%= dish.getPrice() %></td>
                            <td><%= dish.getCategory() != null ? dish.getCategory().getCategoryName() : "Unknown" %></td>    
                            <td><%= FormatTime.formatTimestamp(dish.getCreatedAt()) %></td>
                            <td><%= FormatTime.formatTimestamp(dish.getUpdatedAt()) %></td>
                            <td class="<%= dish.isAvailability() ? "text-success" : "text-danger" %>">
                                <%= dish.isAvailability() ? "Available" : "Unavailable" %>
                            </td>
                            <td class="action-buttons">
                                <a href="/UpdateFood?id=<%= dish.getDishId() %>" class="btn btn-warning btn-sm">Update</a>
                                <button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModal" data-dish-id="<%= dish.getDishId() %>">Delete</button>
                                <button class="btn btn-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#softDeleteModal" 
                                        data-dish-id="<%= dish.getDishId() %>" 
                                        <%= !dish.isAvailability() ? "disabled" : "" %>>
                                    Hide
                                </button>
                            </td>
                        </tr>
                        <% } } else { %>
                        <tr>
                            <td colspan="9" class="text-center">No dishes available.</td>
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
                <!-- Pagination -->
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
        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">Are you sure you want to delete this dish?</div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <a href="#" id="confirmDeleteBtn" class="btn btn-danger">Yes</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Soft Delete Confirmation Modal -->
        <div class="modal fade" id="softDeleteModal" tabindex="-1" aria-labelledby="softDeleteModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-secondary text-white">
                        <h5 class="modal-title" id="softDeleteModalLabel">Confirm Hide</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">Are you sure you want to hide this dish?</div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <a href="#" id="confirmSoftDeleteBtn" class="btn btn-secondary">Yes</a>
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
                    const dishId = button.getAttribute("data-dish-id");
                    confirmDeleteBtn.href = "DeleteFood?id=" + dishId;
                });
                const softDeleteModal = document.getElementById("softDeleteModal");
                const confirmSoftDeleteBtn = document.getElementById("confirmSoftDeleteBtn");
                softDeleteModal.addEventListener("show.bs.modal", function (event) {
                    const button = event.relatedTarget;
                    const dishId = button.getAttribute("data-dish-id");
                    confirmSoftDeleteBtn.href = "SoftDeleteFood?id=" + dishId;
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
