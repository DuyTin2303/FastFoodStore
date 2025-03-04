

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .user-management-container {
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-top: 20px;
            }
            .header {
                background-color: #FF8C00; /* DarkOrange */
                color: white;
                padding: 15px 20px;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .header h1 {
                margin: 0;
                font-size: 24px;
            }
            .header-buttons {
                display: flex;
                gap: 10px;
            }
            .btn-export {
                background-color: white;
                color: #555;
                border: none;
            }
            .btn-add-user {
                background-color: white;
                color: #FF8C00; /* DarkOrange */
                border: none;
            }
            .table th {
                font-weight: 600;
                color: #666;
                border-bottom: 2px solid #eee;
                padding: 12px 15px;
            }
            .table td {
                padding: 12px 15px;
                vertical-align: middle;
                border-bottom: 1px solid #eee;
            }
            .user-avatar {
                width: 36px;
                height: 36px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }
            .user-name {
                display: flex;
                align-items: center;
            }
            .status {
                display: inline-block;
                width: 10px;
                height: 10px;
                border-radius: 50%;
                margin-right: 5px;
            }
            .status.active {
                background-color: #28a745;
            }
            .status.suspended {
                background-color: #FF4500; /* OrangeRed */
            }
            .status.inactive {
                background-color: #FFA500; /* Orange */
            }
            .action-btn {
                color: #FF8C00; /* DarkOrange */
                background: none;
                border: none;
                padding: 5px;
                cursor: pointer;
            }
            .action-btn.delete {
                color: #FF4500; /* OrangeRed */
            }
            .pagination {
                justify-content: flex-end;
                margin-top: 15px;
                margin-bottom: 15px;
            }
            .pagination .page-item.active .page-link {
                background-color: #FF7F50; /* Coral */
                border-color: #FF7F50; /* Coral */
            }
            .pagination .page-link {
                color: #FF8C00; /* DarkOrange */
            }
            .entry-info {
                color: #777;
                font-size: 14px;
                padding: 15px;
            }
            .btn-danger {
                background-color: #FF4500; /* OrangeRed */
                border-color: #FF4500; /* OrangeRed */
            }
            .empty-table-message {
                text-align: center;
                padding: 30px;
                color: #777;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="user-management-container">
              <div class="header">
    <h1>User Management</h1>
    <div class="header-buttons">
        <!-- Nút Export to Excel -->
        <button class="btn btn-export">
            <i class="fas fa-file-export"></i> Export to Excel
        </button>
        
        <!-- Nút Add New User -->
        <button class="btn btn-add-user">
            <i class="fas fa-plus-circle"></i> Add New User
        </button>
        
        <!-- Nút Đăng xuất -->
        <a href="logout" class="btn btn-danger ms-2">
            <i class="fas fa-sign-out-alt"></i> Đăng xuất
        </a>
        
        <!-- Nút Quản lý lịch làm việc với Dropdown Menu -->
        <div class="btn-group ms-2">
            <button type="button" 
                    class="btn btn-primary dropdown-toggle" 
                    data-bs-toggle="dropdown" 
                    aria-expanded="false">
                <i class="fas fa-calendar-alt"></i> Quản lý lịch làm việc
            </button>
            
            <ul class="dropdown-menu">
                <li>
                    <a class="dropdown-item" href="/viewShiftRegistration">
                        <i class="fas fa-list me-2"></i> Xem đơn đăng ký ca làm
                    </a>
                </li>
                <li>
                    <a class="dropdown-item" href="/staffSchedule">
                        <i class="fas fa-calendar-check me-2"></i> Xem lịch làm việc nhân viên
                    </a>
                </li>
            </ul>
        </div>
              <div class="btn-group ms-2">
            <button type="button" 
                    class="btn btn-primary dropdown-toggle" 
                    data-bs-toggle="dropdown" 
                    aria-expanded="false">
                <i class="fas fa-calendar-alt"></i> Quản lí giao hàng
            </button>
            
            <ul class="dropdown-menu">
                   <li>
                    <a class="dropdown-item" href="/deliveryAssignment">
                        <i class="fas fa-calendar-check me-2"></i> Gán nhân viên giao hàng
                    </a>
                </li>
                <li>
                    <a class="dropdown-item" href="/deliveryAssignment">
                        <i class="fas fa-list me-2"></i> xem lịch sử đơn hàng
                    </a>
                </li>
                 <li>
                    <a class="dropdown-item" href="/deliveryAssignment">
                        <i class="fas fa-list me-2"></i> Theo dõi trạng thái đơn hàng
                    </a>
                </li>
             
            </ul>
        </div>
    </div>
</div>


                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Date Created</th>
                                <th>Role</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Bảng trống, không có dữ liệu mẫu -->
                            <tr>
                                <td colspan="6" class="empty-table-message">
                                    <i class="fas fa-users fa-3x mb-3"></i>
                                    <p>No users available. Click "Add New User" to create one.</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="d-flex justify-content-between px-3 pb-3">
                    <div class="entry-info">
                        Showing 0 out of 0 entries
                    </div>
                    <nav>
                        <ul class="pagination">
                            <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                            <li class="page-item active"><a class="page-link" href="#">1</a></li>
                            <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
    </body>
</html>

