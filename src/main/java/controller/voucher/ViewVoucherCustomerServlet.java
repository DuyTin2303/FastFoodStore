import DAO.VoucherDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Voucher;

@WebServlet(name = "ViewVoucherCustomerServlet", urlPatterns = {"/Voucher/viewVoucherCustomer"})
public class ViewVoucherCustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account != null) {
            int userId = account.getUserId(); // Lấy ID khách hàng từ Account trong session

            VoucherDAO voucherDAO = new VoucherDAO();
            List<Voucher> activeVouchers = voucherDAO.getActiveUserVouchers(userId);
            
            request.setAttribute("vouchers", activeVouchers);
            request.getRequestDispatcher("/Voucher/viewVoucherCustomer.jsp").forward(request, response);
        } else {
            response.sendRedirect("loginView.jsp"); // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        }
    }
}
