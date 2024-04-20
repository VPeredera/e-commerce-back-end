package ecommerce.goods.dto;

import ecommerce.reviews.dto.ReviewDto;

import java.time.Instant;
import java.util.List;

public record GoodsDto(String name, Long price, String description, Integer quantity, String category, Instant createdAt, byte[] image, List<ReviewDto> reviews) {
}
