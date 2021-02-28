package dev.kiser.entities;

import java.sql.Date;

/** Expense Java Bean */
public class Expense {

    /** Status of the expense */
    private String status;
    /** Unique id of the expense */
    private int expenseId;
    /** optional reason from manager for expense denied or approval*/
    private String managerReason;
    /** optional reason for expense from employee*/
    private String empReason;
    /** link to the employee */
    private int empId;
    /** the amount to be reimbursed */
    private float amount;
    /** date in unix time of submission */
    private Date submissionDate;
    /** date in unix time of status update */
    private Date statusDate;

    // default constructor
    public Expense(){}

    // constructor
    public Expense(String status, int expenseId, String managerReason, String empReason, int empId,
                   float amount, Date submissionDate, Date statusDate) {
        this.status = status;
        this.expenseId = expenseId;
        this.managerReason = managerReason;
        this.empReason = empReason;
        this.empId = empId;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.statusDate = statusDate;
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
//        SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm z");
//        Date date = new Date(System.currentTimeMillis());
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
}
