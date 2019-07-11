package io.vertx.example;

import io.vertx.core.json.JsonObject;
import java.util.concurrent.ConcurrentHashMap;
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
    this.put(key, value);
  }

  public FakeDB(ConcurrentHashMap<String, JsonObject> contents) {
    this.putAll(contents);
  }
}
