package com.example.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LambdaRequestHandlerTest {

  @Test
  void test() {
    System.out.println("aaaa");

    Context ctx = new TestContext();

    LambdaRequestHandler handler = new LambdaRequestHandler();
    String output = handler.handleRequest("Test", ctx);
    Assertions.assertTrue(output.startsWith("Hello"));
  }

}
