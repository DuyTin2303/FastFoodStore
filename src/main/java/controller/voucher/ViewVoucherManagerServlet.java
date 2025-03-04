package controller.voucher;

import DAO.VoucherDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Voucher;

@WebServlet(name = "ViewVoucherManagerServlet", urlPatterns = {"/Voucher/viewVoucherManager"})
public class ViewVoucherManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VoucherDAO dao = new VoucherDAO();
        List<Voucher> vouchers = dao.getAllVouchers();

        request.setAttribute("vouchers", vouchers); // Đổi key thành "vouchers" (viết thường)
        request.getRequestDispatcher("/Voucher/viewVoucherManager.jsp").forward(request, response);

    }
}
