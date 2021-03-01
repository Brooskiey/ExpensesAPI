package dev.kiser.daotests;

import dev.kiser.daos.ManagerDaoIF;
import dev.kiser.daos.ManagerDaoPostgres;
import dev.kiser.entities.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagerTest {

    ManagerDaoIF mdao = new ManagerDaoPostgres();

    @Test
    void get_manager_by_employee_id() {
        int employee_id = 2;

        Manager manager = mdao.getManagerByID(employee_id);

        System.out.println("\nget_manager_by_employee_id: assertEquals" +
                "\n\tExpected: 2" +
                "\n\tActual: " + manager.getEmpId());
        Assertions.assertEquals(2, manager.getEmpId());
    }
}
