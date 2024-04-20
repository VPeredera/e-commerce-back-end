package ecommerce.security.service;

import ecommerce.security.dao.UserDao;
import ecommerce.security.dto.SignUpDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SingUpValidator {
    private static final Pattern USERNAME_REGEX = Pattern.compile("^(?=.{2,30}$)[A-Z]+(([' -][a-zA-Z ])?[a-zA-Z]*)*$");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^(?=.{1,32}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[{}:#@!%;\\[_'`\\],\"./~?*\\-$^+=\\\\<>]).{8,20}$");
    private final UserDao userDao;

    public SingUpValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public Map<String, String> validate(SignUpDto dto) {
        Map<String, String> errors = new HashMap<>();
        String username = dto.username();
        String email = dto.email();
        if (!USERNAME_REGEX.matcher(username).matches()) errors.put("username", "Username is not valid");
        if (!PASSWORD_REGEX.matcher(dto.password()).matches()) errors.put("password",  "Password is not valid");
        if (!EMAIL_REGEX.matcher(email).matches()) errors.put("email", "Email is not valid");
        if (userDao.existsUserByUsernameOrEmail(username, email)) errors.put("exists", "Username or email already registered");
        return errors;
    }
}
