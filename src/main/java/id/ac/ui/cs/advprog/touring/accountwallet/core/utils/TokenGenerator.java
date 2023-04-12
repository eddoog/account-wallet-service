package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenGenerator implements AuthTool {
    private User user;
    private static final String SECRET_KEY = "645367566B59703373367639792F423F4528482B4D6251655468576D5A713474";

    public TokenGenerator(User user) {
        this.user = user;
    }

    @Override
    public String execute() {
        return Jwts
                .builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}