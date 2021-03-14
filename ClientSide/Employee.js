// get employee expenses
// create expense
// delete pending expense
// employee info
// skillet search button for some flare (non-functional)
// async function getEmployee() {


//     const token = localStorage.getItem("jwt"); // jwt token;

//     const decoded = JSON.parse(atob(token.split('.')[1]));
//     const id = decoded.id;
//     console.log(typeof id);

//     const details = {
//         headers: {
//             "Authorization": token
//         }
//     }

//     const httpResponse = await fetch(`http://localhost:7000/expenses?eid=` + id, details);
//     const body = await httpResponse.json();

//     let date = "";
//     let listHtml = ``;

//     for (let expense of body) {
//         if (expense.statusDate == undefined) {
//             date = "";
//         } else {
//             date = expense.statusDate;
//         }
//         listHtml = listHtml + `<tr>
//         <td> ${expense.expenseId}</td>
//         <td style="width:70px; word-break:break-all;"> ${expense.amount}</td>
//         <td style="width:400px; word-break:break-all;"> ${expense.empReason}</td>
//         <td style="width:80px; word-break:break-all;"> ${expense.status}</td>
//         <td style="width:400px; word-break:break-all;"> ${expense.managerReason}</td>
//         <td style="width:110px; word-break:break-all;"> ${expense.submissionDate}</td>
//         <td style="width:110px; word-break:break-all;"> ${date}</td>
//         <td style="width:5px; word-break:break-all;"> ${expense.managerId}</td>
//         </tr>`;

//     }
//     return listHtml;
// }

async function createExpense() {
    $("#myModal").modal()
}


const handler = async function(event) {

        event.preventDefault();

        const form = event.currentTarget;

        const url = form.action;
        try {

            const formData = new FormData(form);
            const plainFormData = Object.fromEntries(formData.entries());
            const formDataJsonString = JSON.stringify(plainFormData);

            const fetchOptions = {
                method: "POST",
                body: formDataJsonString,
            };

            const response = await fetch(url, fetchOptions);

            if (response.ok) {
                const jwt = await response.text(); // the jwt
                localStorage.setItem('jwt', jwt);
                const token = localStorage.getItem('jwt');
                const decoded = JSON.parse(atob(token.split('.')[1]));
                if (decoded.role == "employee") {
                    window.location.href = "emloyee.html";
                } else if (decoded.role == "manager") {
                    window.location.href = "manager.html";
                }
            }

        } catch (error) {
            console.error(error);
        }
    }
    // document.getElementById("form").addEventListener('submit', handler);