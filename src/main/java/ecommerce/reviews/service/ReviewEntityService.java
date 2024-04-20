package ecommerce.reviews.service;

import ecommerce.commons.AbstractEntityService;
import ecommerce.commons.Mapper;
import ecommerce.goods.dao.GoodsDao;
import ecommerce.goods.domain.Goods;
import ecommerce.goods.dto.GoodsSaveDto;
import ecommerce.goods.service.GoodsEntityService;
import ecommerce.reviews.dao.UserReviewDao;
import ecommerce.reviews.domain.UserReview;
import ecommerce.reviews.dto.ReviewDto;
import ecommerce.reviews.dto.ReviewSaveDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewEntityService extends AbstractEntityService<UserReview, UserReviewDao> {

    private Mapper mapper;
    private GoodsDao goodsDao;

    @Autowired
    public void setMapper(Mapper mapper) {this.mapper = mapper;}

    @Autowired
    public void setGoodsDao(GoodsDao goodsDao) {this.goodsDao = goodsDao;}

    @Override
    @Autowired
    protected void setDao(UserReviewDao dao) {
        this.dao = dao;
    }
    //TODO fix user
    public void createReview(ReviewSaveDto dto) {
        validateDto(dto);
        save(mapper.convertFromDto(dto, null, getGoods(dto.goodsId())));
    }

    public ReviewDto getReviewById(Long id) {return mapper.convertToDto(getById(id));}

    public void updateReview(ReviewSaveDto dto, Long id) {
        validateDto(dto);
        update(mapper.convertFromDto(dto, null, getGoods(dto.goodsId())), id);
    }

    public void deleteReview(Long id) {
        delete(id);
    }

    private void validateDto(ReviewSaveDto dto) {
        if (dto.text().isBlank()) throw new IllegalArgumentException("Text is not valid.");
        if (dto.rate() < 0 || dto.rate() > 5) throw new IllegalArgumentException("Rate is not valid.");
    }

    private Goods getGoods(Long id) {
        return goodsDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Goods by id " + id + " wasn't found."));
    }
}
