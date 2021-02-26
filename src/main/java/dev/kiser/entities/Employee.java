package dev.kiser.entities;

/** Employee Java */
public class Employee {

    /** unique employee id */
    private int empId;
    /** first name */
    private String fName;
    /** last name */
    private String lName;
    /** unique email */
    private String email;
    /** true if manager and false otherwise */
    private boolean manager;

    // default constructor
    public Employee(){}

    // constructor
    public Employee(int empId, String fName, String lName, String email, boolean manager) {
        this.empId = empId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.manager = manager;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }
}
