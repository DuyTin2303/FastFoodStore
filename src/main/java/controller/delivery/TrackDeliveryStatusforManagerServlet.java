package controller.delivery;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Delivery;
import DAO.DeliveryDAO;

@WebServlet(name="TrackDeliveryStatusforManagerServlet", urlPatterns={"/Delivery/trackDeliveryStatusforManager"})
public class TrackDeliveryStatusforManagerServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Create an instance of DeliveryDAO
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        
        // Retrieve all orders with their statuses for the manager
        List<Delivery> ordersWithStatus = deliveryDAO.getAllOrdersWithStatusForManager();
        
        // Set the list of deliveries as a request attribute
        request.setAttribute("ordersWithStatus", ordersWithStatus);
        
        // Forward the request to the JSP page for rendering the data
        request.getRequestDispatcher("/Delivery/trackDeliveryStatusforManager.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // If any POST-related processing is needed, implement here
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Track Delivery Status for Manager Servlet";
    }
}
