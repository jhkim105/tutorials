package com.example.sprinigconcurrent.jms;

import com.example.sprinigconcurrent.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JmsReceiver {

  @Autowired
  private SearchService searchService;

  @JmsListener(destination = JmsQueues.SEARCH + "-1", concurrency = "1")
  public void search1(String keyword) {
    log.debug("q1-keyword:{}", keyword);
    search(keyword);
  }

  @JmsListener(destination = JmsQueues.SEARCH + "-2", concurrency = "1")
  public void search2(String keyword) {
    log.debug("q2-keyword:{}", keyword);
    search(keyword);

  }

  @JmsListener(destination = JmsQueues.SEARCH + "-3", concurrency = "1")
  public void search3(String keyword) {
    log.debug("q3-keyword:{}", keyword);
    search(keyword);
  }


  private void search(String keyword) {
    searchService.search(keyword);
  }

}
