package controller.user;

import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;
import model.MD5;

@WebServlet("/profile")
public class ProfileManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private userDAO userDAO;
    private static final int COOKIE_MAX_AGE = 30 * 24 * 60 * 60; // 30 days in seconds

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new userDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa có
        response.setCharacterEncoding("UTF-8");
        Account account = null;
        
        // Try to get account from session first
        if (session != null) {
            account = (Account) session.getAttribute("account");
        }
        
        // If no account in session, try to get from cookies
        if (account == null) {
            account = getAccountFromCookies(request);
            
            // If found in cookies, create a new session with the account
            if (account != null) {
                session = request.getSession(true);
                session.setAttribute("account", account);
            } else {
                response.sendRedirect("loginView.jsp"); // Redirect if not logged in
                return;
            }
        }

        request.setAttribute("account", account);
        request.getRequestDispatcher("accountView.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        response.setCharacterEncoding("UTF-8");
        
        // If no account in session, try to get from cookies
        if (account == null) {
            account = getAccountFromCookies(request);
            if (account != null) {
                session.setAttribute("account", account);
            } else {
                response.sendRedirect("loginView.jsp?error=Bạn cần đăng nhập!");
                return;
            }
        }

        String action = request.getParameter("action");

        if ("editProfile".equals(action)) {
            updateProfile(request, response, session, account);
        } else if ("changePassword".equals(action)) {
            changePassword(request, response, account);
        } else if ("rememberMe".equals(action)) {
            rememberUser(response, account);
            response.sendRedirect("profileManagementView.jsp?success=Đã lưu thông tin đăng nhập!");
        } else if ("clearCookies".equals(action)) {
            clearCookies(request, response);
            response.sendRedirect("profileManagementView.jsp?success=Đã xóa cookie đăng nhập!");
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session, Account account)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");

        // Cập nhật vào database
        account.setFullName(fullName);
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);
        account.setAddress(address);

        boolean updated = userDAO.updateAccountProfile(account);

        if (updated) {
            // Nếu cập nhật thành công, cập nhật lại session và chuyển hướng
            session.setAttribute("account", account);
            
            // Update cookies if they exist
            updateCookies(request, response, account);
            
            response.sendRedirect("profileManagementView.jsp?success=Cập nhật thành công!");
        } else {
            // Nếu cập nhật thất bại, hiển thị lỗi
            response.sendRedirect("profileManagementView.jsp?error=Cập nhật thất bại. Vui lòng thử lại!");
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Debug logs
        System.out.println("Attempting to change password for user: " + account.getUsername());
        
        // Kiểm tra xem mật khẩu mới và xác nhận mật khẩu có khớp nhau không
        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            response.sendRedirect("profileManagementView.jsp?error=Mật khẩu mới và xác nhận mật khẩu không khớp!");
            return;
        }

        // Kiểm tra mật khẩu cũ
        if (!MD5.verifyPassword(currentPassword, account.getPasswordHash())) {
            response.sendRedirect("profileManagementView.jsp?error=Mật khẩu hiện tại không đúng!");
            return;
        }

        // Kiểm tra độ dài mật khẩu mới
        if (newPassword.length() < 8) {
            response.sendRedirect("profileManagementView.jsp?error=Mật khẩu mới phải có ít nhất 8 ký tự!");
            return;
        }

        // Cập nhật mật khẩu mới vào database
        String hashedPassword = MD5.hashPassword(newPassword);
        System.out.println("Updating password with hash: " + hashedPassword); // Debug log
        
        boolean updated = userDAO.updateUserPassword(account.getUserId(), hashedPassword);
        System.out.println("Password update result: " + updated); // Debug log

        if (updated) {
            // Cập nhật lại session với mật khẩu mới
            account = userDAO.getAccountById(account.getUserId());
            request.getSession().setAttribute("account", account);
            
            // Update cookies if they exist
            updateCookies(request, response, account);
            
            response.sendRedirect("profileManagementView.jsp?success=Mật khẩu đã được cập nhật thành công!");
        } else {
            response.sendRedirect("profileManagementView.jsp?error=Cập nhật mật khẩu thất bại. Vui lòng thử lại sau!");
        }
    }
    
    // Method to store account info in cookies
    private void rememberUser(HttpServletResponse response, Account account) {
        // Create cookie for user ID
        Cookie userIdCookie = new Cookie("userId", String.valueOf(account.getUserId()));
        userIdCookie.setMaxAge(COOKIE_MAX_AGE);
        userIdCookie.setPath("/");
        
        // Create cookie for username
        Cookie usernameCookie = new Cookie("username", account.getUsername());
        usernameCookie.setMaxAge(COOKIE_MAX_AGE);
        usernameCookie.setPath("/");
        
        // Add cookies to response
        response.addCookie(userIdCookie);
        response.addCookie(usernameCookie);
        
        // We don't store sensitive information like passwords in cookies
    }
    
    // Method to clear cookies
    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName()) || "username".equals(cookie.getName())) {
                    cookie.setMaxAge(0); // Set to expire immediately
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }
    
    // Method to get account from cookies
    private Account getAccountFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        
        String userId = null;
        
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                userId = cookie.getValue();
                break;
            }
        }
        
        if (userId != null) {
            try {
                int id = Integer.parseInt(userId);
                return userDAO.getAccountById(id);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        
        return null;
    }
    
    // Method to update cookies if they exist
    private void updateCookies(HttpServletRequest request, HttpServletResponse response, Account account) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            boolean hasUserIdCookie = false;
            
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    hasUserIdCookie = true;
                    break;
                }
            }
            
            if (hasUserIdCookie) {
                rememberUser(response, account);
            }
        }
    }
}

