package jhkim105.tutorials.spring.cloud.microservices.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String author;

  @Builder
  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }
}
