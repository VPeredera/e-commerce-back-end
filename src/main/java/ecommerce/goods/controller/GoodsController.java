package ecommerce.goods.controller;

import ecommerce.goods.dto.GoodsDto;
import ecommerce.goods.dto.GoodsSaveDto;
import ecommerce.goods.service.GoodsEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/goods")
public class GoodsController {
    private final GoodsEntityService service;

    @Autowired
    public GoodsController(GoodsEntityService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGoods(@RequestBody GoodsSaveDto dto) {
        service.createGoods(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GoodsDto getGoodsById(@PathVariable Long id) {
        return service.getGoodsById(id);
    }

    @GetMapping(params = "page")
    @ResponseStatus(HttpStatus.OK)
    public Page<GoodsDto> getAllGoods(@RequestParam int page) {
        return service.getAllGoods(page);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGoods(@PathVariable("id") Long id, @RequestBody GoodsSaveDto dto) {
        service.updateGoods(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGoods(@PathVariable Long id) {
        service.deleteGoods(id);
    }
}
