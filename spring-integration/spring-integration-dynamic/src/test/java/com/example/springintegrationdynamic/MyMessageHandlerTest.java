package com.example.springintegrationdynamic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyMessageHandlerTest {

  @Autowired
  MyMessageHandler myMessageHandler;

  @Test
  @Disabled
  void writeResult() {
    myMessageHandler.writeResult("test.txt", new MessageDto("test01"));
    myMessageHandler.writeResult("test.txt", new MessageDto("test01"));
    myMessageHandler.writeResult("test.txt", new MessageDto("test01"));
    myMessageHandler.writeResult("test.txt", new MessageDto("test01"));
    myMessageHandler.writeResult("test.txt", new MessageDto("test01"));
  }
}