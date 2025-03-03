<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FastFoodStore - Thức ăn nhanh ngon nhất</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .navbar {
                background-color: #e31837 !important;
            }
            .top-bar {
                background-color: #ffc107;
                padding: 5px 0;
            }
            .hero-section {
                background: linear-gradient(45deg, #e31837, #ff6b6b);
                padding: 50px 0;
                color: white;
            }
            .menu-card {
                background-color: #fff;
                border-radius: 10px;
                margin-bottom: 20px;
                text-align: center;
            }
            .menu-card img {
                width: 100%;
                border-radius: 10px 10px 0 0;
            }
            .service-icon {
                width: 120px;
                height: 120px;
                margin: 0 auto;
            }
            .btn-order {
                background-color: #ffc107;
                border: none;
                padding: 10px 30px;
                font-weight: bold;
            }
            .footer {
                background-color: #e31837;
                color: white;
                padding: 40px 0;
            }
            .dropdown-menu {
                background-color: #fff;
                border: none;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .dropdown-item:hover {
                background-color: #e31837;
                color: white;
            }
            .carousel-item img {
                width: 80%;
                height: 400px;
                object-fit: cover;
                margin: auto;
            }

        </style>
    </head>
    <body>
        <!-- Top Bar -->
        <div class="top-bar">
            <div class="container d-flex justify-content-end">
                <div class="me-3">
                    <span>CẦN THƠ</span>
                </div>
                <div>
                    <!-- Chia thành 2 liên kết riêng biệt -->
                    <a href="LoginView.jsp" class="text-dark text-decoration-none me-2">ĐĂNG NHẬP</a> |
                    <a href="registerView.jsp" class="text-dark text-decoration-none ms-2">ĐĂNG KÝ</a>
                </div>
            </div>
        </div>


        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-dark">
            <div class="container">
                <a class="navbar-brand d-flex align-items-center" href="#">
                    <img src="img/logo1.png" alt="FastFoodStore Logo" width="50" height="50" class="me-2">
                    <strong>FastFoodStore</strong>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link" href="#">TRANG CHỦ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">VỀ CHÚNG TÔI</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">THỰC ĐƠN</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">KHUYẾN MÃI</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">DỊCH VỤ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">CỬA HÀNG</a></li>
                    </ul>
                    <button class="btn btn-warning ms-3">PICK UP</button>
                    <div class="ms-3 text-white">1900-1533</div>
                </div>
            </div>
        </nav>

        <!-- Carousel -->
        <section class="hero-section">
            <div class="container-fluid bg-dark">
                <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">

                    <!-- Indicators -->
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                        <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                        <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
                    </div>

                    <!-- Carousel Items -->
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="img/food1.png" class="d-block w-100" alt="Hero Image 1">
                            <div class="carousel-caption d-none d-md-block">
                                <h1 class="display-4">VOUCHER NGÀY LỄ</h1>
                                <p class="lead">CHỈ CÓ Ở FASTFOODSTORE</p>
                                <button class="btn btn-primary">ĐẶT NGAY</button>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="img/food4.png" class="d-block w-100" alt="Hero Image 2">
                            <div class="carousel-caption d-none d-md-block">
                                <h1 class="display-4">KHUYẾN MÃI HẤP DẪN</h1>
                                <p class="lead">MÓN NGON GIẢM GIÁ LÊN ĐẾN 50%</p>
                                <button class="btn btn-primary">ĐẶT NGAY</button>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="img/food3.png" class="d-block w-100" alt="Hero Image 3">
                            <div class="carousel-caption d-none d-md-block">
                                <h1 class="display-4">MÓN MỚI CỰC HOT</h1>
                                <p class="lead">THƯỞNG THỨC NGAY HÔM NAY</p>
                                <button class="btn btn-primary">ĐẶT NGAY</button>
                            </div>
                        </div>
                    </div>

                    <!-- Controls -->
                    <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
        </section>



        <!-- Menu Section -->
        <section class="py-5">
            <div class="container">
                <div class="row">
                    <div class="col-md-3">
                        <div class="bg-danger text-white p-4 rounded h-100">
                            <h2>ĂN GÌ HÔM NAY</h2>
                            <p>Thực đơn đa dạng và phong phú, có rất nhiều sự lựa chọn cho bạn.</p>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="menu-card">
                                    <img src="/api/placeholder/300/200" alt="Gà giòn">
                                    <h5 class="mt-3">Gà Rán</h5>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="menu-card">
                                    <img src="/api/placeholder/300/200" alt="Gà sốt">
                                    <h5 class="mt-3">Gà Sốt Cay</h5>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="menu-card">
                                    <img src="/api/placeholder/300/200" alt="Mì Ý">
                                    <h5 class="mt-3">Mì Ý Sốt Bò Bằm</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Services Section -->
        <section class="bg-light py-5">
            <div class="container">
                <h2 class="text-center text-danger mb-4">DỊCH VỤ</h2>
                <p class="text-center mb-5">TẬN HƯỞNG NHỮNG KHOẢNH KHẮC TRỌN VẸN CÙNG FASTFOODSTORE</p>
                <div class="row">
                    <div class="col-md-3 text-center">
                        <div class="service-icon">
                            <img src="/api/placeholder/120/120" alt="Đặt hàng" class="img-fluid">
                        </div>
                        <h5 class="mt-3">Lấy Tại Cửa Hàng</h5>
                        <button class="btn btn-danger mt-2">XEM THÊM</button>
                    </div>
                    <div class="col-md-3 text-center">
                        <div class="service-icon">
                            <img src="/api/placeholder/120/120" alt="Tiệc" class="img-fluid">
                        </div>
                        <h5 class="mt-3">Đặt Hàng</h5>
                        <button class="btn btn-danger mt-2">XEM THÊM</button>
                    </div>
                    <div class="col-md-3 text-center">
                        <div class="service-icon">
                            <img src="/api/placeholder/120/120" alt="Kids Club" class="img-fluid">
                        </div>
                        <h5 class="mt-3">Khách hàng thân thiết</h5>
                        <button class="btn btn-danger mt-2">XEM THÊM</button>
                    </div>
                    <div class="col-md-3 text-center">
                        <div class="service-icon">
                            <img src="/api/placeholder/120/120" alt="Đơn hàng lớn" class="img-fluid">
                        </div>
                        <h5 class="mt-3">Đơn Hàng Lớn</h5>
                        <button class="btn btn-danger mt-2">XEM THÊM</button>
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
                        <p>Địa chỉ: 123 ABC, Phường XYZ, Quận ABC, TP.HCM</p>
                        <p>Điện thoại: 1900-1533</p>
                        <p>Email: info@fastfoodstore.com</p>
                    </div>
                    <div class="col-md-4">
                        <h5>LIÊN HỆ</h5>
                        <ul class="list-unstyled">
                            <li><a href="#" class="text-white">Chính sách và quy định chung</a></li>
                            <li><a href="#" class="text-white">Chính sách thanh toán</a></li>
                            <li><a href="#" class="text-white">Chính sách bảo mật thông tin</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>

        <!-- Bootstrap JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    </body>
</html>