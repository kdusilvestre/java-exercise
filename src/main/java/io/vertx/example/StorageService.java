package io.vertx.example;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.Map;

public class StorageService {

  // Constants for the filtering task
  private static final String CHARACTER_NAME_PARAMETER = "name";
  private static final String IS_DEAD_PARAMETER = "is_alive";
  private static final String KILL_COUNT_PARAMETER = "kill_count_range";

  private DummySOR dummySOR;

  public StorageService() {
    this.dummySOR = new DummySOR();
  }

  /*
    Returns array of characters according to given criteria.
   */
  public JsonArray getAll(Map<String, String> paramMap) {
    throw new UnsupportedOperationException("TODO: Task #1");
  }

  /*
    Adds a new character to the database
   */
  public void add(JsonObject character) {
    this.dummySOR.addCharacter(character);
  }

}
