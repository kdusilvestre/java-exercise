package io.vertx.example;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;


@Component
public class StorageService {
	// Constants for the filtering task
  private static final String CHARACTER_NAME_PARAMETER = "name";
  private static final String IS_DEAD_PARAMETER = "is_alive";
  private static final String KILL_COUNT_PARAMETER = "kill_count_range";
  @Autowired

  private DummySOR dummySOR;
  /*
    Returns array of characters according to given criteria.
   */
  public JsonArray getAll(Map<String, String> paramMap) {

	  JsonArray  jsonArray = new JsonArray();
	  Collection<JsonObject> jsonObjectColl = dummySOR.getCharacters();
	  JsonArray jsonArrFilters = new JsonArray();
	  
	  if(!paramMap.isEmpty()) {
	  System.out.println(paramMap.size());
	  jsonObjectColl.forEach(action -> {
		  boolean isConditionSat = false;
			 if( paramMap.get(CHARACTER_NAME_PARAMETER)!=null) {
				if(action.getString("actorName")!=null &&  action.getString("actorName").equals(paramMap.get(CHARACTER_NAME_PARAMETER))) {
					isConditionSat=true;
				}
			} if(paramMap.get(IS_DEAD_PARAMETER)!=null) {
					
				if(action.getJsonArray("killedBy") != null && action.getJsonArray("killedBy").isEmpty()) {
					isConditionSat=true;
				}
				 
				 
			 } if(paramMap.get(KILL_COUNT_PARAMETER)!=null) {
					String[] killCountRange = paramMap.get(KILL_COUNT_PARAMETER).split(",");
					int size = action.getJsonArray("Killed")!=null ? action.getJsonArray("Killed").size() : 0;
					if(killCountRange.length>=2 && size >= Integer.valueOf(killCountRange[0]) && size <=Integer.valueOf(killCountRange[1])) {
						isConditionSat=true;
					}
			 }
			 if(isConditionSat)
				 jsonArrFilters.add(action);
		  });
	     return jsonArrFilters;
	  }else {
		  jsonObjectColl.forEach(action -> jsonArray.add(action));
	  }
	  return jsonArray;
	  

  }

  /**
   * ADd the characters to database
   * @param character the JSONObject
   */
  public void add(JsonObject character) {
	  try {
    dummySOR.addCharacter(character);
	  }catch(IOException exception) {
		  exception.printStackTrace();
	  }
  }

}
