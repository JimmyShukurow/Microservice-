package io.smartir.helper;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.*;
import io.smartir.exceptions.YourTokenExpiredException;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.IntPredicate;

public class HelperFunctions {

    @Value("${auth.salt}")
    private String AUTH_SALT;
    @Value("${jwt.secret}")
    private String secret;

    public String hashPassword(String rawPassword) {
        var spec = new PBEKeySpec(rawPassword.toCharArray(), AUTH_SALT.getBytes(), 65536, 512);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPasswordComplexity(String rawPassword) {


        return rawPassword.length() > 8 && // check password length
                containsNumber(rawPassword) && // check password has number or digit
                containsLowerCase(rawPassword) && // check password has lower case letter
                containsUpperCase(rawPassword); // check password has upper case letter
    }

    public boolean checkEmailComplexity(String email) {
        return email.contains("@");
    }


    public boolean containsLowerCase(String value) {
        return contains(value, ch -> Character.isLetter(ch) && Character.isLowerCase(ch));
    }

    public boolean containsUpperCase(String value) {
        return contains(value, ch -> Character.isLetter(ch) && Character.isUpperCase(ch));
    }

    public boolean containsNumber(String value) {
        return contains(value, Character::isDigit);
    }

    public boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }

    public String generateJWToken(String name, String email, String subject) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .claim("name", name)
                .claim("email", email)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(24l, ChronoUnit.HOURS)))
                .signWith(hmacKey)
                .compact();
    }

    public Jws<Claims> validateJWT(String jwtString) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(jwtString);
            return jwt;
    }

}
