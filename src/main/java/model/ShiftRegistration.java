package model;

import java.util.Date;

public class ShiftRegistration {

    private int registrationId;
    private int employeeId; // Mã đơn đăng ký (ID tự động tăng)
    private String employeeName;         // Tên nhân viên đăng ký ca làm
    private String startDate;            // Ngày bắt đầu đăng ký
    private String endDate;              // Ngày kết thúc đăng ký
    private String shiftTime;            // Khung giờ làm (VD: "8h-13h", "13h-18h", "18h-23h")
    private String weekdays;             // Các ngày trong tuần đăng ký (VD: "2,3,5" => Thứ Hai, Ba, Năm)
    private String requestStatus;        // Trạng thái đơn đăng ký (VD: "Chờ duyệt", "Đã duyệt", "Bị từ chối")
    private String notes;                // Ghi chú thêm của nhân viên
    private String managerName;          // Tên người duyệt đơn (quản lý)
    private String approvalDate;         // Ngày duyệt đơn đăng ký
    private String createdDate;          // Ngày tạo đơn đăng ký
    private String shiftDate;            // Ngày đăng ký ca làm
    private String shiftWeekday;
    private int managerId;

    // 📦 Constructors
    public ShiftRegistration() {
        this.requestStatus = "Chờ duyệt"; // Mặc định trạng thái là "Chờ duyệt"
    }

    public ShiftRegistration(int registrationId, int employeeId, String shiftDate,
            String shiftWeekday, String shiftTime, String requestStatus,
            int managerId, String approvalDate, String createdDate) {
        this.registrationId = registrationId;
        this.employeeId = employeeId;
        this.shiftDate = shiftDate;
        this.shiftWeekday = shiftWeekday;
        this.shiftTime = shiftTime;
        this.requestStatus = requestStatus;
        this.managerId = managerId;
        this.approvalDate = approvalDate;
        this.createdDate = createdDate;
    }

    public ShiftRegistration(int registrationId, String employeeName, String startDate, String endDate,
            String shiftTime, String weekdays, String requestStatus, String notes,
            String managerName, String approvalDate, String createdDate) {
        this.registrationId = registrationId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shiftTime = shiftTime;
        this.weekdays = weekdays;
        this.requestStatus = requestStatus;
        this.notes = notes;
        this.managerName = managerName;
        this.approvalDate = approvalDate;
        this.createdDate = createdDate;
    }

    public ShiftRegistration(int registrationId, String employeeName, String startDate, String endDate, String shiftTime, String weekdays, String requestStatus, String notes, String managerName) {
        this.registrationId = registrationId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shiftTime = shiftTime;
        this.weekdays = weekdays;
        this.requestStatus = requestStatus;
        this.notes = notes;
        this.managerName = managerName;
    }

    public String getShiftWeekday() {
        return shiftWeekday;
    }

    public void setShiftWeekday(String shiftWeekday) {
        this.shiftWeekday = shiftWeekday;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    // 🛠️ Getters and Setters
    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
