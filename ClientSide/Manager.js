async function getEmployee() {
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

    let date = "";
    let listHtml = ``;

    for (let expense of body) {
        if (expense.statusDate == undefined) {
            date = "";
        } else {
            date = expense.statusDate;
        }
        listHtml = listHtml + `<tr>
        <td> ${expense.expenseId}</td>
        <td style="width:70px; word-break:break-all;"> ${expense.amount}</td>
        <td style="width:400px; word-break:break-all;"> ${expense.empReason}</td>
        <td style="width:80px; word-break:break-all;"> ${expense.status}</td>
        <td style="width:400px; word-break:break-all;"> ${expense.managerReason}</td>
        <td style="width:110px; word-break:break-all;"> ${expense.submissionDate}</td>
        <td style="width:110px; word-break:break-all;"> ${date}</td>
        <td style="width:5px; word-break:break-all;"> ${expense.managerId}</td>
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

        console.log(url)

        const response = await fetch(url, fetchOptions);

        if (response.ok) {
            document.getElementById("amount").value = "";
            document.getElementById("empReason").value = "";
            document.getElementById("close").onclick();
            document.getElementById("sidebar").onclick();
            getEmployee();
        } else {
            document.getElementById("p").innerHTML = "Could not be submitted";
        }
    } catch (error) {
        console.error(error);
    }
}
// document.getElementById("formMan").addEventListener('submit', handlerMan);