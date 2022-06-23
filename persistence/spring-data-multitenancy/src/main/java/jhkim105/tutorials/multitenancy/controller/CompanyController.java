package jhkim105.tutorials.multitenancy.controller;

import jhkim105.tutorials.multitenancy.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyRepository companyRepository;

  @GetMapping("/deleteByName")
  public ResponseEntity<Void> deleteByName(String name) {
    companyRepository.deleteByName(name);
    return ResponseEntity.noContent().build();
  }

}
