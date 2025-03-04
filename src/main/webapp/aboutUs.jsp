
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Về Chúng Tôi - FastFoodStore</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            /* Updated colors from the new palette */
            :root {
                --primary-orange: #FF8C00; /* DarkOrange */
                --secondary-orange: #FFA500; /* Orange */
                --dark-orange: #FF4500; /* OrangeRed */
                --light-coral: #FF7F50; /* Coral */
                --tomato: #FF6347; /* Tomato */
                --text-dark: #333;
                --text-white: #fff;
            }

            body {
                font-family: Arial, sans-serif;
            }

            /* Top bar with logo and login */
            .top-bar {
                background-color: var(--dark-orange);
                padding: 10px 0;
                color: var(--text-white);
            }

            /* Main navigation */
            .navbar {
                background-color: var(--primary-orange) !important;
                padding: 0;
            }

            .navbar .nav-link {
                color: var(--text-white) !important;
                font-weight: bold;
                padding: 15px 20px;
            }

            .navbar .nav-link:hover {
                background-color: rgba(255, 255, 255, 0.1);
            }

            /* About Us Specific Styles */
            .about-hero {
                background: linear-gradient(45deg, var(--primary-orange), var(--light-coral));
                color: white;
                padding: 60px 0;
                text-align: center;
            }

            .about-section {
                padding: 60px 0;
            }

            .about-section h2 {
                color: var(--primary-orange);
                margin-bottom: 30px;
                font-weight: bold;
            }

            .creator-profile {
                background-color: #f9f9f9;
                border-radius: 10px;
                padding: 30px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
                margin-bottom: 30px;
            }

            .creator-image {
                width: 180px;
                height: 180px;
                border-radius: 50%;
                object-fit: cover;
                margin: 0 auto 20px;
                display: block;
                border: 5px solid var(--primary-orange);
            }

            .skill-badge {
                background-color: var(--primary-orange);
                color: white;
                display: inline-block;
                padding: 5px 15px;
                border-radius: 20px;
                margin: 5px;
                font-weight: bold;
            }

            .timeline {
                position: relative;
                max-width: 800px;
                margin: 0 auto;
            }

            .timeline::after {
                content: '';
                position: absolute;
                width: 6px;
                background-color: var(--primary-orange);
                top: 0;
                bottom: 0;
                left: 50%;
                margin-left: -3px;
            }

            .timeline-item {
                padding: 10px 40px;
                position: relative;
                width: 50%;
                box-sizing: border-box;
            }

            .timeline-item::after {
                content: '';
                position: absolute;
                width: 20px;
                height: 20px;
                right: -10px;
                background-color: white;
                border: 4px solid var(--dark-orange);
                top: 15px;
                border-radius: 50%;
                z-index: 1;
            }

            .left {
                left: 0;
            }

            .right {
                left: 50%;
            }

            .left::after {
                right: -10px;
            }

            .right::after {
                left: -10px;
            }

            .timeline-content {
                padding: 20px;
                background-color: white;
                position: relative;
                border-radius: 6px;
                box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            }

            .timeline-content h3 {
                color: var(--primary-orange);
                margin-top: 0;
                font-weight: bold;
            }

            .timeline-year {
                font-weight: bold;
                color: var(--dark-orange);
                margin-bottom: 10px;
            }

            .mission-vision {
                background-color: var(--primary-orange);
                padding: 40px 0;
                color: white;
                margin: 40px 0;
            }

            .mission-icon {
                font-size: 3rem;
                margin-bottom: 20px;
            }

            /* Footer */
            .footer {
                background-color: var(--dark-orange);
                color: white;
                padding: 50px 0 20px;
            }

            .footer h5 {
                font-weight: bold;
                margin-bottom: 20px;
            }

            .footer a {
                color: white;
                text-decoration: none;
            }

            .footer a:hover {
                text-decoration: underline;
            }

            .footer-bottom {
                text-align: center;
                padding-top: 20px;
                margin-top: 20px;
                border-top: 1px solid rgba(255,255,255,0.1);
            }

            .social-icons a {
                display: inline-block;
                margin-right: 15px;
                font-size: 1.2rem;
            }

            .pickup-button {
                background-color: var(--tomato);
                color: var(--text-white);
                font-weight: bold;
                padding: 8px 25px;
                border-radius: 50px;
                border: none;
            }

            .pickup-button:hover {
                background-color: var(--light-coral);
            }

            .phone-number {
                color: var(--text-white);
                font-weight: bold;
                padding: 10px;
                font-size: 1.1rem;
            }

            @media screen and (max-width: 768px) {
                .timeline::after {
                    left: 31px;
                }

                .timeline-item {
                    width: 100%;
                    padding-left: 70px;
                    padding-right: 25px;
                }

                .timeline-item::after {
                    left: 22px;
                }

                .left::after, .right::after {
                    left: 22px;
                }

                .right {
                    left: 0%;
                }
            }
        </style>
    </head>
    <body>
        <!-- Top Bar with Language and Login -->
        <div class="top-bar py-2">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <a class="navbar-brand" href="index.jsp">
                            <img src="img/logo1.png" alt="FastFoodStore Logo" width="50" height="50" class="me-2">
                            <strong class="text-white">FastFoodStore</strong>
                        </a>
                    </div>
                    <div class="col-md-6">
                        <div class="d-flex justify-content-end align-items-center">
                            <span class="text-white me-3"><i class="fas fa-map-marker-alt me-1"></i>CẦN THƠ</span>
                            <a href="loginView.jsp" class="text-white me-3" style="text-decoration: none;">ĐĂNG NHẬP</a>
                            <a href="registerView.jsp" class="text-white" style="text-decoration: none;">ĐĂNG KÝ</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-dark">
            <div class="container">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link active" href="index.jsp">TRANG CHỦ</a></li>
                        <li class="nav-item"><a class="nav-link" href="FoodManagementController?action=viewAll">THỰC ĐƠN</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">KHUYẾN MÃI</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">DỊCH VỤ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">CỬA HÀNG</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">TIN TỨC</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">LIÊN HỆ</a></li>
                    </ul>
                    <div class="ms-auto d-flex align-items-center">
                        <button class="pickup-button me-3">PICK UP</button>
                        <div class="phone-number">1900-1533</div>
                    </div>
                </div>
            </div>
        </nav>

        <!-- Hero Section -->
        <section class="about-hero">
            <div class="container">
                <h1 class="display-4 fw-bold">VỀ CHÚNG TÔI</h1>
                <p class="lead">Câu chuyện đằng sau FastFoodStore </p>
            </div>
        </section>

        <!-- Creator Profile Section -->
        <section class="about-section">
            <div class="container">
                <h2 class="text-center">NGƯỜI SÁNG LẬP</h2>
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="creator-profile text-center">
                            <img src="img/logo1.png" alt="Người sáng lập" class="creator-image">
                            <h3 class="mt-4 mb-2">GROUP 01</h3>
                            <p class="text-muted mb-3">SWP391 - SE1809</p>
                            <p class="mb-4">Xin chào mọi người, chào mừng mọi người đã đến với project môn SWP391 của nhóm 1. Đây là dự án web về bán thức ăn nhanh trực tuyến thông qua web. Dự án này cũng chứa khá nhiều tâm huyết của các thành viên trong nhóm nên mong mọi người ủng hộ.</p>

                            <div class="mb-4">
                                <h4 class="mb-3">Kỹ năng:</h4>
                                <span class="skill-badge">Java</span>
                                <span class="skill-badge">JSP</span>
                                <span class="skill-badge">Servlet</span>
                                <span class="skill-badge">Bootstrap</span>
                                <span class="skill-badge">HTML/CSS</span>
                                <span class="skill-badge">MySQL</span>
                            </div>

                            <a href="tientce182271@fpt.edu.vn" class="btn btn-primary mt-3">
                                <i class="fas fa-envelope me-2"></i>Liên hệ với tôi
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Footer -->
        <footer class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <h5>CÔNG TY TNHH FASTFOODSTORE VIỆT NAM</h5>
                        <p>Địa chỉ: Tầng 26, Tòa nhà CII Tower, số 152 Điện Biên Phủ, Phường 25, Quận Bình Thạnh, TP.HCM, Việt Nam</p>
                        <p>Điện thoại: (028) 39309168</p>
                        <p>Tổng đài: 1900-1533</p>
                        <p>Email: info@fastfoodstore.com.vn</p>
                    </div>
                    <div class="col-md-4">
                        <h5>LIÊN HỆ</h5>
                        <ul class="list-unstyled">
                            <li><a href="#">Chính sách và quy định chung</a></li>
                            <li><a href="#">Chính sách thanh toán khi đặt hàng</a></li>
                            <li><a href="#">Chính sách hoạt động</a></li>
                            <li><a href="#">Chính sách bảo mật thông tin</a></li>
                            <li><a href="#">Thông tin vận chuyển và giao nhận</a></li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <h5>HÃY KẾT NỐI VỚI CHÚNG TÔI</h5>
                        <div class="social-icons mb-3">
                            <a href="#"><img src="img/facebook-icon.png" alt="Facebook" width="30"></a>
                            <a href="#"><img src="img/email-icon.png" alt="Email" width="30"></a>
                        </div>
                        <h5 class="mt-4">TẢI ỨNG DỤNG ĐẶT HÀNG VỚI NHIỀU ƯU ĐÃI HƠN</h5>
                        <div class="app-download">
                            <a href="#"><img src="img/google-play.png" alt="Google Play" height="40"></a>
                            <a href="#"><img src="img/app-store.png" alt="App Store" height="40"></a>
                        </div>
                    </div>
                </div>
                <div class="footer-bottom">
                    <p>© 2024 FastFoodStore Việt Nam</p>
                </div>
            </div>
        </footer>

        <!-- Bootstrap JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
    </body>
</html>

