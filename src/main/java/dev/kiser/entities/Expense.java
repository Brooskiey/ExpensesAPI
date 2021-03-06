package dev.kiser.entities;

import javax.persistence.*;
import java.sql.Date;

/** Expense Java Bean */
public class Expense {

    /** Status of the expense */
    @Column(name = "status")
    private String status;

    /** Unique id of the expense */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "last_name")
    private int expenseId;

    /** optional reason from manager for expense denied or approval */
    @Column(name = "manager_reason")
    private String managerReason;

    /** optional reason for expense from employee */
    @Column(name = "emp_reason")
    private String empReason;

    /** link to the employee */
    @Column(name = "employee_id")
    @JoinColumn(name = "employee_id")
    private int empId;

    /** the amount to be reimbursed */
    @Column(name = "amount")
    private float amount;

    /** date in unix time of submission */
    @Column(name = "submission_date")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date submissionDate;

    /** date in unix time of status update */
    @Column(name = "status_date")
    private Date statusDate;

    /** approving manager */
    @Column(name = "manager_id")
    @JoinColumn(name = "manager_id")
    private int managerId;

    // default constructor
    public Expense() {
    }

    // constructor
    public Expense(String status, int expenseId, String managerReason, String empReason, int empId,
                   float amount, Date submissionDate, Date statusDate, int managerId) {
        this.status = status;
        this.expenseId = expenseId;
        this.managerReason = managerReason;
        this.empReason = empReason;
        this.empId = empId;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.statusDate = statusDate;
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getManagerReason() {
        return managerReason;
    }

    public void setManagerReason(String managerReason) {
        this.managerReason = managerReason;
    }

    public String getEmpReason() {
        return empReason;
    }

    public void setEmpReason(String empReason) {
        this.empReason = empReason;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Expense{\n" +
                "status='" + status + '\'' +
                ", \nexpenseId=" + expenseId +
                ", \nmanagerReason='" + managerReason + '\'' +
                ", \nempReason='" + empReason + '\'' +
                ", \nempId=" + empId +
                ", \namount=" + amount +
                ", \nsubmissionDate=" + submissionDate +
                ", \nstatusDate=" + statusDate +
                ", \nmanagerId=" + managerId +
                '}';
    }
}
