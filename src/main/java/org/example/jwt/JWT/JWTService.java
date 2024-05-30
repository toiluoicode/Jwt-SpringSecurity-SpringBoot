package org.example.jwt.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.jwt.Models.CustomeUserDetails;
import org.example.jwt.Models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    private final String JWT_SECRET = "9eedcc3da05a09c9e6451eed689f4041d82e475824da96694882c84b8fb99893";
    private final long JWT_EXPIRATION = 3600;


    public <T> T extractClaim(String token, Function<Claims,T> resolver) {
        Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }


    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String generateToken(User userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 24*3600*1000)) /// thoi gian token ton tai
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
