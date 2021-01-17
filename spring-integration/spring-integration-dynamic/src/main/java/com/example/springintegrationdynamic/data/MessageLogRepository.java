package com.example.springintegrationdynamic.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog, String> {

}
