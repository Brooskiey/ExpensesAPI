package dev.kiser.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.kiser.entities.Employee;

public class JwtUtil {

    private static final String secret = "SarahWinchester";
    private static final Algorithm algorithm = Algorithm.HMAC256(secret);

    public static String generate(String role, Employee employee) {

        String token = JWT.create()
                .withClaim("role", role) // add data to the payload
                .withClaim("empName", employee.getfName() + " " + employee.getlName())
                .withClaim("id", employee.getEmpId())
                .sign(algorithm); // this will generate a signture based off of those claims

        return token;
    }

    public static DecodedJWT isValidJWT(String token) {
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt;
    }
}
