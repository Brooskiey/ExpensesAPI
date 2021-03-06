package dev.kiser.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.kiser.utils.JwtUtil;
import org.junit.jupiter.api.Test;

public class JwtTests {


    @Test
    void creates_jwt() {
        String jwt = JwtUtil.generate("employee", "Mary Sue");
        System.out.println(jwt);
    }

    @Test
    void creates_jwt_manager() {
        String jwt = JwtUtil.generate("manager", "Linda Wright");
        System.out.println(jwt);
    }

    @Test
    void decode_jwt() {
        DecodedJWT jwt = JwtUtil.isValidJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoibWFuYWdlciJ9.6Q9ZQKUFFTuOksBLcJ3APjdVtO4gEzcqSqbr9QSPnHU");
        String role = jwt.getClaim("role").asString();
        System.out.println(role);
    }

    @Test
    void edited_jwt() {
        DecodedJWT jwt = JwtUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTWVnYSBNYW5hZ2VyIGFuZCBvd25lciIsImVtcE5hbWUiOiJMaW5kYSBXcmlnaHQifQ.Zg9gHHVPVrwrurQFlMi4j8O97I_I9CRp59OwfH_G1fE");
        String role = jwt.getClaim("role").asString();
        System.out.println(role);
    }
}
