package dev.kiser.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import dev.kiser.daos.EmployeeDaoPostgres;
import dev.kiser.daos.ExpenseDaoPostgres;
import dev.kiser.daos.ManagerDaoPostgres;
import dev.kiser.entities.Expense;
import dev.kiser.exceptions.*;
import dev.kiser.services.ExpenseService;
import dev.kiser.services.ExpenseServiceIF;
import dev.kiser.utils.JwtUtil;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.Set;

public class ExpenseController {

    private final ExpenseServiceIF expenseService = new ExpenseService(new ExpenseDaoPostgres(), new EmployeeDaoPostgres(), new ManagerDaoPostgres());
    private final Logger logger = Logger.getLogger(ExpenseController.class);

    /**
     * Create a new expense based on given expense
     */
    public Handler createExpense = ctx -> {
        // check the jwt sent via authorization
        String jwt = ctx.queryParam("Authorization");
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);

        // get the expense sent
        String body = ctx.body();
        Gson gson = new Gson();
        Expense expense = gson.fromJson(body, Expense.class);


        // create the expense
        expenseService.registerExpense(expense.getEmpId(), expense);
        // send back the expense with the new expense id
        String json = gson.toJson(expense);
        ctx.result(json);
        ctx.status(201);
    };

    /**
     * Get all expenses handler Determines whether to use employee or manager version for the query params Only manager
     * can get all expenses for all employees
     */
    public Handler getAllExpensesHandler = ctx -> {

        // get query params
        String eid = ctx.queryParam("eid", "NONE");
        String status = ctx.queryParam("status", "NONE");
        String expensesJSON; // null values JSON

        try {
            // check the jwt sent via authorization
            String jwt = ctx.queryParam("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt); // make sure it is valid

            // role must be manager
            if (decodedJWT.getClaim("role").asString().equals("manager")) {
                // run the isManager section
                expensesJSON = isManager(eid, status);

            } else {
                // role is employee
                // run the isEmployee section
                expensesJSON = isEmployee(eid, status);
            }
            // send the JSON
            ctx.result(expensesJSON);
            ctx.status(200);

        } catch (JWTVerificationException e) {
            logger.error("Authorization error");
            ctx.status(403);
            ctx.result("Missing authorization or improper token");

        } catch (OperationNotPossible | EmployeeNotFoundException | NumberFormatException e) {
            logger.error(e.getMessage());
            ctx.status(404);
            ctx.result(e.getMessage());
        }
    };
    /**
     * Get the expense by id
     */
    public Handler getExpenseById = ctx -> {
        // get path params
        String exId = ctx.pathParam("xid");
        String empId = ctx.pathParam("eid");

        try {
            // check the jwt sent via authorization
            String jwt = ctx.queryParam("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);

            // make sure the values are integers
            int xid = Integer.parseInt(exId);
            int eid = Integer.parseInt(empId);

            // no error thrown, get the expense
            Expense expense = expenseService.getExpenseById(eid, xid);

        } catch (NumberFormatException e) {
            logger.error("The numerical values aren't numbers: " + exId + " : " + empId);
            ctx.result("The ids provided are not numbers");
            ctx.status(404);

        } catch (EmployeeNotFoundException e) {
            logger.error(e.getMessage());
            ctx.status(404);
            ctx.result(e.getMessage());
        }
    };
    /**
     * Update the expense (MANAGER ONLY)
     */
    public Handler updateExpense = ctx -> {

        // get the path params
        String exId = ctx.pathParam("xid");
        String empId = ctx.pathParam("eid");

        try {
            // check the jwt sent via authorization
            String jwt = ctx.queryParam("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt); // make sure it is valid

            // get the updated expense
            String body = ctx.body();
            Gson gson = new Gson();
            Expense newExpense = gson.fromJson(body, Expense.class);

            // try to decode the the jwt and turn the params into ints


            int xid = Integer.parseInt(exId);
            int eid = Integer.parseInt(empId);
            // make sure the expense id is the expense id
            newExpense.setExpenseId(xid);

            // role must be manager
            if (decodedJWT.getClaim("role").asString().equals("manager")) {
                Expense expense = expenseService.updateExpense(eid, newExpense);

            } else {
                ctx.result("You are not authorized to complete this task");
                ctx.status(404);
            }

        } catch (NumberFormatException e) {
            // the path params were not integers
            logger.error("The numerical values aren't numbers: " + exId + " : " + empId);
            ctx.result("The ids provided are not numbers");
            ctx.status(404);

        } catch (JWTVerificationException e) {
            // jwt could not be decoded
            logger.error("Authorization error");
            ctx.status(403);
            ctx.result("Missing authorization or improper token");

        } catch (ManagerNotFoundException | OperationNotPossible | ExpenseNotFoundException e) {
            logger.error(e.getMessage());
            ctx.status(404);
            ctx.result(e.getMessage());
        }
    };
    /**
     * Delete the expense
     */
    public Handler deleteExpense = ctx -> {
        // get the path params
        String exId = ctx.pathParam("xid");
        String empId = ctx.pathParam("eid");

        // try to decode the the jwt and turn the params into ints
        try {
            // check the jwt sent via authorization
            String jwt = ctx.queryParam("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);

            int xid = Integer.parseInt(exId);
            int eid = Integer.parseInt(empId);
            // make sure the expense id is the expense id

            boolean result = expenseService.deleteExpense(eid, xid);

            if (!result) {
                ctx.result("Expense could not be deleted");
                ctx.status(404);

            } else {
                ctx.result("Expense deleted");
                ctx.status(200);
            }
        } catch (NumberFormatException e) {
            // the path params were not integers
            logger.error("The numerical values aren't numbers: " + exId + " : " + empId);
            ctx.result("The ids provided are not numbers");
            ctx.status(404);

        } catch (EmployeeNotFoundException | ExpenseNotFoundException | OperationNotPossible e) {
            logger.error(e.getMessage());
            ctx.status(404);
            ctx.result(e.getMessage());
        }
    };

    /**
     * Handles the case of the employee being a manager
     *
     * @param eid    employee id given by URI
     * @param status status given by URI
     *
     * @return the JSON string or null
     */
    private String isManager(String eid, String status)
            throws OperationNotPossible, EmployeeNotFoundException {

        Set<Expense> expenses;

        // get all expenses if query params do not exist
        if (eid.equals("NONE") || status.equals("NONE")) {
            expenses = this.expenseService.getAllExpenses();

        } else {
            // get all expenses for all employees
            // -1 is the value used since no id generated by the database is negative
            expenses = this.expenseService.getExpenseByStatus(-1, status);
        }

        // create and send the JSON
        Gson gson = new Gson();
        return gson.toJson(expenses);
    }

    /**
     * Handles the case of the employee not being a manager
     *
     * @param eid    employee id given by URI
     * @param status status given by URI
     *
     * @return JSON string, null is in get expense methods, and 'null' eid for no eid given
     */
    private String isEmployee(String eid, String status)
            throws OperationNotPossible, ExpenseException, EmployeeNotFoundException {

        Set<Expense> expenses;

        if (eid != null) {
            // get the employee id to get the expenses for
            int empId = Integer.parseInt(eid);

            // if the status is nothing, we are getting all the expenses for an employee
            if (status.equals("NONE")) {
                expenses = this.expenseService.getExpensesByEmployee(empId);

            } else {
                // otherwise get the expenses by status
                expenses = this.expenseService.getExpenseByStatus(empId, status);
            }
        } else {
            throw new OperationNotPossible("Employee ID was null");
        }

        // create and send the JSON
        Gson gson = new Gson();
        return gson.toJson(expenses);
    }
}
