package ecommerce.security.dto;

import org.springframework.http.ResponseCookie;

public record AuthResponse(String token, ResponseCookie authCookie) {
}
