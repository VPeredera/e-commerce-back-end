package ecommerce.security.service;

import ecommerce.commons.AbstractEntityService;
import ecommerce.security.dao.UserDao;
import ecommerce.security.domain.Permissions;
import ecommerce.security.domain.RefreshToken;
import ecommerce.security.domain.User;
import ecommerce.security.dto.AuthResponse;
import ecommerce.security.dto.SignInDto;
import ecommerce.security.dto.SignUpDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class AuthService extends AbstractEntityService<User, UserDao> {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final RefreshTokenService refTokenService;

    @Autowired
    @Override
    protected void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Autowired
    public AuthService(@Qualifier("psEncoder") PasswordEncoder passwordEncoder, AuthenticationManager authManager,
                       JwtService jwtService, RefreshTokenService refTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.refTokenService = refTokenService;
    }

    public void signup(SignUpDto dto) {
        save(create(dto.username(), dto.email(), passwordEncoder.encode(dto.password())));
    }

    public AuthResponse login(SignInDto dto) {
        Authentication authenticate = getAuthentication(dto);
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();
        User userByEmail = getByEmail(dto.email());
        String token = jwtService.generateToken(principal.getUsername(), userByEmail.getUsername(), principal.getAuthorities().toString());
        return new AuthResponse(token, ResponseCookie.from("user_info", refTokenService.createRefreshToken(userByEmail.getEmail()).getToken())
                .httpOnly(true).secure(true).maxAge(7 * 24 * 60 * 60).path("/").build());
    }

    public String refreshToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "user_info");
        if (cookie != null && !cookie.getValue().isEmpty()) {
            RefreshToken refreshToken = refTokenService.verifyExpiration(cookie.getValue());
            User user = getByEmail(refreshToken.getUser().getEmail());
            return jwtService.generateToken(user.getEmail(), user.getUsername(), user.getPermissions().toString());
        }
        return null;
    }

    private static User create(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
//        user.setUserStatus(UserStatus.CREATED);
        user.setPermissions(Permissions.ADMIN);
        user.setEnabled(true);
        return user;
    }

    private Authentication getAuthentication(SignInDto loginRequest) {
        Authentication authenticate;
        try {
            authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new InsufficientAuthenticationException("Credentials are not valid.");
        }
        return authenticate;
    }

    private User getByEmail(String email) {
        return dao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with that email wasn't found."));
    }
}
