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


    public Handler loginHandler = (ctx) -> {
        String user = ctx.pathParam("username");
        String pass = ctx.pathParam("password");

        Employee emp = expenseService.getEmpById(user, pass);
        if (emp == null) {
            ctx.result("Invalid login information");
        } else {
            String name = emp.getfName() + " " + emp.getlName();

            String jwt = null;

            if (expenseService.getManById(emp.getEmpId()) == null) {
                jwt = JwtUtil.generate("manager", name);
            } else {
                jwt = JwtUtil.generate("employee", name);
            }
            ctx.result(jwt);
        }
    };
}
