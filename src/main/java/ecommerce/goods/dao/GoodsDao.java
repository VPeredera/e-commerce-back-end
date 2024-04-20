package ecommerce.goods.dao;

import ecommerce.goods.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsDao extends JpaRepository<Goods, Long> {
    Optional<Goods> findGoodsByName(String name);
}
