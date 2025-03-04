/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
public class ShiftRegistration {

    private int registrationId;
    private int employeeId;
      private String employeeName;
    private Date shiftDate;
    private String shiftTime;
    private String requestStatus;
    private String notes;
    private Integer managerId;
      private String managerName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    private Date approvalDate;
    private Date createdDate;

    public ShiftRegistration(int registrationId, int employeeId, Date shiftDate, String shiftTime, String requestStatus, String notes, Integer managerId, Date approvalDate, Date createdDate) {
        this.registrationId = registrationId;
        this.employeeId = employeeId;
        this.shiftDate = shiftDate;
        this.shiftTime = shiftTime;
        this.requestStatus = requestStatus;
        this.notes = notes;
        this.managerId = managerId;
        this.approvalDate = approvalDate;
        this.createdDate = createdDate;
    }

    public ShiftRegistration(int registrationId, String employeeName, Date shiftDate, String shiftTime, String requestStatus, String notes, String managerName, Date approvalDate, Date createdDate) {
        this.registrationId = registrationId;
        this.employeeName = employeeName;
        this.shiftDate = shiftDate;
        this.shiftTime = shiftTime;
        this.requestStatus = requestStatus;
        this.notes = notes;
        this.managerName = managerName;
        this.approvalDate = approvalDate;
        this.createdDate = createdDate;
    }


    

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
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

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
