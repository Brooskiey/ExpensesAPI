async function getEmployeeMan() {
    const table = document.getElementById("expenseTableBody ")
    const thead = document.getElementById("thead")
    thead.innerHTML = `<th class="th-sm " scope="col ">ID</th>
    <th class="th-sm " scope="col ">Employee ID</th>
    <th class="th-sm " scope="col ">Amount</th>
    <th class="th-sm " scope="col ">Employee Reason</th>
    <th class="th-sm " scope="col ">Status</th>
    <th class="th-sm " scope="col ">Manager Reason</th>
    <th class="th-sm " scope="col ">Submission Date</th>
    <th class="th-sm " scope="col ">Status Date</th>
    <th class="th-sm " scope="col ">Manager</th>`

    const details = {
        headers: {
            "Authorization": token
        }
    }

    const httpResponse = await fetch(`http://localhost:7000/expenses?eid=` + -1, details);
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
        <td style="width:110px; word-break:break-word; text-align: center;"> ${expense.submissionDate}</td>
        <td style="width:120px; word-break:break-word; text-align: center;"> ${date}</td>
        <td style="width:5px; word-break:break-all; text-align: center;"> ${manager}</td>
        </tr>`;
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

const handlerMan = async function(event) {

    event.preventDefault();

    try {
        const form = event.currentTarget;
        const url = form.action;

        const formData = new FormData(form);
        const plainFormData = Object.fromEntries(formData.entries());
        const formDataJsonString = JSON.stringify(plainFormData);
        const token = localStorage.getItem('jwt');
        console.log(formDataJsonString);

        const fetchOptions = {
            method: "POST",
            headers: {
                "Authorization": token
            },
            body: formDataJsonString,
        };

        const response = await fetch(url, fetchOptions);

        if (response.ok) {
            document.getElementById("amount").value = "";
            document.getElementById("empReason").value = "";
            document.getElementById("close").onclick();
            document.getElementById("sidebar").onclick();
            getEmployeeMan();
        } else {
            document.getElementById("p").innerHTML = "Could not be submitted";
        }
    } catch (error) {
        console.error(error);
    }
}

const getEmployee = async function() {
    const table = document.getElementById("expenseTableBody ")
    const thead = document.getElementById("thead")
    thead.innerHTML = `<th class="th-sm " scope="col ">ID</th>
    <th class="th-sm " scope="col ">Employee ID</th>
    <th class="th-sm " scope="col ">Amount</th>
    <th class="th-sm " scope="col ">Employee Reason</th>
    <th class="th-sm " scope="col ">Status</th>
    <th class="th-sm " scope="col ">Manager Reason</th>
    <th class="th-sm " scope="col ">Submission Date</th>
    <th class="th-sm " scope="col ">Status Date</th>
    <th class="th-sm " scope="col ">Manager</th>`

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
        <td style="width:110px; word-break:break-word; text-align: center;"> ${expense.submissionDate}</td>
        <td style="width:120px; word-break:break-word; text-align: center;"> ${date}</td>
        <td style="width:5px; word-break:break-all; text-align: center;"> ${manager}</td>`
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

const update = async function() {
    const table = document.getElementById("expenseTableBody ");
    const thead = document.getElementById("thead")
    thead.innerHTML = `<thead style="text-align: center;" id="thead">
    <th class="th-sm " scope="col ">ID</th>
    <th class="th-sm " scope="col ">Employee ID</th>
    <th class="th-sm " scope="col ">Amount</th>
    <th class="th-sm " scope="col ">Employee Reason</th>
    <th class="th-sm " scope="col ">Status</th>
    <th class="th-sm " scope="col ">Manager Reason</th>
    <th class="th-sm " scope="col ">Submission Date</th>
</thead>`

    const token = localStorage.getItem("jwt"); // jwt token;
    const decoded = JSON.parse(atob(token.split('.')[1]));
    const id = decoded.id;

    const details = {
        headers: {
            "Authorization": token
        }
    }

    const httpResponse = await fetch(`http://localhost:7000/expenses?eid=` + -1 + "&status=pending", details);
    const body = await httpResponse.json();

    let approve = "";
    let listHtml = ``;
    let control = true;

    for (let expense of body) {

        if (id == expense.empId) {
            control = false;
            approve = "";
        } else {
            approve = `<button name="open" type="button" id="appdenU" class="btn" onclick="updateModal(this)" style="background-color:rgb(194, 84, 194); opacity: 1.0;">Approve/Deny</button>`;
            control = true;
        }

        if (control) {
            listHtml = listHtml + `<tr>
        <td class="nr" style="width:5px; text-align: left;"> ${expense.expenseId}</td>
        <td style="width:5px; text-align: center;"> ${expense.empId}</td>
        <td style="width:70px; word-break:break-all; text-align: center;"> ${expense.amount}</td>
        <td style="width:400px; word-break:break-all;"> ${expense.empReason}</td>
        <td style="text-align: center;">${approve}</td>
        <td style="width:400px; word-break:break-all;"> ${expense.managerReason}</td>
        <td style="width:110px; word-break:break-word; text-align: center;"> ${expense.submissionDate}</td>`
            control = false;
        }
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

const updateModalRow = async function(event) {

    event.preventDefault();
    const token = localStorage.getItem("jwt"); // jwt token;
    const decoded = JSON.parse(atob(token.split('.')[1]));
    const id = decoded.id;

    try {
        const form = event.currentTarget;
        const url = form.action;

        const formData = new FormData(form);
        const plainFormData = Object.fromEntries(formData.entries());
        const formDataJsonString = JSON.stringify(plainFormData);

        console.log(plainFormData);

        const fetchOptions = {
            method: "PUT",
            headers: {
                "Authorization": token
            },
            body: formDataJsonString,
        };

        const response = await fetch(`http://localhost:7000/expenses/${Number.parseInt(plainFormData.expenseId)}/manager/${id}`, fetchOptions);

        if (response.ok) {
            document.getElementById("exId").value = "";

            input = document.getElementById("empIdU").value = "";

            input = document.getElementById("amountU").value = "";

            input = document.getElementById("empr").value = "";

            input = document.getElementById("empReason").value = "";
            document.getElementById("closeU").onclick();
            update();
        } else {
            document.getElementById("p").innerHTML = "Could not be submitted";
        }
    } catch (error) {
        console.error(error);
    }
}

const updateModal = function(val) {
    // Get the modal
    const appmodal = document.getElementById("appModal");

    // Get the button that opens the modal
    const appbtn = val;

    // Get the <span> element that closes the modal
    const spanU = document.getElementsByClassName("closeU")[0];

    const closeU = document.getElementById("closeU");

    // When the user clicks the button, open the modal
    appbtn.onclick = function(val) {
        appmodal.style.display = "block";

    }

    // When the user clicks on <span> (x), close the modal
    spanU.onclick = function() {
        appmodal.style.display = "none";
    }

    // When the user clicks on <span> (x), close the modal
    closeU.onclick = function() {
        appmodal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function() {
        appmodal.modal({
            backdrop: 'static',
            keyboard: false
        })
        if (val.target == appmodal) {
            appmodal.style.display = "none";
        }
    }
    let arr = [];

    val.parentNode.parentNode.querySelectorAll('td').forEach(item => {
        if (item.getAttribute('class') != "text-center") {
            arr.push(item.innerHTML)
        }
    }, this)

    let input = document.getElementById("manId");
    const tokenU = localStorage.getItem('jwt'); // jwt token;

    const decodedU = JSON.parse(atob(tokenU.split('.')[1]));
    input.value = decodedU.id;

    input = document.getElementById("exId");
    input.value = arr[0];

    input = document.getElementById("empIdU");
    input.value = arr[1];

    input = document.getElementById("amountU");
    input.value = arr[2];

    input = document.getElementById("empr");
    input.value = arr[3];

    document.getElementById("formManU").addEventListener('submit', updateModalRow);
}