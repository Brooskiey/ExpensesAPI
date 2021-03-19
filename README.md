# ExpensesAPI

## Description
The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used
* JavaScript
* HTML
* CSS
* AJAX
* SQL
* Java
* Javalin
* Mockito
* JUnit

## Features
List of features and TODOs for future development

* Manager can approve/deny requests
* Once created, expenses cannot be deleted
* Easy to navigate menu
* Employes and managers can submit expenses
* Managers cannot approve/deny their own expenses
* JWT secuirty tokens
* Username and password protected access

To-do list:

* Add single click button to approve/deny 
* Host on remote server

## Usage
compile: `javac *.java`

run from project home directory: `java main/java/dev/kiser/app/app`
run from app directory: `java app`

To view webpage, navigate to the `ClientSideTech` directory in the project home directory. Copy the path to the `login.html.` It should appear similar to, `file:///C:/Users/<user name>/<home project directory name>/ClientSide/login.html`
Once Gradle has built and javalin server is running, open Google Chrome Web Browser and go to the file path above. From there, a valid user can login with credientials and begin using the prorgram.
