package controller.voucher;

import DAO.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet(name = "CreateVoucherManagerServlet", urlPatterns = {"/Voucher/createVoucherManager"})
public class CreateVoucherManagerServlet extends HttpServlet {
    private VoucherDAO voucherDAO = new VoucherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Voucher/createVoucherManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String discountPercentageStr = request.getParameter("discountPercentage");
        String validFromStr = request.getParameter("validFrom");
        String validUntilStr = request.getParameter("validUntil");
        String soLuongStr = request.getParameter("soLuong");
        String status = request.getParameter("status");

        String errorMessage = validateInput(name, discountPercentageStr, validFromStr, validUntilStr, soLuongStr, status);

        if (errorMessage == null) {
            double discountPercentage = Double.parseDouble(discountPercentageStr);
            int soLuong = Integer.parseInt(soLuongStr);

            boolean success = voucherDAO.createVoucher(name, discountPercentage, validFromStr, validUntilStr, soLuong, status);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/Voucher/viewVoucherManager");
                return;
            } else {
                errorMessage = "Failed to create voucher.";
            }
        }

        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/Voucher/createVoucherManager.jsp").forward(request, response);
    }

    private String validateInput(String name, String discountPercentageStr, String validFromStr, 
                                 String validUntilStr, String soLuongStr, String status) {
        try {
            if (name == null || name.length() < 3 || name.length() > 100) {
                return "Voucher name must be between 3 and 100 characters.";
            }

            double discountPercentage = Double.parseDouble(discountPercentageStr);
            if (discountPercentage < 0 || discountPercentage > 100) {
                return "Discount Percentage must be between 0 and 100.";
            }

            LocalDate validFrom = LocalDate.parse(validFromStr);
            LocalDate validUntil = LocalDate.parse(validUntilStr);
            if (validUntil.isBefore(validFrom)) {
                return "Valid Until date must be after Valid From date.";
            }

            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong < 1 || soLuong > 1000) {
                return "Quantity must be between 1 and 1000.";
            }

            if (!"Active".equalsIgnoreCase(status) && !"Out of stock".equalsIgnoreCase(status)) {
                return "Status must be either 'Active' or 'Out of stock'.";
            }
        } catch (NumberFormatException e) {
            return "Invalid number format.";
        } catch (DateTimeParseException e) {
            return "Invalid date format.";
        }

        return null; // Không có lỗi, dữ liệu hợp lệ
    }
}
