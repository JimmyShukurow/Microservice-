package io.smartir;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class HelperFunctions {

    @Value("${auth.salt}")
    private String AUTH_SALT;

    public String hashPassword(String rawPassword) {
        var spec = new PBEKeySpec(rawPassword.toCharArray(), AUTH_SALT.getBytes(), 65536, 128);
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

        return rawPassword.length() > 10 && rawPassword.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)\n");
    }

    public String generateToken() {

        return "token";
    }
}
