package io.vertx.example;

import io.vertx.core.json.JsonObject;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class FakeDB extends ConcurrentHashMap<String, JsonObject> {

  // You're not allowed to modify this class in this exercise

  private static int MOCK_DELAY_MS = 10;

  public FakeDB() {
    super();
  }

  public void insert(String key, JsonObject value) {
    try {
      Thread.sleep(MOCK_DELAY_MS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(key);
    this.put(key, value);
  }

  public FakeDB(ConcurrentHashMap<String, JsonObject> contents) {
    this.putAll(contents);
  }
}
