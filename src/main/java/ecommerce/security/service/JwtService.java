package ecommerce.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {
    @Value("${expiration.jwt}")
    private Long expirationTime;

    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Jwts.SIG.HS512.key().build().toString().getBytes());

    public void validateToken(String jwt) {
        Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwt);
    }

    public String generateToken(String email, String username, String authorities) {
        return Jwts
                .builder()
                .subject(email)
                .signWith(SECRET_KEY)
                .issuer(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expirationTime)))
                .claim("role", authorities)
                .compact();
    }

    public String extractEmail(String jwt) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwt).getPayload().getSubject();
    }
}
