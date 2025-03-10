/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.user;

import DAO.adminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import model.Account;

/**
 *
 * @author a
 */
@WebServlet(name="AdminServlet", urlPatterns={"/AdminServlet"})
public class AdminServlet extends HttpServlet {
    
    private adminDAO adminDAO;
    
    @Override
    public void init() {
        adminDAO = new adminDAO();
    }
   
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listAccounts(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteAccount(request, response);
                break;
            default:
                listAccounts(request, response);
                break;
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "create":
                createAccount(request, response);
                break;
            case "update":
                updateAccount(request, response);
                break;
            case "updateRole":
                updateRole(request, response);
                break;
            default:
                listAccounts(request, response);
                break;
        }
    }
    
    private void listAccounts(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String searchKeyword = request.getParameter("search");
        String roleFilter = request.getParameter("roleFilter");
        
        List<Account> accounts = adminDAO.getAllAccounts(searchKeyword, roleFilter);
        
        // Get counts for statistics
        int totalAccounts = adminDAO.countTotalAccounts();
        int adminCount = adminDAO.countAccountsByRole("Admin");
        int staffCount = adminDAO.countAccountsByRole("Staff");
        int customerCount = adminDAO.countAccountsByRole("Customer");
        int managerCount = adminDAO.countAccountsByRole("Manager");
        int guestCount = adminDAO.countAccountsByRole("Guest");
        
        // Set attributes
        request.setAttribute("accounts", accounts);
        request.setAttribute("searchKeyword", searchKeyword);
        request.setAttribute("roleFilter", roleFilter);
        request.setAttribute("totalAccounts", totalAccounts);
        request.setAttribute("adminCount", adminCount);
        request.setAttribute("staffCount", staffCount);
        request.setAttribute("customerCount", customerCount);
        request.setAttribute("managerCount", managerCount);
        request.setAttribute("guestCount", guestCount);
        
        request.getRequestDispatcher("/admin/adminView.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        Account account = adminDAO.getAccountById(userId);
        
        if (account != null) {
            request.setAttribute("account", account);
            request.getRequestDispatcher("/admin/editAccount.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin");
        }
    }
    
    private void createAccount(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        
        // Validate username and email uniqueness
        if (adminDAO.isUsernameExists(username)) {
            request.setAttribute("errorMessage", "Username already exists");
            request.getRequestDispatcher("/admin/adminView.jsp").forward(request, response);
            return;
        }
        
        if (adminDAO.isEmailExists(email)) {
            request.setAttribute("errorMessage", "Email already exists");
            request.getRequestDispatcher("/admin/adminView.jsp").forward(request, response);
            return;
        }
        
        // Create new account
        Account newAccount = new Account(
            0, // ID will be assigned by database
            username,
            password, // In a real app, you should hash this password
            email,
            fullName,
            phoneNumber,
            address,
            role,
            null, // No Google ID for manually created accounts
            new Date(),
            new Date()
        );
        
        boolean success = adminDAO.addAccount(newAccount);
        
        if (success) {
            request.setAttribute("successMessage", "Account created successfully");
        } else {
            request.setAttribute("errorMessage", "Failed to create account");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin");
    }
    
    private void updateAccount(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        
        // Get existing account to preserve password and other fields
        Account existingAccount = adminDAO.getAccountById(userId);
        
        if (existingAccount != null) {
            existingAccount.setUsername(username);
            existingAccount.setEmail(email);
            existingAccount.setFullName(fullName);
            existingAccount.setPhoneNumber(phoneNumber);
            existingAccount.setAddress(address);
            existingAccount.setRole(role);
            
            boolean success = adminDAO.updateAccount(existingAccount);
            
            if (success) {
                request.setAttribute("successMessage", "Account updated successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to update account");
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/admin");
    }
    
    private void updateRole(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String newRole = request.getParameter("newRole");
        
        boolean success = adminDAO.updateUserRole(userId, newRole);
        
        response.setContentType("text/plain");
        response.getWriter().write(success ? "success" : "error");
    }
    
    private void deleteAccount(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        
        boolean success = adminDAO.deleteAccount(userId);
        
        if (success) {
            request.setAttribute("successMessage", "Account deleted successfully");
        } else {
            request.setAttribute("errorMessage", "Failed to delete account");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Admin Servlet for User Management";
    }
}

