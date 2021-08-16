package by.valvik.bannermanagement.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.valvik.bannermanagement.security.constant.SecurityConstant.AUTHORIZATION_PREFIX;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final int TO_MILLIS = 1000;

    private final UserDetailsService adminDetailsService;

    private final String secretKeyValue;

    private final Integer expirationInSeconds;

    private SecretKey secretKey;

    public JwtProvider(@Qualifier("adminDetailsService") UserDetailsService adminDetailsService,
                       @Value("${jwt.secret-key}") String secretKeyValue,
                       @Value("${jwt.expiration-in-seconds}") Integer expirationInSeconds) {

        this.adminDetailsService = adminDetailsService;

        this.secretKeyValue = secretKeyValue;

        this.expirationInSeconds = expirationInSeconds;

    }

    public String generateJwt(Authentication authentication) {

        Claims claims = Jwts.claims().setSubject(authentication.getName());

        List<String> authorities = authentication.getAuthorities().stream()
                                                                  .map(GrantedAuthority::getAuthority)
                                                                  .toList();

        claims.put(AUTHORITIES_KEY, authorities);

        Date now = new Date();

        Date expires = new Date(now.getTime() + expirationInSeconds * TO_MILLIS);

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(expires)
                   .signWith(secretKey, HS256)
                   .compact();
        
    }

    public Authentication getAuthentication(String jwt) {

        UserDetails userDetails = adminDetailsService.loadUserByUsername(getUsername(jwt));

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

    public Optional<String> resolveJwt(HttpServletRequest req) {

        String headerValue = req.getHeader(AUTHORIZATION);

        if (headerValue != null && headerValue.startsWith(AUTHORIZATION_PREFIX)) {

            return Optional.of(headerValue.substring(AUTHORIZATION_PREFIX.length()));

        }

        return Optional.empty();

    }

    public boolean isJwtValid(String jwt) {

        boolean isValid = true;

        try {

            getJws(jwt);

        } catch (JwtException | IllegalArgumentException  e) {

            isValid = false;

        }

        return isValid;
        
    }

    private String getUsername(String jwt) {

        return getJws(jwt).getBody().getSubject();

    }

    private Jws<Claims> getJws(String jwt) {

        return Jwts.parserBuilder()
                   .setSigningKey(secretKey)
                   .build()
                   .parseClaimsJws(jwt);

    }

    @PostConstruct
    private void init() {

        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyValue));

    }

}
