package ecommerce.reviews.dto;

import java.time.Instant;

public record ReviewDto(String email, Long goodsId, Instant createdAt, String text, Integer rate) {
}
