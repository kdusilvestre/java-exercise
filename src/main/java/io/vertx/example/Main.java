package io.vertx.example;

import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args) {
	  
    Vertx.vertx().deployVerticle(SimpleREST.class.getName());
    System.out.println("We're good to go! Listening on localhost:8080");
  }

}
