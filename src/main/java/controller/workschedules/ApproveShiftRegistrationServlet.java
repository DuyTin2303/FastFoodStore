package controller.workschedules;

import DAO.StaffScheduleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ApproveShiftRegistrationServlet", urlPatterns = {"/approveShiftRegistration"})
public class ApproveShiftRegistrationServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int registrationId = Integer.parseInt(request.getParameter("registrationId"));
    // Lấy thông tin từ cơ sở dữ liệu để hiển thị thông tin đăng ký ca
    request.setAttribute("registrationId", registrationId);
    request.getRequestDispatcher("/approveShiftRegistration.jsp").forward(request, response);
}

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Lấy thông tin từ request
    int registrationId = Integer.parseInt(request.getParameter("registrationId"));
    String managerName = request.getParameter("managerName");
    String requestStatus = request.getParameter("requestStatus");

    // Gọi DAO để cập nhật trạng thái
    StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
    boolean isApproved = staffScheduleDAO.duyetCaLam(registrationId, managerName, requestStatus);

    // Xử lý kết quả duyệt
    if (isApproved) {
        // Nếu duyệt thành công, chuyển hướng về trang danh sách đăng ký ca
        response.sendRedirect(request.getContextPath() + "/viewShiftRegistration");
    } else {
        // Nếu có lỗi xảy ra khi duyệt, chuyển hướng về trang duyệt đăng ký ca
        response.sendRedirect(request.getContextPath() + "/approveShiftRegistration.jsp?message=Đã+có+lỗi+xảy+ra+khi+duyệt+đơn+đăng+ký.");
    }
}
}