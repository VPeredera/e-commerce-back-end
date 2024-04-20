package ecommerce.security.service;

import ecommerce.security.dao.UserDao;
import ecommerce.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao dao;

    @Autowired
    public void setDao(UserDao dao) {this.dao = dao;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = dao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with that email wasn't found."));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getPermissions(user.getPermissions().toString())
        );
    }

    private Collection<? extends GrantedAuthority> getPermissions(String permission) {
        return Collections.singletonList(new SimpleGrantedAuthority(permission));
    }
}
