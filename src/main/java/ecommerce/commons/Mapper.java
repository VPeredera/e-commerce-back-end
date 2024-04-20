package ecommerce.commons;

import ecommerce.goods.domain.Categories;
import ecommerce.goods.domain.Goods;
import ecommerce.goods.dto.GoodsDto;
import ecommerce.goods.dto.GoodsSaveDto;
import ecommerce.reviews.domain.Rate;
import ecommerce.reviews.domain.UserReview;
import ecommerce.reviews.dto.ReviewDto;
import ecommerce.reviews.dto.ReviewSaveDto;
import ecommerce.security.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public GoodsDto convertToDto(Goods goods) {
        return new GoodsDto(
                goods.getName(),
                goods.getPrice(),
                goods.getDescription(),
                goods.getQuantity(),
                goods.getCategory().toString(),
                goods.getCreatedAt(),
                null,
                convertToListDto(goods.getUserReview())
        );
    }

    public Goods convertFromDto(GoodsSaveDto dto) {
        Goods goods = new Goods();
        goods.setName(dto.name());
        goods.setPrice(dto.price());
        goods.setDescription(dto.description());
        goods.setQuantity(dto.quantity());
        goods.setCategory(Categories.valueOf(dto.category()));
        goods.setRate(0L);
        return goods;
    }
//TODO fix user
    public ReviewDto convertToDto(UserReview review) {
        return new ReviewDto(
//                review.getUser().getEmail(),
                "mock@gmail.com",
                review.getGoods().getId(),
                review.getCreatedAt(),
                review.getText(),
                review.getRate().getRate()
        );
    }

    public UserReview convertFromDto(ReviewSaveDto dto, User user, Goods goods) {
        UserReview review = new UserReview();
        review.setText(dto.text());
        review.setRate(Rate.valueOf(dto.rate()));
        review.setUser(user);
        review.setGoods(goods);
        return review;
    }

    public List<ReviewDto> convertToListDto(List<UserReview> list) {
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
