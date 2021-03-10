package dev.kiser.servicetests;

import dev.kiser.daos.EmployeeDaoIF;
import dev.kiser.daos.ExpenseDaoIF;
import dev.kiser.daos.ManagerDaoIF;
import dev.kiser.entities.Employee;
import dev.kiser.entities.Expense;
import dev.kiser.entities.Manager;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockTests {


    @Mock
    ExpenseDaoIF xdao = null;
    @Mock
    EmployeeDaoIF edao = null;
    @Mock
    ManagerDaoIF mdao = null;

    ExpenseServiceIF serviceIf = null;


    @BeforeEach
    void setUp() {
        Employee emp = new Employee(1, "Brooke", "Kiser", "email", "Kiser", "Brooke");
        Manager man = new Manager(1, 1);
        Mockito.when(edao.getEmployeeById(1)).thenReturn(emp);
        //Mockito.when(mdao.getManagerByID(1)).thenReturn(man);

        this.serviceIf = new ExpenseService(xdao, edao, mdao);
    }

    @Test
    @Order(1)
    void register_expense() {

        try {
            Expense expense = new Expense(null, 0, "Go with Potato. Go see your sister and Auntie BB.",
                    "I need a plane ticket home",
                    1, 300, null, null, 0);

            serviceIf.registerExpense(1, expense);

            System.out.println("\nregister_expense: assertEquals" +
                    "\n\tExpected: 'Pending'" +
                    "\n\tActual: " + expense.getStatus());
            Assertions.assertEquals("Pending", expense.getStatus());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
