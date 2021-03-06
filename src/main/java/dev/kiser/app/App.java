package dev.kiser.app;

import dev.kiser.controllers.ExpenseController;
import dev.kiser.controllers.LoginController;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create(
                config -> {
                    config.enableCorsForAllOrigins();
                }
        );

        ExpenseController expenseController = new ExpenseController();
        LoginController loginController = new LoginController();

        // GET /expenses => return all expenses
        // GET /expenses?eid=2&status=pending => only returns expenses containing that status for that employee
        app.get("/expenses", expenseController.getAllExpensesHandler);

//        // GET /expenses/12/employee/2 => Get expense with ID 12 from employee 2
//        app.get("/expenses/:id/employee/:eid",null);
//
//        // POST /expenses => create a new expense
//        app.post("/expenses",null);
//
//        // PUT /expenses/12/employee/2 => update expense 12 for employee 2
//        app.put("/expenses/:id/employee/:eid", null);
//
//        // DELETE /expenses/12/employee/2 => delete expense with 12 for employee 2
//        app.delete("/expenses/:id/employee/:eid", null);

        // we had users
        app.post("/users/login", loginController.loginHandler);

        app.start(); // starts the web server
    }
}
