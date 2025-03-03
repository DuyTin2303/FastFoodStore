package controller.user;

import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.MD5;
import java.io.IOException;

/**
 * RegisterController - Xử lý đăng ký người dùng
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy dữ liệu từ form đăng ký
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");

        // Kiểm tra mật khẩu nhập lại
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.getRequestDispatcher("registerView.jsp").forward(request, response);
            return;
        }

        UserDAO accDAO = new UserDAO();

        // Kiểm tra xem username đã tồn tại chưa
        if (accDAO.isUsernameExists(username)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("registerView.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu bằng MD5
        String hashedPassword = MD5.getMd5(password);

        // Gọi hàm đăng ký
        boolean success = accDAO.registerAccount(username, hashedPassword, email, fullName, phoneNumber, address);

        if (success) {
            // Tạo session để lưu thông tin user
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Chuyển hướng đến dashboard.jsp
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("error", "Đăng ký thất bại! Vui lòng thử lại.");
            request.getRequestDispatcher("registerView.jsp").forward(request, response);
        }
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
