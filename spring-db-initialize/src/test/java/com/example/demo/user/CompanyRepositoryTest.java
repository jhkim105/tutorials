package com.example.demo.user;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest {


  @Autowired
  CompanyRepository companyRepository;

  @Test
  void findAll() {
    List<Company> list = companyRepository.findAll();
    Assertions.assertThat(list).hasSize(1);
  }
}
