package dev.kiser.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import dev.kiser.daos.EmployeeDaoPostgres;
import dev.kiser.daos.ExpenseDaoPostgres;
import dev.kiser.daos.ManagerDaoPostgres;
import dev.kiser.entities.Expense;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import dev.kiser.utils.JwtUtil;
import io.javalin.http.Handler;

import java.util.Set;

public class ExpenseController {

    private final ExpenseServiceIF expenseService = new ExpenseService(new ExpenseDaoPostgres(), new EmployeeDaoPostgres(), new ManagerDaoPostgres());

    public Handler getAllExpensesHandler = (ctx) -> {

        String eid = ctx.queryParam("eid", "NONE");// second value is default value
        String status = ctx.queryParam("status", "NONE");

        try {
            String jwt = ctx.queryParam("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt); // returns decodeJWT or throws an exception

            if (decodedJWT.getClaim("role").asString().equals("manager")) {

                if (eid.equals("NONE") || status.equals("NONE")) {

                    Set<Expense> allExpenses = this.expenseService.getAllExpenses();
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(allExpenses); // convert any Java object or collection of Java objects into a JSON String version
                    ctx.result(expenseJSON); // this does not execute immediately you could add more to end
                    ctx.status(200); // signifies request was processed with no problems
                } else {
                    Set<Expense> expenses = this.expenseService.getExpenseByStatus(-1, status);
                    Gson gson = new Gson();
                    String statusJSON = gson.toJson(expenses);
                    ctx.result(statusJSON);
                    ctx.status(200);
                }
            } else {
                if (eid != null) {

                    int empId = Integer.parseInt(eid);
                    Set<Expense> expenses = this.expenseService.getExpenseByStatus(empId, status);
                    Gson gson = new Gson();
                    String statusJSON = gson.toJson(expenses);
                    ctx.result(statusJSON);
                    ctx.status(200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(403);
            ctx.result("Missing authorization or improper token");
        }
    };


}
