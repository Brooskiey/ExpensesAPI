package dev.kiser.entities;

public class Manager extends Employee {

    /**
     * manager id
     */
    private int manId;

    public Manager() {
    }

    public Manager(int empId, String fName, String lName, String email, String password,
                   String username, int manId) {
        super(empId, fName, lName, email, password, username);
        this.manId = manId;
    }

    public int getEmpId() {
        return manId;
    }

    public void setEmpId(int manId) {
        this.manId = manId;
    }
}
