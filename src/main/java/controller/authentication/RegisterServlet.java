package controller.authentication;

import DAO.accountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MD5;
import java.io.IOException;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tham số từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");

        // Không lấy role từ form nữa, thay vào đó luôn đặt mặc định là "Customer"
        String standardizedRole = "Customer";

        // Lưu lại thông tin đã nhập
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);
        request.setAttribute("phoneNumber", phoneNumber);
        request.setAttribute("address", address);
        request.setAttribute("role", standardizedRole);

        // Kiểm tra lỗi nhập liệu
        boolean hasError = false;

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Tên đăng nhập không được để trống!");
            hasError = true;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("passwordError", "Mật khẩu nhập lại không khớp");
            hasError = true;
        }

        if (password.length() < 8) {
            request.setAttribute("passwordLengthError", "Mật khẩu phải có ít nhất 8 ký tự!");
            hasError = true;
        }

        accountDAO accDAO = new accountDAO();
        if (accDAO.isUsernameExists(username)) {
            request.setAttribute("usernameError", "Tên đăng nhập đã tồn tại!");
            hasError = true;
        }

        if (accDAO.isEmailExists(email)) {
            request.setAttribute("emailError", "Email này đã được sử dụng!");
            hasError = true;
        }

        if (phoneNumber != null && !phoneNumber.isEmpty() && accDAO.isPhoneNumberExists(phoneNumber)) {
            request.setAttribute("phoneError", "Số điện thoại này đã được sử dụng!");
            hasError = true;
        }if (phoneNumber != null && !phoneNumber.isEmpty()) {
            String phoneRegex = "^[0-9]{10,12}$";
            if (!phoneNumber.matches(phoneRegex)) {
                request.setAttribute("phoneFormatError", "Số điện thoại không hợp lệ! Vui lòng nhập 10-12 chữ số.");
                hasError = true;
            }
        }

        if (hasError) {
            request.getRequestDispatcher("registerView.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu
        String hashedPassword = MD5.getMd5(password);

        // Đăng ký tài khoản
        boolean registrationResult = accDAO.registerAccount(username, hashedPassword, email, fullName, phoneNumber, address, standardizedRole);

        if (!registrationResult) {
            request.setAttribute("error", "Đăng ký thất bại! Vui lòng thử lại.");
            request.getRequestDispatcher("registerView.jsp").forward(request, response);
            return;
        }

        // Đăng ký thành công, thiết lập session
        HttpSession session = request.getSession(true);
        
        // Thiết lập thời gian timeout cho session (30 phút)
        session.setMaxInactiveInterval(30 * 60);
        
        // Lưu thông tin người dùng vào session
        session.setAttribute("username", username);
        session.setAttribute("role", standardizedRole);
        session.setAttribute("email", email);
        session.setAttribute("fullName", fullName);
        session.setAttribute("loggedIn", true);
        session.setAttribute("registrationTime", System.currentTimeMillis());
        
        // Tạo cookies để duy trì đăng nhập ngay cả khi đóng trình duyệt
        Cookie usernameCookie = new Cookie("username", username);
        Cookie roleCookie = new Cookie("role", standardizedRole);
        
        // Thiết lập thời gian hết hạn cho cookie (7 ngày)
        int cookieMaxAge = 7 * 24 * 60 * 60;
        usernameCookie.setMaxAge(cookieMaxAge);
        roleCookie.setMaxAge(cookieMaxAge);
        
        // Thiết lập path cho cookie để có thể sử dụng trong toàn bộ ứng dụng
        usernameCookie.setPath("/");
        roleCookie.setPath("/");
        
        // Thêm cookies vào response
        response.addCookie(usernameCookie);
        response.addCookie(roleCookie);

        // Chuyển hướng đến trang dành cho khách hàng
        response.sendRedirect(request.getContextPath() + "/customerView.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("registerView.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}