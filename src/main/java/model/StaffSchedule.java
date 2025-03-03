/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Vo Truong Qui - CE181170
 */


public class StaffSchedule {
    private int shiftId; // shift_id
    private int employeeId; // employee_id
     private String employeeName;
    private String shiftDate; // shift_date
    private String shiftTime; // shift_time
    private String status; // status
    private String notes; // notes
    private Integer managerId; // manager_id (có thể NULL)
    private String replacementEmployeeName;
    private Integer replacementEmployeeId; // Sửa từ String thành Integer

// Constructor mới
public StaffSchedule(int shiftId, int employeeId, String employeeName, String shiftDate, String shiftTime, String status, String notes, Integer managerId, Integer replacementEmployeeId) {
    this.shiftId = shiftId;
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.shiftDate = shiftDate;
    this.shiftTime = shiftTime;
    this.status = status;
    this.notes = notes;
    this.managerId = managerId;
    this.replacementEmployeeId = replacementEmployeeId;
}
public StaffSchedule(int shiftId, int employeeId, String shiftDate, String shiftTime, String status, String notes, Integer managerId, Integer replacementEmployeeId) {
    this.shiftId = shiftId;
    this.employeeId = employeeId;
    this.shiftDate = shiftDate;
    this.shiftTime = shiftTime;
    this.status = status;
    this.notes = notes;
    this.managerId = managerId;
    this.replacementEmployeeId = replacementEmployeeId;
}


// Getter & Setter mới
public Integer getReplacementEmployeeId() {
    return replacementEmployeeId;
}

public void setReplacementEmployeeId(Integer replacementEmployeeId) {
    this.replacementEmployeeId = replacementEmployeeId;
}

    
   

    // Constructor đầy đủ

 public StaffSchedule(int shiftId, int employeeId, String employeeName, String shiftDate, String shiftTime, String status, String notes, Integer managerId, String replacementEmployeeName) {
    this.shiftId = shiftId;
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.shiftDate = shiftDate;
    this.shiftTime = shiftTime;
    this.status = status;
    this.notes = notes;
    this.managerId = managerId;
    this.replacementEmployeeName = replacementEmployeeName;
}


    public String getReplacementEmployeeName() {
        return replacementEmployeeName;
    }

    public void setReplacementEmployeeName(String replacementEmployeeName) {
        this.replacementEmployeeName = replacementEmployeeName;
    }





    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    // Getters và Setters
    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}
