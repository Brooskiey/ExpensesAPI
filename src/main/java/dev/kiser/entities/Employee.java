package dev.kiser.entities;

/**
 * Employee Java
 */
public class Employee {

    /**
     * unique employee id
     */
    private int empId;
    /**
     * first name
     */
    private String fName;
    /**
     * last name
     */
    private String lName;
    /**
     * unique email
     */
    private String email;

    /**
     * the employee's password
     */
    private String password;
    /**
     * the employee's username
     */
    private String username;

    // default constructor
    public Employee() {
    }

    public Employee(int empId) {
        this.empId = empId;
    }

    // constructor
    public Employee(int empId, String fName, String lName, String email, String password,
                    String username) {
        this.empId = empId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
