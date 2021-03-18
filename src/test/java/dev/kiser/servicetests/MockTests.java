package dev.kiser.servicetests;

import dev.kiser.daos.EmployeeDaoIF;
import dev.kiser.daos.ExpenseDaoIF;
import dev.kiser.daos.ManagerDaoIF;
import dev.kiser.entities.Employee;
import dev.kiser.entities.Expense;
import dev.kiser.entities.Manager;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
        Employee emp = new Employee(3, "Brooke", "Kiser", "email", "Kiser", "Brooke");
        //Manager man = new Manager(1, 1);
        Mockito.when(edao.getEmployeeById(3)).thenReturn(emp);
        //Mockito.when(mdao.getManagerByID(1)).thenReturn(man);

        this.serviceIf = new ExpenseService(xdao, edao, mdao);
    }

    @Test
    @Order(1)
    void register_expense() {
        try {
            Expense expense = new Expense(null, 0, "Go with Potato. Go see your sister and Auntie BB.",
                    "I need a plane ticket home",
                    3, 300, null, null, 0);

            expense = serviceIf.registerExpense(3, expense);

            System.out.println("\nregister_expense: assertEquals" +
                    "\n\tExpected: 'pending'" +
                    "\n\tActual: " + expense.getStatus());
            Assertions.assertEquals("pending", expense.getStatus());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
