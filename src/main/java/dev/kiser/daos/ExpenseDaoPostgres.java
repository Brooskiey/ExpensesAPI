package dev.kiser.daos;

import dev.kiser.entities.Expense;
import dev.kiser.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ExpenseDaoPostgres implements ExpenseDaoIF {

    Logger logger = Logger.getLogger(ExpenseDaoPostgres.class);


    /**
     * create an expense
     *
     * @param empId   employee id
     * @param expense expense id
     */
    @Override
    public Expense createExpense(int empId, Expense expense) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "insert into expense(status, manager_reason, emp_reason, employee_id, amount) values (?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, expense.getStatus());
            ps.setString(2, expense.getManagerReason());
            ps.setString(3, expense.getEmpReason());
            ps.setInt(4, empId);
            ps.setDouble(5, expense.getAmount());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys(); // return the value of the generated key
            rs.next(); // move cursor forward
            int key = rs.getInt("expense_id");
            Date sub_date = rs.getDate("submission_date");
            expense.setExpenseId(key);
            expense.setStatusDate(sub_date);


            logger.info("Create expense #" + expense.getExpenseId() + " for employee #" + empId);

            return expense;

        } catch (SQLException sqlException) {
            logger.error("Error in creating an expense for employee #" + empId + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * get all Expenses (MANAGER ONLY)
     */
    @Override
    public Set<Expense> getAllExpenses() {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from expense";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Set<Expense> expenses = new HashSet<>();

            // while accounts exist
            while (rs.next()) {

                Expense expense = new Expense();
                expense.setEmpId(rs.getInt("employee_id"));
                expense.setStatus(rs.getString("status"));
                expense.setManagerReason(rs.getString("manager_reason"));
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setAmount(rs.getFloat("amount"));
                expense.setEmpReason(rs.getString("emp_reason"));
                expense.setSubmissionDate(rs.getDate("submission_date"));
                expense.setManagerId(rs.getInt("manager_id"));
                expense.setStatusDate(rs.getDate("status_date"));

                expenses.add(expense);
            }

            logger.info("Get all expenses");

            return expenses;

        } catch (SQLException sqlException) {
            logger.error("Error in getting all expenses\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * get expense by id
     *
     * @param empId     employee id
     * @param expenseId expense id
     */
    @Override
    public Expense getExpenseById(int empId, int expenseId) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from expense where employee_id = ? and expense_id = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ps.setInt(2, expenseId);
            ResultSet rs = ps.executeQuery();

            rs.next();

            Expense expense = new Expense();
            expense.setEmpId(rs.getInt("employee_id"));
            expense.setStatus(rs.getString("status"));
            expense.setManagerReason(rs.getString("manager_reason"));
            expense.setExpenseId(rs.getInt("expense_id"));
            expense.setAmount(rs.getFloat("amount"));
            expense.setEmpReason(rs.getString("emp_reason"));
            expense.setSubmissionDate(rs.getDate("submission_date"));
            expense.setManagerId(rs.getInt("manager_id"));
            expense.setStatusDate(rs.getDate("status_date"));

            logger.info("Get expense with ID " + expenseId + " for employee ID " + empId);

            return expense;

        } catch (SQLException sqlException) {
            logger.error("Error in getting expense with id " + expenseId +
                    " for employee " + empId + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * get expenses by status
     *
     * @param empId  employee id
     * @param status "denied", "approved"
     */
    @Override
    public Set<Expense> getExpensesByStatus(int empId, String status) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from expense where employee_id = ? and status = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();

            Set<Expense> expenses = new HashSet<>();

            while (rs.next()) {

                Expense expense = new Expense();
                expense.setEmpId(rs.getInt("employee_id"));
                expense.setStatus(rs.getString("status"));
                expense.setManagerReason(rs.getString("manager_reason"));
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setAmount(rs.getFloat("amount"));
                expense.setEmpReason(rs.getString("emp_reason"));
                expense.setSubmissionDate(rs.getDate("submission_date"));
                expense.setManagerId(rs.getInt("manager_id"));
                expense.setStatusDate(rs.getDate("status_date"));

                expenses.add(expense);
            }

            logger.info("Get expenses for employee ID " + empId + " with status " + status);

            return expenses;

        } catch (SQLException sqlException) {
            logger.error("Error in getting expenses for employee " + empId +
                    " with status " + status + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * get all expenses by status
     *
     * @param status status of the expenses
     */
    @Override
    public Set<Expense> getExpensesByStatus(String status) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from expense where status = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            Set<Expense> expenses = new HashSet<>();

            while (rs.next()) {

                Expense expense = new Expense();
                expense.setEmpId(rs.getInt("employee_id"));
                expense.setStatus(rs.getString("status"));
                expense.setManagerReason(rs.getString("manager_reason"));
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setAmount(rs.getFloat("amount"));
                expense.setEmpReason(rs.getString("emp_reason"));
                expense.setSubmissionDate(rs.getDate("submission_date"));
                expense.setManagerId(rs.getInt("manager_id"));
                expense.setStatusDate(rs.getDate("status_date"));


                expenses.add(expense);
            }

            logger.info("Get all expenses with status " + status);

            return expenses;

        } catch (SQLException sqlException) {
            logger.error("Error in getting all expenses for employee with status " + status +
                    "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * get all expenses for an employee
     *
     * @param empId employee id
     */
    @Override
    public Set<Expense> getExpenseByEmployee(int empId) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from expense where employee_id = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            Set<Expense> expenses = new HashSet<>();

            while (rs.next()) {

                Expense expense = new Expense();
                expense.setEmpId(rs.getInt("employee_id"));
                expense.setStatus(rs.getString("status"));
                expense.setManagerReason(rs.getString("manager_reason"));
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setAmount(rs.getFloat("amount"));
                expense.setEmpReason(rs.getString("emp_reason"));
                expense.setSubmissionDate(rs.getDate("submission_date"));
                expense.setManagerId(rs.getInt("manager_id"));
                expense.setStatusDate(rs.getDate("status_date"));

                expenses.add(expense);
            }

            logger.info("Get all expenses with ID for employee ID " + empId);

            return expenses;

        } catch (SQLException sqlException) {
            logger.error("Error in getting all expenses with ID for employee ID " + empId + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * update an expense (MANAGER ONLY update status)
     *
     * @param manId   manager id
     * @param expense updated expense
     */
    @Override
    public Expense updateExpense(int manId, Expense expense) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "update expense set status = ?, status_date = ?, manager_id = ?, manager_reason = ? where employee_id = ? and expense_id = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, expense.getStatus());
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setInt(3, expense.getManagerId());
            ps.setString(4, expense.getManagerReason());
            ps.setInt(5, expense.getEmpId());
            ps.setInt(6, expense.getExpenseId());
            ps.execute();

            expense.setManagerId(manId);
            logger.info("Update expense with ID " + expense.getExpenseId() + " for employee ID " + expense.getEmpId() +
                    " and updated by manager ID " + manId);

            return expense;

        } catch (SQLException sqlException) {
            logger.error("Error in updating expense with id " + expense.getExpenseId() +
                    " for employee " + expense.getEmpId() + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * delete an expense
     *
     * @param empId     employee id
     * @param expenseId expense id
     */
    @Override
    public boolean deleteExpense(int empId, int expenseId) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "delete from expense where expense_id = ? and employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, expenseId);
            ps.setInt(2, empId);
            ps.execute();
            logger.info("Delete expense with ID " + expenseId + " for employee ID " + empId);
            return true;
        } catch (SQLException sqlException) {
            logger.error("Error in deleting expense with id " + expenseId +
                    " for employee " + empId + "\n\t\t\t\t\t" + sqlException);
            return false;
        }
    }
}
