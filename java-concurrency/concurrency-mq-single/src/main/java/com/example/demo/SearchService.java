package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {

  private final SearchLogRepository searchLogRepository;
  private final SearchStatsRepository searchStatsRepository;

  @Transactional
  public SearchStats search(String keyword) {
    saveSearcLog(keyword);
    SearchStats searchStats = saveSearchStats(keyword);
    return searchStats;
  }

  private SearchLog saveSearcLog(String keyword) {
    SearchLog searchLog = SearchLog.builder().keyword(keyword).build();
    return searchLogRepository.save(searchLog);
  }


  private SearchStats saveSearchStats(String keyword) {
    SearchStats searchStats;
    Optional<SearchStats> optionalSearchStats = searchStatsRepository.findById(keyword);
    if (optionalSearchStats.isPresent()) {
      searchStats = optionalSearchStats.get();
    } else {
      searchStats = SearchStats.builder().id(keyword).build();
    }
    searchStats.addCount();
    return searchStatsRepository.save(searchStats);
  }
}
