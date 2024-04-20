package ecommerce.reviews.dao;

import ecommerce.reviews.domain.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewDao extends JpaRepository<UserReview, Long> {
}
