package dev.kiser.controllers;

import dev.kiser.daos.EmployeeDaoPostgres;
import dev.kiser.daos.ExpenseDaoPostgres;
import dev.kiser.daos.ManagerDaoPostgres;
import dev.kiser.entities.Employee;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import dev.kiser.utils.JwtUtil;
import io.javalin.http.Handler;

public class LoginController {

    private final ExpenseServiceIF expenseService = new ExpenseService(new ExpenseDaoPostgres(),
            new EmployeeDaoPostgres(), new ManagerDaoPostgres());

    /**
     * handler for logging in.
     */
    public Handler loginHandler = (ctx) -> {

        // get the username
        String user = ctx.pathParam("username");
        String pass = ctx.pathParam("password");

        // get the employee based on the login information
        Employee emp = expenseService.getEmpById(user, pass);

        // if the login is invalid
        if (emp == null) {
            ctx.result("Invalid login information");
        } else {
            String name = emp.getfName() + " " + emp.getlName();

            // if the employee is a manager
            if (expenseService.getManById(emp.getEmpId()) == null) {

                //add the name and 'manager' role to the jwt
                String jwt = JwtUtil.generate("manager", name);
                ctx.result(jwt);
            } else {

                //add the name and 'manager' role to the jwt
                String jwt = JwtUtil.generate("employee", name);
                ctx.result(jwt);

            }
        }
    };
}
