

<%-- 
    Document   : adminView
    Created on : 03-03-2025, 20:06:08
    Author     : a
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - User Management</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .stats-card {
                transition: all 0.3s;
            }
            .stats-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 20px rgba(0,0,0,0.1);
            }
            .role-badge {
                font-size: 0.8rem;
                padding: 0.35em 0.65em;
            }
            .table-responsive {
                overflow-x: auto;
            }
            .action-btns a {
                margin-right: 5px;
            }
            .search-box {
                position: relative;
            }
            .search-box i {
                position: absolute;
                top: 50%;
                left: 10px;
                transform: translateY(-50%);
            }
            .search-box input {
                padding-left: 35px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar can be added here -->

                <!-- Main Content -->
                <main class="col-md-12 ms-sm-auto col-lg-12 px-md-4 py-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">User Management</h1>
                        <div>
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addUserModal">
                                <i class="fas fa-user-plus"></i> Add New User
                            </button>
                        </div>
                    </div>

                    <!-- Alert Messages -->
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            ${successMessage}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${errorMessage}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Statistics Cards -->
                    <div class="row mb-4">
                        <div class="col-xl-2 col-md-4 mb-3">
                            <div class="card stats-card text-bg-primary">
                                <div class="card-body">
                                    <h5 class="card-title">Total Users</h5>
                                    <h2 class="mb-0">${totalAccounts}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 mb-3">
                            <div class="card stats-card text-bg-danger">
                                <div class="card-body">
                                    <h5 class="card-title">Admins</h5>
                                    <h2 class="mb-0">${adminCount}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 mb-3">
                            <div class="card stats-card text-bg-warning">
                                <div class="card-body">
                                    <h5 class="card-title">Managers</h5>
                                    <h2 class="mb-0">${managerCount}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 mb-3">
                            <div class="card stats-card text-bg-info">
                                <div class="card-body">
                                    <h5 class="card-title">Staff</h5>
                                    <h2 class="mb-0">${staffCount}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 mb-3">
                            <div class="card stats-card text-bg-success">
                                <div class="card-body">
                                    <h5 class="card-title">Customers</h5>
                                    <h2 class="mb-0">${customerCount}</h2>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-md-4 mb-3">
                            <div class="card stats-card text-bg-secondary">
                                <div class="card-body">
                                    <h5 class="card-title">Guests</h5>
                                    <h2 class="mb-0">${guestCount}</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Filters -->
                    <div class="row mb-4">
                        <div class="col-md-6 mb-3">
                            <div class="card">
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/admin" method="get" class="d-flex align-items-center gap-3">
                                        <div class="search-box flex-grow-1">
                                            <i class="fas fa-search"></i>
                                            <input type="text" name="search" class="form-control" placeholder="Search by username, email, or name" value="${searchKeyword}">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Search</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="card">
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/admin" method="get" class="d-flex align-items-center gap-3">
                                        <select name="roleFilter" class="form-select flex-grow-1">
                                            <option value="All" ${roleFilter == 'All' ? 'selected' : ''}>All Roles</option>
                                            <option value="Admin" ${roleFilter == 'Admin' ? 'selected' : ''}>Admin</option>
                                            <option value="Manager" ${roleFilter == 'Manager' ? 'selected' : ''}>Manager</option>
                                            <option value="Staff" ${roleFilter == 'Staff' ? 'selected' : ''}>Staff</option>
                                            <option value="Customer" ${roleFilter == 'Customer' ? 'selected' : ''}>Customer</option>
                                            <option value="Guest" ${roleFilter == 'Guest' ? 'selected' : ''}>Guest</option>
                                        </select>
                                        <button type="submit" class="btn btn-primary">Filter</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Users Table -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="mb-0">User Accounts</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Username</th>
                                            <th>Email</th>
                                            <th>Full Name</th>
                                            <th>Phone</th>
                                            <th>Role</th>
                                            <th>Created</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="account" items="${accounts}">
                                            <tr>
                                                <td>${account.userId}</td>
                                                <td>${account.username}</td>
                                                <td>${account.email}</td>
                                                <td>${account.fullName}</td>
                                                <td>${account.phoneNumber}</td>
                                                <td>
                                                    <div class="dropdown">
                                                        <button class="btn btn-sm dropdown-toggle
                                                                ${account.role == 'Admin' ? 'btn-danger' : 
                                                                  account.role == 'Staff' ? 'btn-info' : 
                                                                  account.role == 'Customer' ? 'btn-success' :
                                                                  account.role == 'Manager' ? 'btn-warning' : 'btn-secondary'}" 
                                                                type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                            ${account.role}
                                                        </button>
                                                        <ul class="dropdown-menu">
                                                            <li><a class="dropdown-item ${account.role == 'Admin' ? 'active' : ''}" href="#" 
                                                                   onclick="updateRole(${account.userId}, 'Admin')">Admin</a></li>
                                                            <li><a class="dropdown-item ${account.role == 'Manager' ? 'active' : ''}" href="#" 
                                                                   onclick="updateRole(${account.userId}, 'Manager')">Manager</a></li>
                                                            <li><a class="dropdown-item ${account.role == 'Staff' ? 'active' : ''}" href="#" 
                                                                   onclick="updateRole(${account.userId}, 'Staff')">Staff</a></li>
                                                            <li><a class="dropdown-item ${account.role == 'Customer' ? 'active' : ''}" href="#" 
                                                                   onclick="updateRole(${account.userId}, 'Customer')">Customer</a></li>
                                                            <li><a class="dropdown-item ${account.role == 'Guest' ? 'active' : ''}" href="#" 
                                                                   onclick="updateRole(${account.userId}, 'Guest')">Guest</a></li>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td>${account.createdAt}</td>
                                                <td class="action-btns">
                                                    <a href="${pageContext.request.contextPath}/admin?action=edit&id=${account.userId}" 
                                                       class="btn btn-sm btn-primary">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <button type="button" class="btn btn-sm btn-danger" 
                                                            onclick="confirmDelete(${account.userId}, '${account.username}')">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>

        <!-- Add User Modal -->
        <div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="${pageContext.request.contextPath}/admin?action=create" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addUserModalLabel">Add New User</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" required>
                            </div>
                            <div class="mb-3">
                                <label for="phoneNumber" class="form-label">Phone Number</label>
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address</label>
                                <textarea class="form-control" id="address" name="address" rows="3"></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="role" class="form-label">Role</label>
                                <select class="form-select" id="role" name="role" required>
                                    <option value="Admin">Admin</option>
                                    <option value="Manager">Manager</option>
                                    <option value="Staff">Staff</option>
                                    <option value="Customer" selected>Customer</option>
                                    <option value="Guest">Guest</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Create User</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Confirm Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete user <span id="deleteUserName" class="fw-bold"></span>?</p>
                        <p class="text-danger">This action cannot be undone.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <a href="#" id="confirmDeleteBtn" class="btn btn-danger">Delete</a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                                                // Function to confirm delete
                                                                function confirmDelete(userId, username) {
                                                                    document.getElementById('deleteUserName').textContent = username;
                                                                    document.getElementById('confirmDeleteBtn').href = '${pageContext.request.contextPath}/admin?action=delete&id=' + userId;
                                                                    var deleteModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
                                                                    deleteModal.show();
                                                                }

                                                                // Function to update role
                                                                function updateRole(userId, newRole) {
                                                                    fetch('${pageContext.request.contextPath}/admin?action=updateRole', {
                                                                        method: 'POST',
                                                                        headers: {
                                                                            'Content-Type': 'application/x-www-form-urlencoded',
                                                                        },
                                                                        body: 'userId=' + userId + '&newRole=' + newRole
                                                                    })
                                                                            .then(response => response.text())
                                                                            .then(data => {
                                                                                if (data === 'success') {
                                                                                    window.location.reload();
                                                                                } else {
                                                                                    alert('Failed to update role. Please try again.');
                                                                                }
                                                                            })
                                                                            .catch(error => {
                                                                                console.error('Error:', error);
                                                                                alert('An error occurred. Please try again.');
                                                                            });
                                                                }

                                                                // Auto-dismiss alerts after 5 seconds
                                                                document.addEventListener('DOMContentLoaded', function () {
                                                                    setTimeout(function () {
                                                                        const alerts = document.querySelectorAll('.alert');
                                                                        alerts.forEach(function (alert) {
                                                                            const bsAlert = new bootstrap.Alert(alert);
                                                                            bsAlert.close();
                                                                        });
                                                                    }, 5000);
                                                                });
        </script>
    </body>
</html>


