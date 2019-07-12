package io.vertx.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
;
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
  		List<Boolean> isConditionSat = new ArrayList<>();
  		Collection<JsonObject> jsonObjectColl = dummySOR.getCharacters();
  		JsonArray jsonArrFilters = new JsonArray();
		if(!paramMap.isEmpty()) {
			jsonObjectColl.forEach(action -> {
				isConditionSat.clear();
				action.stream().forEach(jsonObject -> {
					if( paramMap.get(CHARACTER_NAME_PARAMETER)!=null) 
							
							if(jsonObject != null && 
							jsonObject.getKey().toString().equals("actorName")  &&  
							jsonObject.getValue().toString().equals(paramMap.get(CHARACTER_NAME_PARAMETER))) {
								isConditionSat.add(true);
								
					} 
					
					if(paramMap.get(IS_DEAD_PARAMETER)!=null && jsonObject != null && 
							jsonObject.getKey().toString().equals("killedBy") &&
							!(jsonObject.getValue() instanceof JsonArray)) {
								isConditionSat.add(true);
						
					}if(paramMap.get(KILL_COUNT_PARAMETER)!=null && jsonObject != null && 
							jsonObject.getKey().toString().equals("killed")) {
						
						String[] killCountRange = paramMap.get(KILL_COUNT_PARAMETER).split(",");
						int killCountSize = killCountRange.length;
						if(killCountSize >= Integer.parseInt("2")) {
							int size = jsonObject.getValue().toString().split(",").length;
								if(size >= Integer.valueOf(killCountRange[0])  && size <=Integer.valueOf(killCountRange[1])) {
									isConditionSat.add(true);
								}
							}
					}
					if(isConditionSat.size() == paramMap.size()) {
						jsonArrFilters.add(action);
						
					}
					
				});
				
					
				
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
