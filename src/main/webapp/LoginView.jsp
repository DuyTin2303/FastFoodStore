<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3 class="text-center">Đăng nhập</h3>
                    </div>
                    <div class="card-body">
                        <!-- Regular Login Form -->
                        <form action="LoginController" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Tên đăng nhập</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Mật khẩu</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Đăng nhập</button>
                            </div>
                        </form>
                        
                        <!-- Google Login Button -->
                        <div class="d-grid mt-3">
                            <a href="LoginController?action=google" class="btn btn-danger">
                                <i class="fab fa-google"></i> Đăng nhập với Google
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>