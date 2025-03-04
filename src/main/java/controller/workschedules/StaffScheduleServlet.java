/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.workschedules;

import DAO.StaffScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.StaffSchedule;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
@WebServlet(name = "StaffScheduleServlet", urlPatterns = {"/staffSchedule"})
public class StaffScheduleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffScheduleDAO dao = new StaffScheduleDAO();
        List<StaffSchedule> schedules = dao.getAllSchedules();

        request.setAttribute("schedules", schedules);
        request.getRequestDispatcher("/staffSchedule.jsp").forward(request, response);
    }

}
