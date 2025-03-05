
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Giao diện nhân viên</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .sidebar {
                min-height: 100vh;
                background-color: #343a40;
            }
            .sidebar-link {
                color: #fff;
                text-decoration: none;
                transition: 0.3s;
            }
            .sidebar-link:hover {
                background-color: #495057;
            }
            .content {
                padding: 20px;
            }
            .card-dashboard {
                transition: 0.3s;
            }
            .card-dashboard:hover {
                transform: translateY(-5px);
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 col-lg-2 d-md-block sidebar collapse p-0">
                    <div class="position-sticky">
                        <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-4 text-white min-vh-100">
                            <a href="#" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                                <span class="fs-4">Nhân viên</span>
                            </a>
                            <hr class="w-100">
                            <ul class="nav nav-pills flex-column mb-auto w-100">
                                <li class="nav-item w-100">
                                    <a href="#" class="sidebar-link d-flex align-items-center px-3 py-2 w-100 active">
                                        <i class="fas fa-tachometer-alt me-2"></i> Tổng quan
                                    </a>
                                </li>
                                <li class="nav-item w-100">
                                    <a href="#" class="sidebar-link d-flex align-items-center px-3 py-2 w-100">
                                        <i class="fas fa-tasks me-2"></i> Nhiệm vụ
                                    </a>
                                </li>
                                <li class="nav-item w-100">
                                    <a href="#workScheduleSubmenu" 
                                       class="btn sidebar-link d-flex align-items-center px-3 py-2 w-100" 
                                       data-bs-toggle="collapse" 
                                       aria-expanded="false">
                                        <i class="fas fa-calendar-alt me-2"></i> Lịch làm việc
                                    </a>

                                    <!-- Submenu hiển thị khi nhấp vào nút trên -->
                                    <div class="collapse" id="workScheduleSubmenu">
                                        <ul class="list-unstyled ms-4">
                                            <li class="nav-item">
                                                <a href="/viewShiftSchedule" class="sidebar-link d-flex align-items-center py-2">
                                                    <i class="fas fa-eye me-2"></i> Xem lịch làm việc
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a href="/createShiftRegistration" class="sidebar-link d-flex align-items-center py-2">
                                                    <i class="fas fa-plus-circle me-2"></i> Đăng ký ca làm
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </li>


                                <li class="nav-item w-100">
                                    <a href="#" class="sidebar-link d-flex align-items-center px-3 py-2 w-100">
                                        <i class="fas fa-file-alt me-2"></i> Báo cáo
                                    </a>
                                </li>
                                <li class="nav-item w-100">
                                    <a href="#" class="sidebar-link d-flex align-items-center px-3 py-2 w-100">
                                        <i class="fas fa-comment me-2"></i> Tin nhắn
                                    </a>
                                </li>
                                <li class="nav-item w-100">
                                    <a href="/viewDeliveryForStaff" class="sidebar-link d-flex align-items-center px-3 py-2 w-100">
                                        <i class="fas fa-truck me-2"></i> Giao hàng
                                    </a>
                                </li>


                            </ul>
                            <hr class="w-100">
                            <div class="dropdown pb-4">
                                <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                                    <img src="https://via.placeholder.com/40" alt="User" width="32" height="32" class="rounded-circle me-2">
                                    <span>Nguyễn Văn A</span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
                                    <li><a class="dropdown-item" href="#">Hồ sơ</a></li>
                                    <li><a class="dropdown-item" href="#">Cài đặt</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 ms-sm-auto content">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">Bảng điều khiển</h1>
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <div class="btn-group me-2">
                                <button type="button" class="btn btn-sm btn-outline-secondary">Chia sẻ</button>
                                <button type="button" class="btn btn-sm btn-outline-secondary">Xuất</button>
                            </div>
                            <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                                <i class="fas fa-calendar me-1"></i> Tuần này
                            </button>
                        </div>
                    </div>

                    <!-- Dashboard Cards -->
                    <div class="row row-cols-1 row-cols-md-2 row-cols-xl-4 g-4 mb-4">
                        <div class="col">
                            <div class="card card-dashboard h-100 bg-primary text-white">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="card-title">Nhiệm vụ đang thực hiện</h6>
                                            <h2 class="mb-0">5</h2>
                                        </div>
                                        <i class="fas fa-tasks fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card card-dashboard h-100 bg-success text-white">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="card-title">Nhiệm vụ hoàn thành</h6>
                                            <h2 class="mb-0">12</h2>
                                        </div>
                                        <i class="fas fa-check-circle fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card card-dashboard h-100 bg-warning text-dark">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="card-title">Thời gian làm việc</h6>
                                            <h2 class="mb-0">32h</h2>
                                        </div>
                                        <i class="fas fa-clock fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card card-dashboard h-100 bg-info text-white">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 class="card-title">Tin nhắn mới</h6>
                                            <h2 class="mb-0">3</h2>
                                        </div>
                                        <i class="fas fa-envelope fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Tasks List -->
                    <div class="card mb-4">
                        <div class="card-header bg-white">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="mb-0">Danh sách nhiệm vụ</h5>
                                <div>
                                    <button class="btn btn-sm btn-primary">
                                        <i class="fas fa-plus me-1"></i> Thêm mới
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Tiêu đề</th>
                                            <th scope="col">Mức độ ưu tiên</th>
                                            <th scope="col">Hạn hoàn thành</th>
                                            <th scope="col">Trạng thái</th>
                                            <th scope="col">Tác vụ</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <th scope="row">1</th>
                                            <td>Hoàn thiện báo cáo quý I</td>
                                            <td><span class="badge bg-danger">Cao</span></td>
                                            <td>15/04/2023</td>
                                            <td><span class="badge bg-warning">Đang thực hiện</span></td>
                                            <td>
                                                <button class="btn btn-sm btn-info me-1"><i class="fas fa-edit"></i></button>
                                                <button class="btn btn-sm btn-success"><i class="fas fa-check"></i></button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row">2</th>
                                            <td>Chuẩn bị tài liệu cho cuộc họp</td>
                                            <td><span class="badge bg-warning">Trung bình</span></td>
                                            <td>10/04/2023</td>
                                            <td><span class="badge bg-info">Mới</span></td>
                                            <td>
                                                <button class="btn btn-sm btn-info me-1"><i class="fas fa-edit"></i></button>
                                                <button class="btn btn-sm btn-success"><i class="fas fa-check"></i></button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row">3</th>
                                            <td>Kiểm tra hệ thống hàng tuần</td>
                                            <td><span class="badge bg-success">Thấp</span></td>
                                            <td>08/04/2023</td>
                                            <td><span class="badge bg-warning">Đang thực hiện</span></td>
                                            <td>
                                                <button class="btn btn-sm btn-info me-1"><i class="fas fa-edit"></i></button>
                                                <button class="btn btn-sm btn-success"><i class="fas fa-check"></i></button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row">4</th>
                                            <td>Cập nhật dữ liệu khách hàng</td>
                                            <td><span class="badge bg-warning">Trung bình</span></td>
                                            <td>12/04/2023</td>
                                            <td><span class="badge bg-success">Hoàn thành</span></td>
                                            <td>
                                                <button class="btn btn-sm btn-info me-1"><i class="fas fa-edit"></i></button>
                                                <button class="btn btn-sm btn-success" disabled><i class="fas fa-check"></i></button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row">5</th>
                                            <td>Liên hệ với đối tác mới</td>
                                            <td><span class="badge bg-danger">Cao</span></td>
                                            <td>14/04/2023</td>
                                            <td><span class="badge bg-warning">Đang thực hiện</span></td>
                                            <td>
                                                <button class="btn btn-sm btn-info me-1"><i class="fas fa-edit"></i></button>
                                                <button class="btn btn-sm btn-success"><i class="fas fa-check"></i></button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Calendar & Notifications -->
                    <div class="row">
                        <!-- Calendar -->
                        <div class="col-md-8 mb-4">
                            <div class="card h-100">
                                <div class="card-header bg-white">
                                    <h5 class="mb-0">Lịch làm việc tuần này</h5>
                                </div>
                                <div class="card-body">
                                    <table class="table table-bordered text-center">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Thứ 2</th>
                                                <th>Thứ 3</th>
                                                <th>Thứ 4</th>
                                                <th>Thứ 5</th>
                                                <th>Thứ 6</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="bg-light">9:00 - Họp nhóm</td>
                                                <td></td>
                                                <td class="bg-info text-white">10:00 - Kiểm tra hệ thống</td>
                                                <td></td>
                                                <td class="bg-warning">15:00 - Deadline báo cáo</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="bg-success text-white">13:30 - Gặp khách hàng</td>
                                                <td></td>
                                                <td class="bg-danger text-white">11:00 - Cuộc họp quan trọng</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="bg-primary text-white">14:00 - Đào tạo</td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td class="bg-info text-white">16:30 - Tổng kết tuần</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <!-- Notifications -->
                        <div class="col-md-4 mb-4">
                            <div class="card h-100">
                                <div class="card-header bg-white">
                                    <h5 class="mb-0">Thông báo</h5>
                                </div>
                                <div class="card-body p-0">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            <div>
                                                <div class="fw-bold">Cuộc họp nhóm</div>
                                                <div class="small text-muted">Thay đổi từ 9:00 sang 10:00</div>
                                            </div>
                                            <span class="badge bg-primary rounded-pill">Mới</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            <div>
                                                <div class="fw-bold">Đào tạo khóa mới</div>
                                                <div class="small text-muted">Đăng ký trước 10/04/2023</div>
                                            </div>
                                            <span class="badge bg-primary rounded-pill">Mới</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            <div>
                                                <div class="fw-bold">Cập nhật hệ thống</div>
                                                <div class="small text-muted">Hệ thống sẽ bảo trì vào Chủ Nhật</div>
                                            </div>
                                            <span class="badge bg-secondary rounded-pill">1 ngày trước</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            <div>
                                                <div class="fw-bold">Đánh giá hiệu suất Q1</div>
                                                <div class="small text-muted">Hạn chót nộp tự đánh giá: 20/04</div>
                                            </div>
                                            <span class="badge bg-secondary rounded-pill">2 ngày trước</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                            <div>
                                                <div class="fw-bold">Chính sách mới</div>
                                                <div class="small text-muted">Cập nhật quy định làm việc từ xa</div>
                                            </div>
                                            <span class="badge bg-secondary rounded-pill">1 tuần trước</span>
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-footer bg-white text-center">
                                    <a href="#" class="btn btn-sm btn-link">Xem tất cả thông báo</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Thêm nút đăng xuất cố định ở góc dưới bên phải -->
        <div class="position-fixed bottom-0 end-0 p-3">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">
                <i class="fas fa-sign-out-alt me-1"></i> Đăng xuất
            </a>
        </div>

        <!-- Bootstrap JS Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

