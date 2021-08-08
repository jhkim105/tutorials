package com.example.demo;

import javax.persistence.EntityListeners;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Configuration
@EntityListeners(AuditingEntityListener.class)
public class JpaConfig {

}
