package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceSttLogRepository extends JpaRepository<ConferenceSttLog, String> {
  ConferenceSttLog getByConferenceIdAndSeq(String conferenceId, Integer seq);
}
