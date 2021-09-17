package jhkim105.tutorials.spring.data.multiple.datasource.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductRepository productRepository;

  @GetMapping
  public ResponseEntity<Page> findAll(Pageable pageable) {
    Page<Product> page = productRepository.findAll(pageable);
    return ResponseEntity.ok().body(page);
  }
}
