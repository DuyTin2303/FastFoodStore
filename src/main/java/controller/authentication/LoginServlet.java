/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import DAO.loginDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;
import model.GoogleAuth;

/**
 *
 * @author a
 */
@WebServlet("/LoginController")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private loginDAO loginDAO;
    private static final String GOOGLE_CLIENT_ID = "your-google-client-id";
    private static final String GOOGLE_CLIENT_SECRET = "your-google-client-secret";
    private static final String GOOGLE_REDIRECT_URI = "your-redirect-uri";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30 days in seconds

    public void init() {
        loginDAO = new loginDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe"); // Checkbox value

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập tên đăng nhập!");
            request.getRequestDispatcher("loginView.jsp").forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập mật khẩu!");
            request.getRequestDispatcher("loginView.jsp").forward(request, response);
            return;
        }

        // Thêm kiểm tra độ dài mật khẩu giống như trong RegisterController
        if (password.length() < 8) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 8 ký tự!");
            request.getRequestDispatcher("loginView.jsp").forward(request, response);
            return;
        }

        try {
            Account account = loginDAO.verifyCredentials(username, password);
            if (account != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", account);

                // Nếu người dùng chọn "Remember Me", lưu cookie
                if (rememberMe != null && rememberMe.equals("on")) {
                    // Tạo cookie cho username
                    Cookie usernameCookie = new Cookie("username", username);usernameCookie.setMaxAge(COOKIE_MAX_AGE);
                    usernameCookie.setPath("/");
                    response.addCookie(usernameCookie);
                    
                    // Tạo cookie để đánh dấu trạng thái đăng nhập
                    Cookie loginCookie = new Cookie("loggedIn", "true");
                    loginCookie.setMaxAge(COOKIE_MAX_AGE);
                    loginCookie.setPath("/");
                    response.addCookie(loginCookie);
                    
                    // Không lưu mật khẩu vào cookie vì lý do bảo mật
                    // Có thể lưu token xác thực nếu cần
                    
                    // Cookie cho role (tùy chọn)
                    Cookie roleCookie = new Cookie("userRole", account.getRole());
                    roleCookie.setMaxAge(COOKIE_MAX_AGE);
                    roleCookie.setPath("/");
                    response.addCookie(roleCookie);
                }

                // Kiểm tra role từ Account
                String role = account.getRole();
                System.out.println("Đăng nhập thành công, Role của tài khoản: " + role);

                if (role != null) {
                    switch (role) {
                        case "Admin":
                            response.sendRedirect("adminView.jsp");
                            break;
                        case "Customer":
                            response.sendRedirect("customerView.jsp");
                            break;
                        case "Manager":
                            response.sendRedirect("managerView.jsp");
                            break;
                        case "Staff":
                            response.sendRedirect("staffView.jsp");
                            break;
                        default:
                            request.setAttribute("error", "Vai trò không hợp lệ!");
                            request.getRequestDispatcher("loginView.jsp").forward(request, response);
                            break;
                    }
                } else {
                    request.setAttribute("error", "Không xác định được vai trò tài khoản!");
                    request.getRequestDispatcher("loginView.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("loginView.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi trong quá trình đăng nhập: " + e.getMessage());
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {String action = request.getParameter("action");

        if ("google".equals(action)) {
            GoogleAuth authHelper = new GoogleAuth(GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, GOOGLE_REDIRECT_URI);
            String authUrl = authHelper.buildAuthorizationUrl();
            response.sendRedirect(authUrl);
        } else {
            // Kiểm tra xem đã có cookie đăng nhập chưa
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                String savedUsername = null;
                boolean isLoggedIn = false;
                
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        savedUsername = cookie.getValue();
                    }
                    if (cookie.getName().equals("loggedIn") && cookie.getValue().equals("true")) {
                        isLoggedIn = true;
                    }
                }
                
                // Nếu đã có cookie đăng nhập, tự động đăng nhập
                if (savedUsername != null && isLoggedIn) {
                    try {
                        // Đọc thông tin tài khoản từ database dựa vào username
                        // Lưu ý: Phương thức này cần được thêm vào loginDAO
                        Account account = loginDAO.getAccountByUsername(savedUsername);
                        
                        if (account != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("account", account);
                            
                            String role = account.getRole();
                            if (role != null) {
                                switch (role) {
                                    case "Admin":
                                        response.sendRedirect("adminView.jsp");
                                        return;
                                    case "Customer":
                                        response.sendRedirect("customerView.jsp");
                                        return;
                                    case "Manager":
                                        response.sendRedirect("managerView.jsp");
                                        return;
                                    case "Staff":
                                        response.sendRedirect("staffView.jsp");
                                        return;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                // Điền username vào form đăng nhập nếu có
                if (savedUsername != null) {
                    request.setAttribute("savedUsername", savedUsername);}
            }
            
            // Nếu không có cookie hoặc tự động đăng nhập thất bại, hiển thị trang đăng nhập
            request.getRequestDispatcher("loginView.jsp").forward(request, response);
        }
    }
}