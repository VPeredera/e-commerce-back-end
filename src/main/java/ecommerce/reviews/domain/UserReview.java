package ecommerce.reviews.domain;

import ecommerce.commons.Identifier;
import ecommerce.security.domain.User;
import ecommerce.goods.domain.Goods;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
public class UserReview extends Identifier {
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;
    @Enumerated
    private Rate rate;
    @UpdateTimestamp
    private Instant createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goods_id", nullable = false)
    private Goods goods;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
