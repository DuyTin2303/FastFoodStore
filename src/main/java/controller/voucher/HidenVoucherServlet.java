/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.voucher;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.VoucherDAO;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name="HidenVoucherServlet", urlPatterns={"/Voucher/hidenVoucher"})

public class HidenVoucherServlet extends HttpServlet {

  @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String voucherIdParam = request.getParameter("voucherId");
    
    if (voucherIdParam == null || voucherIdParam.isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/Voucher/viewVoucherManager");
        return;
    }
    
    int voucherId = Integer.parseInt(voucherIdParam);
    VoucherDAO voucherDAO = new VoucherDAO();
    boolean isHidden = voucherDAO.hideVoucher(voucherId);

    if (isHidden) {
        // Chuyển hướng về trang danh sách voucher sau khi ẩn thành công
        response.sendRedirect(request.getContextPath() + "/Voucher/viewVoucherManager");
    } else {
        // Chuyển hướng kèm thông báo lỗi nếu không ẩn được
        request.setAttribute("message", "Không thể ẩn voucher.");
        request.getRequestDispatcher("/Voucher/viewVoucherManager.jsp").forward(request, response);
    }
}
}