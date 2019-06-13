package io.vertx.example;


import io.vertx.core.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DummySOR {

  private static final String JSON_FILENAME = "characters.json";

  /*
    Fake DB implementation.
   */
  private FakeDB characters;

  public DummySOR() {
    loadData();
  }

  public void addCharacter(JsonObject character) {
    // TODO: Task #3
    // Note: the solution is NOT to change characters.insert to characters.put ;)
    addCharacterBlocking(character);
  }

  private void loadData() {
    try {
      String fileContents = String.join("", Files.readAllLines(getFileFromRes(JSON_FILENAME).toPath()));
      Map<String, JsonObject> temp = new JsonObject(fileContents)
          .getJsonArray("characters")
          .stream()
          .map(o -> (JsonObject)o)
          .collect(Collectors.toMap(j -> j.getString("characterName"), Function.identity(), (a, b) -> a, ConcurrentHashMap::new));
      characters = new FakeDB((ConcurrentHashMap<String, JsonObject>) temp);
    } catch (IOException e) {
      this.characters = new FakeDB();
    }
  }

  // The current implementation is a blocking operation.
  private void addCharacterBlocking(JsonObject character) {
    characters.insert(character.getString("characterName"), character);
  }

  private File getFileFromRes(String fileName) {
    URL resource = DummySOR.class.getClassLoader().getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("Ooops - " + fileName + " not found");
    } else {
      return new File(resource.getFile());
    }

  }
}
