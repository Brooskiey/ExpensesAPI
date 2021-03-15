package dev.kiser.controllers;

import com.google.gson.Gson;
import dev.kiser.daos.EmployeeDaoPostgres;
import dev.kiser.daos.ExpenseDaoPostgres;
import dev.kiser.daos.ManagerDaoPostgres;
import dev.kiser.entities.Employee;
import dev.kiser.exceptions.LoginFailedExceptions;
import dev.kiser.exceptions.ManagerNotFoundException;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import dev.kiser.utils.JwtUtil;
import dev.kiser.utils.LoginCredential;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

public class LoginController {

    private final ExpenseServiceIF expenseService = new ExpenseService(new ExpenseDaoPostgres(),
            new EmployeeDaoPostgres(), new ManagerDaoPostgres());
    Logger logger = Logger.getLogger(EmployeeDaoPostgres.class);


    /**
     * handler for logging in.
     */
    public Handler loginHandler = (ctx) -> {

        String body = ctx.body();
        Gson gson = new Gson();
        LoginCredential login = gson.fromJson(body, LoginCredential.class);
        Employee emp;

        try {
            // get the employee based on the login information
            emp = expenseService.getEmpById(login.getUsername(), login.getPassword());
            // if the employee is a manager
            expenseService.getManById(emp.getEmpId());
            //add the name and 'manager' role to the jwt
            String jwt = JwtUtil.generate("manager", emp);
            ctx.result(jwt);
            ctx.status(200);

        } catch (ManagerNotFoundException e) {
            emp = expenseService.getEmpById(login.getUsername(), login.getPassword());

            //add the name and 'employee' role to the jwt
            String jwt = JwtUtil.generate("employee", emp);
            ctx.result(jwt);
            ctx.status(200);

        } catch (LoginFailedExceptions e) {
            ctx.status(404);
        }
    };


}
