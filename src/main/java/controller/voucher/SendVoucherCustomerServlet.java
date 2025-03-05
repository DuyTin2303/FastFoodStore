package controller.voucher;

import DAO.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SendVoucherCustomerServlet", urlPatterns = {"/sendVoucherCustomer"})
public class SendVoucherCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        String voucherIdParam = request.getParameter("voucherId");

        if (userIdParam == null || voucherIdParam == null) {
            response.sendRedirect("sendVoucherError.jsp?message=Thiếu thông tin người dùng hoặc voucher.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);
            int voucherId = Integer.parseInt(voucherIdParam);

            VoucherDAO voucherDAO = new VoucherDAO();
            boolean result = voucherDAO.sendVoucherToUser(userId, voucherId);

            if (result) {
                response.sendRedirect("/User/viewCustomer?voucherId=" + voucherId);
            } else {
                response.sendRedirect("sendVoucherError.jsp?message=Không thể gửi voucher. Vui lòng thử lại.");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("sendVoucherError.jsp?message=ID không hợp lệ.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
