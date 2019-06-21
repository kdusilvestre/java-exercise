package io.vertx.example;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.Json;
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
	 * Returns array of characters according to given criteria.
	 */
	public JsonArray getAll(Map<String, String> paramMap) {
		CharactersFilter filter = prepareFilters(paramMap);
		Map<String, JsonObject> charactersMap = dummySOR.getCharacters(filter);
		JsonArray result = new JsonArray();
		charactersMap.values().forEach(result::add);
		return result;
	}

	private CharactersFilter prepareFilters(Map<String, String> paramMap) {
		CharactersFilter filter = new CharactersFilter();

		getFilterName(paramMap, filter);
		getFilterIsAlive(paramMap, filter);
		getFilterKill(paramMap, filter);
		return filter;
	}

	/*
	 * set filter if kill parameter is set
	 */
	private void getFilterKill(Map<String, String> paramMap, CharactersFilter filter) {
		if (paramMap.containsKey(KILL_COUNT_PARAMETER)) {
			try {
				JsonArray data = (JsonArray) Json.decodeValue(paramMap.get(KILL_COUNT_PARAMETER));
				filter.kill_max = Integer.parseInt(data.getValue(1).toString());
				filter.kill_min = Integer.parseInt(data.getValue(0).toString());
			} catch (Exception e) {
				filter.kill_max = null;
				filter.kill_min = null;
			}
		}
	}

	/*
	 * set filter if alive parameter is set
	 */
	private void getFilterIsAlive(Map<String, String> paramMap, CharactersFilter filter) {
		if (paramMap.containsKey(IS_DEAD_PARAMETER)) {
			try {
				filter.is_alive = Boolean.parseBoolean(paramMap.get(IS_DEAD_PARAMETER));
			} catch(Exception e) {
				filter.is_alive = null;
			}
		}
	}

	/*
	 * set filter if name parameter is set
	 */
	private void getFilterName(Map<String, String> paramMap, CharactersFilter filter) {
		if (paramMap.containsKey(CHARACTER_NAME_PARAMETER)) {
			filter.name = paramMap.get(CHARACTER_NAME_PARAMETER);
		}
	}

	/*
	 * Adds a new character to the database
	 */
	public void add(JsonObject character) {
		this.dummySOR.addCharacter(character);
	}

}
