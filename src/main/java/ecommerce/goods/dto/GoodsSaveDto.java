package ecommerce.goods.dto;

public record GoodsSaveDto(String name, Long price, String description, Integer quantity, String category) {
}
