package ecommerce.goods.service;

import ecommerce.commons.AbstractEntityService;
import ecommerce.commons.Mapper;
import ecommerce.goods.dao.GoodsDao;
import ecommerce.goods.domain.Goods;
import ecommerce.goods.dto.GoodsDto;
import ecommerce.goods.dto.GoodsSaveDto;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GoodsEntityService extends AbstractEntityService<Goods, GoodsDao> {
    private Mapper mapper;

    @Autowired
    public void setMapper(Mapper mapper) {this.mapper = mapper;}

    @Override
    @Autowired
    protected void setDao(GoodsDao dao) {
        this.dao = dao;
    }

    public void createGoods(GoodsSaveDto dto) {
        validateDto(dto);
        save(mapper.convertFromDto(dto));
    }

    public GoodsDto getGoodsById(Long id) {
        return mapper.convertToDto(getById(id));
    }

    public Page<GoodsDto> getAllGoods(int page) {return getAll(page, 10).map(mapper::convertToDto);}

    public void updateGoods(GoodsSaveDto dto, Long id) {
        validateDto(dto);
        if (!getById(id).getName().contentEquals(dto.name())) existenceCheck(dto.name());
        update(mapper.convertFromDto(dto), id);
    }

    public void deleteGoods(Long id) {
        delete(id);
    }

    private void validateDto(GoodsSaveDto dto) {
        if (dto.name().isBlank()) throw new IllegalArgumentException("Name is not valid.");
        if (dto.price() <= 0L) throw new IllegalArgumentException("Price is not valid.");
        if (dto.description().isBlank()) throw new IllegalArgumentException("Description is not valid.");
        if (dto.quantity() < 0) throw new IllegalArgumentException("Quantity is not valid.");
        if (dto.category().isBlank()) throw new IllegalArgumentException("Category is not valid.");
    }

    private void existenceCheck(String name) {
        Optional<Goods> byName = dao.findGoodsByName(name);
        if (byName.isPresent()) throw new EntityExistsException("Goods with that name already exists.");
    }
}
