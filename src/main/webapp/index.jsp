<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FastFoodStore - Thức ăn nhanh ngon nhất</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
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

            /* Hero banner */
            .hero-banner {
                background: linear-gradient(45deg, var(--primary-orange), var(--light-coral));
                padding: 0;
                color: white;
                position: relative;
                overflow: hidden;
            }

            .hero-content {
                padding: 30px;
                position: relative;
                z-index: 2;
            }

            .hero-image {
                text-align: center;
            }

            .hero-image img {
                max-width: 100%;
                height: auto;
            }

            /* Food menu section */
            .menu-section {
                padding: 50px 0;
                background-color: #f9f9f9;
            }

            .menu-title {
                text-align: center;
                margin-bottom: 40px;
                font-weight: bold;
                font-size: 2.5rem;
                color: var(--primary-orange);
            }

            .menu-item {
                background-color: #fff;
                border-radius: 10px;
                overflow: hidden;
                margin-bottom: 30px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
                transition: transform 0.3s;
            }

            .menu-item:hover {
                transform: translateY(-5px);
            }

            .menu-item img {
                width: 100%;
                height: 200px;
                object-fit: cover;
            }

            .menu-item-body {
                padding: 20px;
                text-align: center;
            }

            .menu-item-title {
                font-weight: bold;
                font-size: 1.2rem;
                color: var(--primary-orange);
                margin-bottom: 10px;
            }

            /* Services section */
            .services-section {
                padding: 50px 0;
                background-color: #fff;
                text-align: center;
            }

            .services-title {
                margin-bottom: 40px;
                font-weight: bold;
                font-size: 2rem;
                color: var(--primary-orange);
            }

            .service-item {
                margin-bottom: 30px;
            }

            .service-icon {
                margin-bottom: 15px;
            }

            .service-icon img {
                max-width: 120px;
                height: auto;
            }

            .service-title {
                font-weight: bold;
                margin-bottom: 10px;
                font-size: 1.2rem;
            }

            .btn-view-more {
                background-color: var(--primary-orange);
                color: var(--text-white);
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                font-weight: bold;
                transition: background-color 0.3s;
            }

            .btn-view-more:hover {
                background-color: var(--dark-orange);
                color: var(--text-white);
            }

            .btn-order-now {
                background-color: var(--dark-orange);
                color: var(--text-white);
                padding: 15px 40px;
                border: none;
                border-radius: 5px;
                font-weight: bold;
                font-size: 1.2rem;
                transition: background-color 0.3s;
                display: inline-block;
                margin-top: 20px;
            }

            .btn-order-now:hover {
                background-color: var(--tomato);
                color: var(--text-white);
                text-decoration: none;
            }

            /* About section */
            .about-section {
                padding: 50px 0;
                background-color: #f9f9f9;
            }

            .about-content {
                text-align: center;
                max-width: 800px;
                margin: 0 auto;
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

            /* Language and location */
            .lang-location {
                display: flex;
                align-items: center;
            }

            .lang-location a {
                margin-right: 15px;
                color: var(--text-white);
                text-decoration: none;
                font-weight: bold;
            }

            /* Full-width carousel styles */
            .hero-banner {
                position: relative;
                overflow: hidden;
                padding: 0;
            }

            .carousel, .carousel-inner, .carousel-item {
                height: 500px; /* Set a fixed height or use vh units like 70vh */
            }

            .carousel-background {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
            }

            .carousel-overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: linear-gradient(90deg, rgba(255, 140, 0, 0.8) 0%, rgba(255, 99, 71, 0.4) 100%);
            }

            .hero-content {
                color: var(--text-white);
                position: relative;
                z-index: 2;
                padding: 60px 20px;
            }

            /* Update button styles to match Jollibee example */
            .btn-order-now {
                background-color: var(--dark-orange);
                color: var(--text-white);
                padding: 12px 40px;
                border: none;
                border-radius: 30px;
                font-weight: bold;
                font-size: 1.2rem;
                transition: background-color 0.3s;
                display: inline-block;
                margin-top: 20px;
                text-transform: uppercase;
            }

            .btn-order-now:hover {
                background-color: var(--tomato);
                color: var(--text-white);
                text-decoration: none;
            }

            /* Responsive adjustments */
            @media (max-width: 768px) {
                .carousel, .carousel-inner, .carousel-item {
                    height: 400px;
                }

                .hero-content {
                    text-align: center;
                    padding: 40px 15px;
                }
            }

            /* Additional color adjustments */
            .text-danger {
                color: var(--dark-orange) !important;
            }

            .btn-outline-danger {
                color: var(--primary-orange);
                border-color: var(--primary-orange);
            }

            .btn-outline-danger:hover {
                background-color: var(--primary-orange);
                color: var(--text-white);
            }
        </style>
    </head>
    <body>
        <!-- Top Bar with Language and Login -->
        <div class="top-bar py-2">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <a class="navbar-brand" href="#">
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

                        <li class="nav-item"><a class="nav-link" href="aboutUs.jsp">VỀ CHÚNG TÔI</a></li>
                        <li class="nav-item"><a class="nav-link" href="FoodManagementController?action=viewAll">THỰC ĐƠN</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">KHUYẾN MÃI</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">DỊCH VỤ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">LIÊN HỆ</a></li>
                    </ul>
                    <div class="ms-auto d-flex align-items-center">
                        <button class="pickup-button me-3">PICK UP</button>
                        <div class="phone-number">0944211019</div>
                    </div>
                </div>
            </div>
        </nav>

        <!-- Hero Banner Section with Full-width Carousel -->
        <section class="hero-banner">
            <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
                    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="3" aria-label="Slide 4"></button>
                    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="4" aria-label="Slide 5"></button>
                </div>

                <div class="carousel-inner">
                    <!-- Slide 1 -->
                    <div class="carousel-item active">
                        <div class="carousel-background" style="background-image: url('img/Combo01.png');"></div>
                        <div class="carousel-overlay"></div>
                        <div class="container position-relative">
                            <div class="row align-items-center justify-content-center text-center">
                                <div class="col-md-6 hero-content">
                                    <h1 class="display-4 fw-bold mb-4">COMBO</h1>
                                    <p class="lead mb-4">Thưởng thức ngay các món ăn thơm ngon tại FastFoodStore</p>
                                    <div class="btn-container">
                                        <a href="loginView.jsp" class="btn-order-now">ĐẶT NGAY</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Slide 2 -->
                    <div class="carousel-item">
                        <div class="carousel-background" style="background-image: url('img/garan.png');"></div>
                        <div class="carousel-overlay"></div>
                        <div class="container position-relative">
                            <div class="row align-items-center justify-content-center text-center">
                                <div class="col-md-6 hero-content">
                                    <h1 class="display-4 fw-bold mb-4">GÀ GIÒN</h1>
                                    <p class="lead mb-4">Hương vị giòn rụm, thơm ngon khó cưỡng</p>
                                    <a href="loginView.jsp" class="btn-order-now">KHÁM PHÁ NGAY</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Slide 3 -->
                    <div class="carousel-item">
                        <div class="carousel-background" style="background-image: url('img/hamburger.png');"></div>
                        <div class="carousel-overlay"></div>
                        <div class="container position-relative">
                            <div class="row align-items-center justify-content-center text-center">
                                <div class="col-md-6 hero-content">
                                    <h1 class="display-4 fw-bold mb-4">HAMBURGER</h1>
                                    <p class="lead mb-4">Món ăn ngon cho cả gia đình với giá cực hấp dẫn</p>
                                    <a href="loginView.jsp" class="btn-order-now">XEM COMBO</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Slide 4 -->
                    <div class="carousel-item">
                        <div class="carousel-background" style="background-image: url('img/miy.png');"></div>
                        <div class="carousel-overlay"></div>
                        <div class="container position-relative">
                            <div class="row align-items-center justify-content-center text-center">
                                <div class="col-md-6 hero-content">
                                    <h1 class="display-4 fw-bold mb-4">MỲ Ý SỐT BÒ BĂM</h1>
                                    <p class="lead mb-4">Hương vị Ý đích thực, bò bằm thơm ngon</p>
                                    <a href="loginView.jsp" class="btn-order-now">THƯỞNG THỨC</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Slide 5 -->
                    <div class="carousel-item">
                        <div class="carousel-background" style="background-image: url('img/tarttrung.png');"></div>
                        <div class="carousel-overlay"></div>
                        <div class="container position-relative">
                            <div class="row align-items-center justify-content-center text-center">
                                <div class="col-md-6 hero-content">
                                    <h1 class="display-4 fw-bold mb-4">TRÁNG MIỆNG</h1>
                                    <p class="lead mb-4">Kết thúc bữa ăn với hương vị tuyệt hảo</p>
                                    <a href="loginView.jsp" class="btn-order-now">XEM THÊM</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </section>

        <!-- Menu Section -->
        <section class="menu-section">
            <div class="container">
                <h2 class="menu-title">ĂN GÌ HÔM NAY</h2>
                <p class="text-center mb-5">Thực đơn FastFoodStore đa dạng và phong phú, có rất nhiều sự lựa chọn cho bạn, gia đình và bạn bè.</p>

                <div class="row">
                    <div class="col-md-3">
                        <div class="menu-item">
                            <img src="img/food4.png" alt="Gà Giòn Vui Vẻ">
                            <div class="menu-item-body">
                                <h3 class="menu-item-title">Gà Giòn Vui Vẻ</h3>
                                <a href="FoodManagementController?category=&action=filterCategory" class="btn btn-sm btn-outline-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="menu-item">
                            <img src="img/gasotcay.png" alt="Gà Sốt Cay">
                            <div class="menu-item-body">
                                <h3 class="menu-item-title">Gà Sốt Cay</h3>
                                <a href="FoodManagementController?category=&action=filterCategory" class="btn btn-sm btn-outline-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="menu-item">
                            <img src="img/spaghetti.png" alt="Mỳ Ý Sốt Bò Bằm">
                            <div class="menu-item-body">
                                <h3 class="menu-item-title">Mỳ Ý Sốt Bò Bằm</h3>
                                <a href="FoodManagementController?category=&action=filterCategory" class="btn btn-sm btn-outline-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="menu-item">
                            <img src="img/flan.png" alt="Món tráng miệng">
                            <div class="menu-item-body">
                                <h3 class="menu-item-title">Món tráng miệng</h3>
                                <a href="FoodManagementController?category=&action=filterCategory" class="btn btn-sm btn-outline-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        

        <!-- About Section -->
        <section class="about-section">
            <div class="container">
                <div class="about-content">
                    <h2 class="mb-4 text-danger">FASTFOODSTORE, XIN CHÀO</h2>
                    <p class="mb-4">Chúng tôi là FastFoodStore Việt Nam và đây là một dự án nhầm phục vụ các nhu cầu đặt đồ ăn trực tuyến giúp cho mọi người có một trải nghiệm tuyệt vời!</p>
                    <a href="#" class="btn-order-now">ĐẶT HÀNG</a>
                </div>
            </div>
        </section>

        <!-- Footer -->
        <footer class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <h5>CÔNG TY TNHHMMT FASTFOODSTORE VIỆT NAM</h5>
                        <p>Địa chỉ: FPT Cần Thơ.</p>
                        <p>Điện thoại: 0944211019</p>
                        <p>Tổng đài: 0944211019</p>
                        <p>Email: tientce182271@fpt.edu.vn</p>
                    </div>
                </div>
                <div class="footer-bottom">
                    <p>© 2024 FastFoodStore Việt Nam</p>
                </div>
            </div>
        </footer>

        <!-- Bootstrap JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

