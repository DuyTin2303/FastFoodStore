package controller.user;

import DAO.userDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "ViewCustomerServlet", urlPatterns = {"/User/viewCustomer"})
public class ViewCustomerServlet extends HttpServlet {

   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // Lấy voucherId từ URL
    String voucherId = request.getParameter("voucherId");

    // Gọi phương thức DAO để lấy danh sách khách hàng
    List<Account> customers = userDAO.getAllCustomers();

    // Đặt danh sách khách hàng và voucherId vào thuộc tính request
    request.setAttribute("customers", customers);
    request.setAttribute("voucherId", voucherId); // Truyền voucherId tới JSP

    // Chuyển tiếp tới trang JSP để hiển thị
    request.getRequestDispatcher("/User/viewCustomer.jsp").forward(request, response);
}
}