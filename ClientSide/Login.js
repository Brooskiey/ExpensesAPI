async function login(username, password) {

    const login = {"username": username, "password": password}

    const details = {
        method: 'POST',
        body: JSON.stringify(login)
    }

    const httpResponse = await fetch(`http://localhost:7000/users/login`, details);
    const jwt = await httpResponse.text(); // the jwt
    localStorage.setItem('jwt', jwt);

    var token = 'eyJ0eXAiOo876jgJ96...'; // jwt token;
    var decoded = jwt_decode(token);
    console.log(decoded);

    console.log(localStorage.getItem("jwt"));
}