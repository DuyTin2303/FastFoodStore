package controller.cart;

import DAO.CartItemDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartItems;

@WebServlet(name = "DeleteCartItemController", urlPatterns = {"/cart/delete"})
public class DeleteCartItemController extends HttpServlet {

    private CartItemDAO cartItemDAO = new CartItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

            CartItems cartItem = cartItemDAO.getByCartItemId(cartItemId);
            if (cartItem == null) {
                throw new Exception();
            }

            boolean isSuccess = cartItemDAO.delete(cartItem);
            if (!isSuccess) {
                throw new Exception();
            }

            response.sendRedirect("/cart");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Can't delete this dish");
            response.sendRedirect("/cart");
        }
    }
}
