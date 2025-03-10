package controller.shiftregistration;

import DAO.ShiftRegistrationsDAO;  // Use the correct DAO class
import model.ShiftRegistration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="ViewWorkShiftRegistrationsForStaffServlet", urlPatterns={"/ShiftRegistration/viewWorkShiftRegistrationsForStaff"})
public class ViewWorkShiftRegistrationsForStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user_id từ session hoặc cookie
        HttpSession session = request.getSession(false);
        Integer userId = null;

        // Kiểm tra xem user_id có tồn tại trong session không
        if (session != null) {
            userId = (Integer) session.getAttribute("userId");
        }

        // Nếu không có user_id trong session, kiểm tra trong cookie
        if (userId == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userId".equals(cookie.getName())) {
                        userId = Integer.parseInt(cookie.getValue());
                        break;
                    }
                }
            }
        }

        if (userId == null) {
            // Nếu không có user_id, chuyển hướng về trang login
            response.sendRedirect("loginView.jsp");
            return;
        }

        // Tạo DAO và gọi phương thức getShiftRegistrationsByUserId từ ShiftRegistrationsDAO
        ShiftRegistrationsDAO shiftRegistrationsDAO = new ShiftRegistrationsDAO();  // Update DAO class name
        List<ShiftRegistration> shiftRegistrations = shiftRegistrationsDAO.getShiftRegistrationsByUserId(userId);

        // Chuyển dữ liệu sang JSP để hiển thị
        request.setAttribute("shiftRegistrations", shiftRegistrations);
        request.getRequestDispatcher("/ShiftRegistration/viewWorkShiftRegistrationsForStaff.jsp").forward(request, response);
    }
}
