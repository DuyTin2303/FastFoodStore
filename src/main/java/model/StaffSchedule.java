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

    private int shiftId;
    private int employeeId;
    private String employeeName;
    private String shiftDate;
    private String shiftTime;
    private String status;
    private String notes;
    private Integer managerId;
    private String replacementEmployeeName;

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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getReplacementEmployeeName() {
        return replacementEmployeeName;
    }

    public void setReplacementEmployeeName(String replacementEmployeeName) {
        this.replacementEmployeeName = replacementEmployeeName;
    }
}
