package ecommerce.security.service;

import ecommerce.security.dao.RefreshTokenDao;
import ecommerce.security.dao.UserDao;
import ecommerce.security.domain.RefreshToken;
import ecommerce.security.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${expiration.refresh-token}")
    private Long expirationTime;

    private RefreshTokenDao refreshTokenDao;
    private UserDao userDao;

    @Autowired
    public void setDao(RefreshTokenDao dao) {this.refreshTokenDao = dao;}

    @Autowired
    public void setDao(UserDao dao) {this.userDao = dao;}

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(getUser(email));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(expirationTime));
        System.out.println(refreshToken.getToken());
        return refreshTokenDao.save(refreshToken);
    }

    public RefreshToken verifyExpiration(String token) {
        RefreshToken refreshToken = findByToken(token);
        if(refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenDao.delete(refreshToken);
            throw new CredentialsExpiredException("Refresh token has expired.");
        }
        return refreshToken;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenDao.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Refresh token is invalid."));
    }

    private User getUser(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User wasn't found."));
    }
}
