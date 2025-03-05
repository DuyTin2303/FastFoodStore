

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Account" %>
<%
    Account account = (Account) session.getAttribute("account");
    if (account == null) {
        response.sendRedirect("loginView.jsp?error=Bạn cần đăng nhập!");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản lý tài khoản</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
        <style>
            /* Custom colors based on the provided palette */
            :root {
                --orange: #FFA500;
                --dark-orange: #FF8C00;
                --coral: #FF7F50;
                --tomato: #FF6347;
                --orange-red: #FF4500;
            }
            
            .btn-primary {
                background-color: var(--dark-orange);
                border-color: var(--dark-orange);
            }
            
            .btn-primary:hover, .btn-primary:focus {
                background-color: var(--orange-red);
                border-color: var(--orange-red);
            }
            
            .btn-secondary {
                background-color: var(--coral);
                border-color: var(--coral);
            }
            
            .btn-secondary:hover, .btn-secondary:focus {
                background-color: var(--tomato);
                border-color: var(--tomato);
            }
            
            .bg-primary {
                background-color: var(--dark-orange) !important;
            }
            
            .bg-secondary {
                background-color: var(--coral) !important;
            }
            
            .card-header {
                font-weight: bold;
            }
            
            .alert-success {
                background-color: #d4edda;
                border-color: var(--dark-orange);
                color: #155724;
            }
            
            /* Add a subtle background color */
            body {
                background-color: #fff9f0;
            }
            
            .card {
                border-color: var(--orange);
            }
        </style>
    </head>
    <body>
        <div class="container py-5">
            <div class="row">
                <div class="col-lg-10 mx-auto">
                    <!-- Kiểm tra và hiển thị thông báo thành công từ query string -->
                    <% if (request.getParameter("success") != null) { %>
                    <div class="alert alert-success">
                        <%= request.getParameter("success") %>
                    </div>
                    <% } %>

                    <!-- Kiểm tra và hiển thị thông báo lỗi từ query string -->
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">
                        <%= request.getParameter("error") %>
                    </div>
                    <% } %>

                    <!-- Nút quay về trang chủ -->
                    <div class="text-end mb-4">
                        <a href="customerView.jsp" class="btn btn-primary">
                            <i class="bi bi-house-door me-2"></i>Quay về trang chủ
                        </a>
                    </div>

                    <!-- Tiêu đề trang -->
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            <h2 class="card-title mb-4" style="color: var(--orange-red);"><i class="bi bi-person-circle me-2"></i>Thông tin tài khoản</h2>
                            <div class="row mb-3">
                                <div class="col-md-3 fw-bold">Họ và Tên:</div>
                                <div class="col-md-9"><%= account.getFullName() %></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-3 fw-bold">Email:</div>
                                <div class="col-md-9"><%= account.getEmail() %></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-3 fw-bold">Số điện thoại:</div>
                                <div class="col-md-9"><%= account.getPhoneNumber() %></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-3 fw-bold">Địa chỉ:</div>
                                <div class="col-md-9"><%= account.getAddress() %></div>
                            </div>
                        </div>
                    </div>

                    <!-- Button toggle -->
                    <div class="d-grid gap-2 d-md-flex justify-content-md-center mb-4">
                        <button class="btn btn-primary me-md-2" type="button" data-bs-toggle="collapse" 
                                data-bs-target="#editProfileForm" aria-expanded="false">
                            <i class="bi bi-pencil-square me-2"></i>Chỉnh sửa hồ sơ
                        </button>
                        <button class="btn btn-secondary" type="button" data-bs-toggle="collapse" 
                                data-bs-target="#changePasswordForm" aria-expanded="false">
                            <i class="bi bi-key me-2"></i>Đổi mật khẩu
                        </button>
                    </div>

                    <!-- Form chỉnh sửa hồ sơ (đã ẩn) -->
                    <div class="collapse mb-4" id="editProfileForm">
                        <div class="card shadow">
                            <div class="card-header bg-primary text-white">
                                <h3 class="card-title mb-0 fs-5"><i class="bi bi-pencil me-2"></i>Chỉnh sửa hồ sơ</h3>
                            </div>
                            <div class="card-body">
                                <form action="profile" method="post" class="row g-3">
                                    <input type="hidden" name="action" value="editProfile">

                                    <div class="col-md-6">
                                        <label for="fullName" class="form-label">Họ và Tên</label>
                                        <input type="text" class="form-control" id="fullName" name="fullName" 
                                               value="<%= account.getFullName() %>" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" 
                                               value="<%= account.getEmail() %>" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="phoneNumber" class="form-label">Số điện thoại</label>
                                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" 
                                               value="<%= account.getPhoneNumber() %>">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="address" class="form-label">Địa chỉ</label>
                                        <input type="text" class="form-control" id="address" name="address" 
                                               value="<%= account.getAddress() %>">
                                    </div>
                                    <div class="col-12 text-end">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-save me-2"></i>Lưu Thay Đổi
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Form đổi mật khẩu (đã ẩn) -->
                    <div class="collapse mb-4" id="changePasswordForm">
                        <div class="card shadow">
                            <div class="card-header bg-secondary text-white">
                                <h3 class="card-title mb-0 fs-5"><i class="bi bi-key me-2"></i>Đổi mật khẩu</h3>
                            </div>
                            <div class="card-body">
                                <form action="profile" method="post" class="row g-3" id="passwordForm" onsubmit="return validatePasswordForm()">
                                    <input type="hidden" name="action" value="changePassword">

                                    <div class="col-md-12">
                                        <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                                        <input type="password" class="form-control" id="currentPassword" 
                                               name="currentPassword" required>
                                    </div>
                                    <div class="col-md-12">
                                        <label for="newPassword" class="form-label">Mật khẩu mới</label>
                                        <input type="password" class="form-control" id="newPassword" 
                                               name="newPassword" required>
                                        <div class="form-text">Mật khẩu phải có ít nhất 8 ký tự</div>
                                    </div>
                                    <div class="col-md-12">
                                        <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                                        <input type="password" class="form-control" id="confirmPassword" 
                                               name="confirmPassword" required>
                                        <div class="invalid-feedback" id="passwordMatchError">
                                            Mật khẩu xác nhận không khớp với mật khẩu mới
                                        </div>
                                    </div>
                                    <div class="col-12 text-end">
                                        <button type="submit" class="btn btn-secondary">
                                            <i class="bi bi-check-circle me-2"></i>Đổi mật khẩu
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Script kiểm tra mật khẩu khớp nhau -->
        <script>
            function validatePasswordForm() {
                const newPassword = document.getElementById('newPassword').value;
                const confirmPassword = document.getElementById('confirmPassword').value;
                const confirmInput = document.getElementById('confirmPassword');
                const errorElement = document.getElementById('passwordMatchError');

                // Kiểm tra độ dài mật khẩu
                if (newPassword.length < 8) {
                    alert("Mật khẩu mới phải có ít nhất 8 ký tự!");
                    return false;
                }

                // Kiểm tra mật khẩu khớp nhau
                if (newPassword !== confirmPassword) {
                    confirmInput.classList.add('is-invalid');
                    errorElement.style.display = 'block';
                    return false;
                } else {
                    confirmInput.classList.remove('is-invalid');
                    errorElement.style.display = 'none';
                    return true;
                }
            }

            // Thêm sự kiện để kiểm tra mật khẩu khi người dùng nhập
            document.getElementById('newPassword').addEventListener('input', function () {
                if (document.getElementById('confirmPassword').value !== '') {
                    validatePasswordForm();
                }
            });

            document.getElementById('confirmPassword').addEventListener('input', validatePasswordForm);
        </script>
    </body>
</html>

