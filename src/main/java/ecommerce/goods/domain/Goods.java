package ecommerce.goods.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecommerce.commons.Identifier;
import ecommerce.reviews.domain.UserReview;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;
import java.util.List;
//TODO add User field
@Entity
public class Goods extends Identifier {
    @Column(unique = true)
    private String name;
    private Long price;
    private String description;
    private Integer quantity;
    private Long rate;
    @Enumerated(EnumType.STRING)
    private Categories category;
    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserReview> userReview;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public List<UserReview> getUserReview() {
        return userReview;
    }

    public void setUserReview(List<UserReview> userReview) {
        this.userReview = userReview;
    }
}
