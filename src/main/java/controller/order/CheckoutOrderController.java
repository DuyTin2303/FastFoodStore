package controller.order;

import DAO.CartDAO;
import DAO.CartItemDAO;
import DAO.InventoryDAO;
import DAO.OrderDAO;
import DAO.OrderDetailDAO;
import DAO.VoucherDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Cart;
import model.CartItems;
import model.Orders;
import model.Users;
import model.Vouchers;
import model.enums.OrderStatusEnum;
import model.enums.PaymentMethodEnum;
import utils.GeoUtils;
import utils.StringUtils;
import utils.VNPay;

@WebServlet(name = "CheckoutOrder", urlPatterns = {"/order/checkout"})
public class CheckoutOrderController extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private CartItemDAO cartItemDAO = new CartItemDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private VoucherDAO voucherDAO = new VoucherDAO();
    private InventoryDAO inventoryDAO = new InventoryDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get params
            String paymentMethod = request.getParameter("paymentMethod"); // Use to create order
            if (!StringUtils.isValidEnum(PaymentMethodEnum.class, paymentMethod)) {
                throw new Exception();
            }

            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String specific = request.getParameter("specific");
            String address = String.join(", ", specific, ward, district, city); // Use to create order

            int voucherId;
            try {
                voucherId = Integer.parseInt(request.getParameter("voucherId")); // Use to create order
            } catch (Exception e) {
                voucherId = 0;
            }

            // Process order and orderDetail
            Users user = (Users) request.getSession().getAttribute("account"); // Use to create order
            Cart cart = cartDAO.getByUserId(user.getUserId());

            Vouchers voucher = voucherDAO.getById(voucherId);
            double percentageOfDecrease = voucher != null ? voucher.getDiscountPercentage() / 100 : 0;
            double totalAmount = cart.getTotalAmount() * (1 - percentageOfDecrease); // Use to create order

            String locationToEstimate = String.join(", ", ward, district, city);
            double estimatedDeliveryMinute = GeoUtils.estimateDeliveryTimeFromLocation(locationToEstimate);
            LocalDateTime estimatedDeliveryTime = LocalDateTime.now().plusMinutes((long) estimatedDeliveryMinute); // Use to create order

            int orderId = orderDAO.add(user.getUserId(),
                    totalAmount,
                    voucherId,
                    paymentMethod,
                    address,
                    estimatedDeliveryTime.toLocalDate());
            if (orderId == 0) {
                throw new Exception();
            }

            orderDAO.updateStatus(orderId, OrderStatusEnum.Pending);

            /*
            In each iteration of cartItems in cart
            -> Add new inventory based on dish in cartItem
            -> Add new orderDetail based on inventory and order 
            -> Remove cartItem 
             */
            for (CartItems cartItem : cart.getCartItems()) {
                double originalPrice = cartItem.getTotalPrice();
                double sellingPrice = cartItem.getTotalPrice() * (1 - percentageOfDecrease);

                int inventoryId = inventoryDAO.add(cartItem.getDishId(),
                        0,
                        cartItem.getQuantity(),
                        originalPrice, // Purchase Price
                        sellingPrice); // Selling Price
                orderDetailDAO.add(orderId,
                        inventoryId,
                        cartItem.getQuantity(),
                        sellingPrice,
                        originalPrice * percentageOfDecrease, // Discount
                        originalPrice);
                cartItemDAO.delete(cartItem);
            }

            Orders order = orderDAO.getById(orderId);
            if (paymentMethod.equalsIgnoreCase(PaymentMethodEnum.Online.toString())) {
                String paymentUrl = VNPay.getPaymentURL(order);
                response.sendRedirect(paymentUrl);
            } else {
                request.getSession().setAttribute("success", "Order succesfully! Your order will be deliveried in" + order.getEstimatedDeliveryDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Can't checkout this order. Please try again later!");
        }
        request.getRequestDispatcher("/order").forward(request, response);
    }
}
