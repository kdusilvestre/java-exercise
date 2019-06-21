package io.vertx.example;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DummySOR {

	private static final String JSON_FILENAME = "characters.json";
	ExecutorService executor = Executors.newFixedThreadPool(10);

	/*
	 * Fake DB implementation.
	 */
	private FakeDB characters;

	public DummySOR() {
		loadData();
	}

	public void addCharacter(JsonObject character) {
		// TODO: Task #3
		// Note: the solution is NOT to change characters.insert to characters.put ;)
		// slow version
//		addCharacterBlocking(character);
		
		
		//fast version
		addCharacterParalel(character);
	}

	private void addCharacterParalel(JsonObject character) {
		Runnable  runnable = new Runnable () {
			public void run() {
				addCharacterBlocking(character);
			}
		};
		
		executor.execute(runnable);
	}

	public Map<String, JsonObject> getCharacters(CharactersFilter filter) {
		Stream<Map.Entry<String, JsonObject>> stream = characters.entrySet().stream();

		stream = filterByName(filter, stream);
		stream = filterByIsAlive(filter, stream);
		stream = filterByKills(filter, stream);

		return stream.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}

	private Stream<Map.Entry<String, JsonObject>> filterByName(CharactersFilter filter,
			Stream<Map.Entry<String, JsonObject>> stream) {
		if (filter.name != null) {
			stream = stream.filter(c -> c.getKey().contains(filter.name));
		}
		return stream;
	}

	private Stream<Map.Entry<String, JsonObject>> filterByIsAlive(CharactersFilter filter,
			Stream<Map.Entry<String, JsonObject>> stream) {
		if (filter.is_alive != null) {
			/*
			 * keep if both are true, or both are false: is alive:
			 * c.getValue().getJsonArray("killedBy") == null looking for alive:
			 * filter.is_alive
			 */
			stream = stream.filter(c -> !(c.getValue().getJsonArray("killedBy") == null ^ filter.is_alive));
		}
		return stream;
	}

	private Stream<Map.Entry<String, JsonObject>> filterByKills(CharactersFilter filter,
			Stream<Map.Entry<String, JsonObject>> stream) {
		if (filter.kill_max != null && filter.kill_min != null) {
			stream = stream.filter(c -> {
				JsonArray killedArray = c.getValue().getJsonArray("killed");
				int killed = killedArray != null ? killedArray.size() : 0;

				return killed >= filter.kill_min && filter.kill_max >= killed;
			});
		}
		return stream;
	}

	private void loadData() {
		try {
			String fileContents = String.join("", Files.readAllLines(getFileFromRes(JSON_FILENAME).toPath()));
			Map<String, JsonObject> temp = new JsonObject(fileContents).getJsonArray("characters").stream()
					.map(o -> (JsonObject) o).collect(Collectors.toMap(j -> j.getString("characterName"),
							Function.identity(), (a, b) -> a, ConcurrentHashMap::new));
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
