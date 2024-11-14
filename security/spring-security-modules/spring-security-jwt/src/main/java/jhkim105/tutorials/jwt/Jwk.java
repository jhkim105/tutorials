package jhkim105.tutorials.jwt;


import static org.hibernate.Length.LONG32;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "jwk")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class Jwk {

    @Id
    @Column(length = 50)
    private String id;

    @Lob
    @Column(nullable = false, length = LONG32)
    private String keyData;

    @CreatedDate
    private Instant createdAt;

    public Jwk(String id, String keyData) {
        this.id = id;
        this.keyData = keyData;
    }

}
