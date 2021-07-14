package com.example.demo;

import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadService {


  private final UserService userService;

  @Async
  public void load(int no) {
    IntStream.range(0, no).parallel().forEach( i -> {
      userService.findAll();
    });
  }
}
