package controller.cart;

import DAO.CartDAO;
import DAO.VoucherDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;
import model.Users;

@WebServlet(name = "GetCartController", urlPatterns = {"/cart"})
public class GetCartController extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private VoucherDAO voucherDAO = new VoucherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt((String) request.getSession().getAttribute("userId"));
            Cart cart = cartDAO.getByUserId(userId);

            request.setAttribute("cart", cart);
            request.setAttribute("vouchers", voucherDAO.getAllValid());
            request.setAttribute("totalQuantity", cart.getTotalQuantity());
            request.setAttribute("totalAmount", cart.getTotalAmount());
            request.getRequestDispatcher("/cart/cart.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("/").forward(request, response);
        }

    }
}
