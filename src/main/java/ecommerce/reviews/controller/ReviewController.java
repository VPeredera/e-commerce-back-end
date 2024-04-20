package ecommerce.reviews.controller;

import ecommerce.reviews.dto.ReviewDto;
import ecommerce.reviews.dto.ReviewSaveDto;
import ecommerce.reviews.service.ReviewEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/review")
public class ReviewController {
    private final ReviewEntityService service;

    @Autowired
    public ReviewController(ReviewEntityService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestBody ReviewSaveDto dto) {
        service.createReview(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto getReviewById(@PathVariable Long id) {
        return service.getReviewById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateReview(@PathVariable("id") Long id, @RequestBody ReviewSaveDto dto) {
        service.updateReview(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
    }
}
