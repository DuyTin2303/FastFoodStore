    package controller.workschedules;

    import DAO.StaffScheduleDAO;

    import java.io.IOException;
    import java.sql.SQLException;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    /**
     * Servlet xử lý thêm lịch làm việc cho nhân viên
     *
     * @author Vo Truong Qui - CE181170
     */
    @WebServlet(name = "CreateStaffScheduleServlet", urlPatterns = {"/createStaffSchedule"})
    public class CreateStaffScheduleServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            request.getRequestDispatcher("/createStaffSchedule.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // Lấy dữ liệu từ form
            String employeeName = request.getParameter("employeeName");
            String shiftDate = request.getParameter("shiftDate");
            String shiftTime = request.getParameter("shiftTime");
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");
            String managerName = request.getParameter("managerName");
            String replacementEmployeeName = request.getParameter("replacementEmployeeName");

            // Kiểm tra dữ liệu đầu vào
            if (isInvalid(employeeName) || isInvalid(shiftDate) || isInvalid(shiftTime) || isInvalid(status) || isInvalid(managerName)) {
                request.setAttribute("message", "❌ Vui lòng điền đầy đủ thông tin bắt buộc!");
                redirectToForm(request, response, employeeName, shiftDate, shiftTime, status, notes, managerName, replacementEmployeeName);
                return;
            }

            // Kiểm tra xem managerName có phải là một Manager hợp lệ không
            try {
                if (!isValidManager(managerName)) {
                    request.setAttribute("message", "❌ Người quản lý không hợp lệ!");
                    redirectToForm(request, response, employeeName, shiftDate, shiftTime, status, notes, managerName, replacementEmployeeName);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "❌ Lỗi khi kiểm tra người quản lý!");
                redirectToForm(request, response, employeeName, shiftDate, shiftTime, status, notes, managerName, replacementEmployeeName);
                return;
            }

            // Gọi DAO để thêm lịch làm việc
            StaffScheduleDAO dao = new StaffScheduleDAO();
            boolean isInserted = dao.addSchedule(employeeName, shiftDate, shiftTime, status, notes, managerName, replacementEmployeeName);

            if (isInserted) {
                // ✅ Thêm thành công → Chuyển hướng về trang staffSchedule
                response.sendRedirect("staffSchedule");
            } else {
                request.setAttribute("message", "❌ Không thể thêm lịch làm việc. Kiểm tra lại dữ liệu.");
                redirectToForm(request, response, employeeName, shiftDate, shiftTime, status, notes, managerName, replacementEmployeeName);
            }
        }

        /**
         * Kiểm tra xem một chuỗi có hợp lệ hay không
         */
        private boolean isInvalid(String value) {
            return value == null || value.trim().isEmpty();
        }

        /**
         * Kiểm tra xem managerName có phải là một Manager hợp lệ không
         */
        private boolean isValidManager(String managerName) throws SQLException {
            StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
            return staffScheduleDAO.isValidManager(managerName);
        }

        /**
         * Chuyển hướng lại trang tạo lịch làm việc với dữ liệu giữ lại
         */
        private void redirectToForm(HttpServletRequest request, HttpServletResponse response,
                String employeeName, String shiftDate, String shiftTime,
                String status, String notes, String managerName, String replacementEmployeeName)
                throws ServletException, IOException {
            request.setAttribute("employeeName", employeeName);
            request.setAttribute("shiftDate", shiftDate);
            request.setAttribute("shiftTime", shiftTime);
            request.setAttribute("status", status);
            request.setAttribute("notes", notes);
            request.setAttribute("managerName", managerName);
            request.setAttribute("replacementEmployeeName", replacementEmployeeName);
            request.getRequestDispatcher("createStaffSchedule.jsp").forward(request, response);
        }
    }
