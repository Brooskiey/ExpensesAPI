// get employee expenses
// create expense
// delete pending expense
// employee info
// skillet search button for some flare (non-functional)
const getEmployee = async function() {
    const table = document.getElementById("expenseTableBody ")

    const token = localStorage.getItem("jwt"); // jwt token;
    const decoded = JSON.parse(atob(token.split('.')[1]));
    const id = decoded.id;

    const details = {
        headers: {
            "Authorization": token
        }
    }

    const httpResponse = await fetch(`http://localhost:7000/expenses?eid=` + id, details);
    const body = await httpResponse.json();

    let manager = "";
    let date = "";
    let listHtml = ``;

    for (let expense of body) {
        if (expense.statusDate == undefined) {
            date = "";
        } else {
            date = expense.statusDate;
        }

        if (expense.managerId == 0) {
            manager = "";
        } else {
            manager = expense.managerId;
        }
        listHtml = listHtml + `<tr>
        <td style="width:5px; text-align: left;"> ${expense.expenseId}</td>
        <td style="width:5px; text-align: center;"> ${expense.empId}</td>
        <td style="width:70px; word-break:break-all; text-align: center;"> ${expense.amount}</td>
        <td style="width:400px; word-break:break-all;"> ${expense.empReason}</td>
        <td style="width:80px; word-break:break-all; text-align: center;"> ${expense.status}</td>
        <td style="width:400px; word-break:break-all;"> ${expense.managerReason}</td>
        <td style="width:110px; word-break:break-all; text-align: center;"> ${expense.submissionDate}</td>
        <td style="width:120px; word-break:break-all; text-align: center;"> ${date}</td>
        <td style="width:5px; word-break:break-all; text-align: center;"> ${manager}</td>`;

    }

    table.innerHTML = listHtml;
    //https://www.w3schools.com/howto/tryit.asp?filename=tryhow_js_sort_table

    var rows, switching, i, x, y, shouldSwitch;
    switching = true;
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 0; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("td")[0];
            y = rows[i + 1].getElementsByTagName("td")[0];
            if (parseInt(x.innerHTML) > parseInt(y.innerHTML)) {
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}

async function createExpense() {
    $("#myModal").modal()
}