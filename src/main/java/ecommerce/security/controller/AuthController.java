package ecommerce.security.controller;

import ecommerce.security.dto.AuthResponse;
import ecommerce.security.dto.SignInDto;
import ecommerce.security.dto.SignUpDto;
import ecommerce.security.service.SingUpValidator;
import ecommerce.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping("/ecommerce/auth")
public class AuthController {
    private final SingUpValidator validator;
    private final AuthService authService;

    @Autowired
    public AuthController(SingUpValidator validator, AuthService authService) {
        this.validator = validator;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto dto) {
        Map<String, String> errors = validator.validate(dto);
        if (!errors.isEmpty()) return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        authService.signup(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInDto dto) {
        AuthResponse authResponse = authService.login(dto);
        return ResponseEntity.ok().header(SET_COOKIE, authResponse.authCookie().toString()).body(authResponse.token());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }
}
