package controller.delivery;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import DAO.DeliveryDAO;
import model.Delivery;

/**
 * Servlet để hiển thị danh sách đơn hàng giao theo nhân viên
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name = "ViewDeliveryForStaffServlet", urlPatterns = {"/viewDeliveryForStaff"})
public class ViewDeliveryForStaffServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu GET để lấy danh sách giao hàng theo tên nhân viên
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String staffName = request.getParameter("staffName"); // Lấy tên nhân viên từ request

        if (staffName == null || staffName.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập tên nhân viên.");
        } else {
            DeliveryDAO dao = new DeliveryDAO();
            List<Delivery> deliveries = dao.getDeliveriesByStaffName(staffName);
            request.setAttribute("deliveries", deliveries); // Đưa dữ liệu lên request để hiển thị trên JSP
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("viewDeliveryForStaff.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Xử lý yêu cầu POST giống như GET
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet để lấy danh sách đơn hàng giao theo nhân viên.";
    }
}
