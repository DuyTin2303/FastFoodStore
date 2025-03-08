package controller.cart;

import DAO.CartDAO;
import DAO.CartItemDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;
import model.CartItems;
import model.Users;

@WebServlet(name = "AddCartController", urlPatterns = {"/cart/add"})
public class AddCartItemController extends HttpServlet {

    private CartItemDAO cartItemDAO = new CartItemDAO();
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Users user = (Users) request.getSession().getAttribute("account"); // Change here
            Cart cart = cartDAO.getByUserId(user.getUserId());

            boolean isSuccess;
            CartItems cartItem = cartItemDAO.getByCartIdAndDishId(cart.getCartId(), dishId);
            if (cartItem != null) {
                isSuccess = cartItemDAO.updateQuantity(cartItem, cartItem.getQuantity() + quantity);
            } else {
                isSuccess = cartItemDAO.add(cart.getCartId(), dishId, quantity);
            }

            if (!isSuccess) {
                throw new Exception();
            }

            response.sendRedirect("/cart");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Can't add this dish to cart");
            response.sendRedirect("/cart");
        }
    }
}
