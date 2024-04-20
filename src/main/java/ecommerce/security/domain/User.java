package ecommerce.security.domain;

import ecommerce.commons.Identifier;
import ecommerce.reviews.domain.UserReview;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Identifier {
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
//    @Enumerated(EnumType.STRING)
//    private UserStatus userStatus;
    private boolean enabled;
    @CreationTimestamp
    private Instant createdAt;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserReview> reviews;
    @Enumerated(EnumType.STRING)
    private Permissions permissions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public UserStatus getUserStatus() {
//        return userStatus;
//    }
//
//    public void setUserStatus(UserStatus userStatus) {
//        this.userStatus = userStatus;
//    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<UserReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<UserReview> reviews) {
        this.reviews = reviews;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
}
