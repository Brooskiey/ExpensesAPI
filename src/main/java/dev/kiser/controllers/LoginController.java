package dev.kiser.controllers;

import com.google.gson.Gson;
import dev.kiser.daos.EmployeeDaoPostgres;
import dev.kiser.daos.ExpenseDaoPostgres;
import dev.kiser.daos.ManagerDaoPostgres;
import dev.kiser.entities.Employee;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import dev.kiser.utils.JwtUtil;
import dev.kiser.utils.LoginCredientials;
import io.javalin.http.Handler;

public class LoginController {

    private final ExpenseServiceIF expenseService = new ExpenseService(new ExpenseDaoPostgres(),
            new EmployeeDaoPostgres(), new ManagerDaoPostgres());

    /**
     * handler for logging in.
     */
    public Handler loginHandler = (ctx) -> {

        String body = ctx.body();
        Gson gson = new Gson();
        LoginCredientials login = gson.fromJson(body, LoginCredientials.class);

        // get the employee based on the login information
        Employee emp = expenseService.getEmpById(login.getUsername(), login.getPassword());

        // if the login is invalid
        if (emp == null) {
            ctx.result("Invalid login information");
        } else {
            String name = emp.getfName() + " " + emp.getlName();

            // if the employee is a manager
            if (expenseService.getManById(emp.getEmpId()) != null) {

                //add the name and 'manager' role to the jwt
                String jwt = JwtUtil.generate("manager", emp);
                ctx.result(jwt);
            } else {

                //add the name and 'manager' role to the jwt
                String jwt = JwtUtil.generate("employee", emp);
                ctx.result(jwt);

            }
        }
    };


}
