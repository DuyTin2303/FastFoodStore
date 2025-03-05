package controller.voucher;

import DAO.VoucherDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(name = "UpdateVoucherManagerServlet", urlPatterns = {"/Voucher/updateVoucherManager"})
public class UpdateVoucherManagerServlet extends HttpServlet {

    private VoucherDAO voucherDAO = new VoucherDAO();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String voucherId = request.getParameter("id"); // Đổi từ "voucherId" thành "id"
    if (voucherId != null) {
        try {
            int id = Integer.parseInt(voucherId);
            request.setAttribute("voucher", voucherDAO.getVoucherById(id));
            request.getRequestDispatcher("/Voucher/updateVoucherManager.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("viewVoucherManager");
        }
    } else {
        response.sendRedirect("viewVoucherManager");
    }
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String errorMessage = null;

        try {
            int voucherId = Integer.parseInt(request.getParameter("voucherId"));
            String name = request.getParameter("name").trim();
            double discountPercentage = Double.parseDouble(request.getParameter("discountPercentage"));
            String validFrom = request.getParameter("validFrom").trim();
            String validUntil = request.getParameter("validUntil").trim();
            int soLuong = Integer.parseInt(request.getParameter("soLuong"));
            String status = request.getParameter("status").trim();

            // Validation
            if (name.isEmpty()) {
                errorMessage = "Tên voucher không được để trống.";
            } else if (discountPercentage < 0 || discountPercentage > 100) {
                errorMessage = "Phần trăm giảm giá phải nằm trong khoảng từ 0 đến 100.";
            } else if (soLuong < 1) {
                errorMessage = "Số lượng phải lớn hơn 0.";
            } else if (!status.equals("Active") && !status.equals("Out of stock")) {
                errorMessage = "Trạng thái chỉ được chọn 'Active' hoặc 'Out of stock'.";
            } else {
                try {
                    Date fromDate = dateFormat.parse(validFrom);
                    Date untilDate = dateFormat.parse(validUntil);
                    if (untilDate.before(fromDate)) {
                        errorMessage = "Ngày hết hạn phải sau ngày bắt đầu.";
                    }
                } catch (ParseException e) {
                    errorMessage = "Định dạng ngày không hợp lệ.";
                }
            }

            if (errorMessage == null) {
                boolean success = voucherDAO.updateVoucher(voucherId, name, discountPercentage, 
                        validFrom, validUntil, soLuong, status);
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/Voucher/viewVoucherManager");
                    return;
                } else {
                    errorMessage = "Cập nhật voucher thất bại.";
                }
            }

        } catch (NumberFormatException e) {
            errorMessage = "Dữ liệu nhập vào không hợp lệ.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/Voucher/updateVoucherManager.jsp").forward(request, response);
    }
}
