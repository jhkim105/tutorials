package jhkim105.tutorials.spring.cloud.microservices.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;


  public List<Book> findAll() {
    return bookRepository.findAll();
  }

}
