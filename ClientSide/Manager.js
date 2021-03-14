async function getEmployee() {


    const token = localStorage.getItem("jwt"); // jwt token;

    const decoded = JSON.parse(atob(token.split('.')[1]));
    const id = decoded.id;
    console.log(typeof id);

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
        <td> ${expense.amount}</td>
        <td> ${expense.empReason}</td>
        <td> ${expense.status}</td>
        <td> ${expense.managerReason}</td>
        <td> ${expense.submissionDate}</td>
        <td> ${date}</td>
        <td> ${expense.managerId}</td>
        </tr>`;

    }
    return listHtml;
}