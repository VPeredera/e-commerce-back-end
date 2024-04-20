package ecommerce.reviews.dto;

public record ReviewSaveDto(String email, Long goodsId, String text, Integer rate) {
}
