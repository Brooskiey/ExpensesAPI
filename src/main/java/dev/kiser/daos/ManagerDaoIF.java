package dev.kiser.daos;

import dev.kiser.entities.Manager;

public interface ManagerDaoIF {

    /**
     * get the manager based on the employee id
     */
    Manager getManagerByID(int empId);
}
