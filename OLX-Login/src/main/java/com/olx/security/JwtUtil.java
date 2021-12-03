package com.olx.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	/* This key can be anything */
	// Secret key plays vital role in creating the signature
    private String SECRET_KEY = "Zensar";

    
    // whatever token you receive from the client you just pass to this function
    // This returns you the username of the client easily.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // generateToken takes an argument of UserDetails object
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /* Here we are creating our own function */
    public String generateTokenByUsername(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // When you are creating the token you are specifying the expiry
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        		 // setExpiry function here we call and we set the expiry of 10 hours
        		.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        		// Signature says please use algorithm called HS256 and the secret key
        		// Your signature depends upon 2 factors algorithm and secert key
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public Boolean validateTokenByUsername(String token, String clientUsername) {
        final String username = extractUsername(token);
        return (username.equals(clientUsername) && !isTokenExpired(token));
    }
}