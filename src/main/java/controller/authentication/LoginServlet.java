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

@WebServlet("/LoginController")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private loginDAO loginDAO;
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30 ngày

    public void init() {
        loginDAO = new loginDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe"); // Checkbox "Remember Me"

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

        if (password.length() < 8) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 8 ký tự!");
            request.getRequestDispatcher("loginView.jsp").forward(request, response);
            return;
        }

        try {
            Account account = loginDAO.verifyCredentials(username, password);
            if (account != null) {
                HttpSession session = request.getSession();
                
                // Lưu ID khách hàng và thông tin tài khoản vào session
                session.setAttribute("userId", account.getUserId()); // Thêm ID vào session
                session.setAttribute("account", account);
                session.setAttribute("userRole", account.getRole()); // Lưu role người dùng

                // Nếu chọn "Remember Me", lưu thông tin vào cookie
                if ("on".equals(rememberMe)) {
                    // Cookie cho username
                    Cookie usernameCookie = new Cookie("username", username);
                    usernameCookie.setMaxAge(COOKIE_MAX_AGE);
                    usernameCookie.setPath("/");
                    response.addCookie(usernameCookie);

                    // Cookie đánh dấu đăng nhập
                    Cookie loginCookie = new Cookie("loggedIn", "true");
                    loginCookie.setMaxAge(COOKIE_MAX_AGE);
                    loginCookie.setPath("/");
                    response.addCookie(loginCookie);

                    // Cookie lưu ID khách hàng
                    Cookie userIdCookie = new Cookie("userId", String.valueOf(account.getUserId()));
                    userIdCookie.setMaxAge(COOKIE_MAX_AGE);
                    userIdCookie.setPath("/");
                    response.addCookie(userIdCookie);
                }

                // Điều hướng trang theo vai trò người dùng
                String role = account.getRole();
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
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("loginView.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi trong quá trình đăng nhập: " + e.getMessage());
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra cookie để đăng nhập tự động
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String savedUsername = null;
            String savedUserId = null;
            boolean isLoggedIn = false;

            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    savedUsername = cookie.getValue();
                }
                if ("userId".equals(cookie.getName())) {
                    savedUserId = cookie.getValue();
                }
                if ("loggedIn".equals(cookie.getName()) && "true".equals(cookie.getValue())) {
                    isLoggedIn = true;
                }
            }

            if (isLoggedIn && savedUsername != null && savedUserId != null) {
                try {
                    Account account = loginDAO.getAccountByUsername(savedUsername);
                    if (account != null) {
                        session = request.getSession(true);
                        session.setAttribute("account", account);
                        session.setAttribute("userId", Integer.parseInt(savedUserId));
                        session.setAttribute("userRole", account.getRole());

                        String role = account.getRole();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        request.getRequestDispatcher("loginView.jsp").forward(request, response);
    }
}
