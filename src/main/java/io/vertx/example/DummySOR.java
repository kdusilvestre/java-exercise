package io.vertx.example;
import io.vertx.core.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
/**
 * DummySOR is being converted to a spring component 
 * @author poornadevapathni
 *
 */
@Component
public class DummySOR {

  private static final String JSON_FILENAME = "characters.json";
  private static final File file = new File("/Users/poornadevapathni/Documents/PerformanceTestResults.txt");
  private static int taskCounter =0;
  /*
    Fake DB implementation.
   */
  private FakeDB characters;
  
  public DummySOR() {
	  characters = new FakeDB();
	  //Making sure to have hundred capacity elements to store.--Poorna
	  characters.newKeySet(10);
  }

  public void addCharacter(JsonObject character) throws IOException {
	// TODO: Task #3
    // Note: the solution is NOT to change characters.insert to characters.put ;)
	Date dt = new Date();
    Long lg = dt.getTime();
    addCharacterBlocking(character);
    Date dt1 = new Date();
    Long lg1 =	dt1.getTime();
    taskCounter++;
    Files.write(file.toPath(),("Task"+ taskCounter+" added capacity--->"+ String.valueOf(lg1-lg)+" seconds \n").getBytes(), APPEND, CREATE);
  }
  
  public Collection<JsonObject> getCharacters() {
	  loadData();
	  return characters.values();
  }

  private void loadData() {
    try {
      String fileContents = String.join("", Files.readAllLines(getFileFromRes(JSON_FILENAME).toPath()));
      Map<String, JsonObject> temp = new JsonObject(fileContents)
          .getJsonArray("characters")
          .stream()
          .map(o -> (JsonObject)o)
          .collect(Collectors.toMap(j -> j.getString("characterName"), Function.identity(), (a, b) -> a, ConcurrentHashMap::new));
      characters.putAll(temp);
    } catch (IOException e) {
    	this.characters = new FakeDB();
        e.printStackTrace();
    }
  }

  // The current implementation is a blocking operation.
  private void addCharacterBlocking(JsonObject character) {
	  characters.insert(character.getString("characterName"),character );
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
