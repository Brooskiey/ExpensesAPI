package dev.kiser.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Manager extends Employee {

    /**
     * manager id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private int manId;

    public Manager() {
    }

    public Manager(int empId, int manID) {
        super(empId);
        this.manId = manID;
    }

    public Manager(int empId, String fName, String lName, String email, String password,
                   String username, int manId) {
        super(empId, fName, lName, email, password, username);
        this.manId = manId;
    }

    public int getManId() {
        return manId;
    }

    public void setManId(int manId) {
        this.manId = manId;
    }
}
