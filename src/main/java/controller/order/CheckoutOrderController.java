package controller.order;

import DAO.CartDAO;
import DAO.OrderDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Cart;
import model.Users;
import model.enums.PaymentMethodEnum;
import utils.GeoUtils;
import utils.StringUtils;

@WebServlet(name = "CheckoutOrder", urlPatterns = {"/order/checkout"})
public class CheckoutOrderController extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get params
            String paymentMethod = request.getParameter("paymentMethod");
            if (!StringUtils.isValidEnum(PaymentMethodEnum.class, paymentMethod)) {
                throw new Exception();
            }

            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String specific = request.getParameter("specific");
            String address = String.join(", ", specific, ward, district, city);

            int voucherId;
            try {
                voucherId = Integer.parseInt(request.getParameter("voucherId"));
            } catch (Exception e) {
                voucherId = 0;
            }

            // Preprocess order and orderDetail
            Users user = (Users) request.getSession().getAttribute("account");
            Cart cart = cartDAO.getByUserId(user.getUserId());
            String locationToEstimate = String.join(", ", ward, district, city);
            double estimatedDeliveryMinute = GeoUtils.estimateDeliveryTimeFromLocation(locationToEstimate);
            LocalDateTime estimatedDeliveryTime = LocalDateTime.now().plusMinutes((long) estimatedDeliveryMinute);

            int orderId = orderDAO.add(user.getUserId(),
                    cart.getTotalAmount(),
                    voucherId,
                    paymentMethod,
                    address,
                    estimatedDeliveryTime.toLocalDate());

        } catch (Exception e) {
        }
    }
}
