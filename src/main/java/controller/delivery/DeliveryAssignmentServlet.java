package controller.delivery;

import DAO.DeliveryDAO;
import model.Delivery;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeliveryAssignmentServlet", urlPatterns = {"/deliveryAssignment"})
public class DeliveryAssignmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DeliveryDAO dao = new DeliveryDAO();
        List<Delivery> assignments = dao.getAllDeliveryAssignments();
        
        if (assignments != null) {
            request.setAttribute("assignments", assignments);
            request.getRequestDispatcher("deliveryAssignment.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tải danh sách phân công giao hàng.");
        }
    }
}
