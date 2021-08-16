package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.security.domain.Admin;
import by.valvik.bannermanagement.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static by.valvik.bannermanagement.security.constant.SecurityConstant.AUTHORIZATION_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        
        this.authenticationManager = authenticationManager;

        this.jwtProvider = jwtProvider;
    }

    @PostMapping()
    public ResponseEntity<Void> authenticate(@RequestBody @Valid Admin admin) {

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(admin.getLogin(),
                                                                                     admin.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        String jwt = jwtProvider.generateJwt(authenticate);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(AUTHORIZATION, AUTHORIZATION_PREFIX + jwt);

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);

    }

}
