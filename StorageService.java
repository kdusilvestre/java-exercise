package io.vertx.example;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.Map;
import java.util.Map.Entry;

public class StorageService {

  // Constants for the filtering task


  private DummySOR dummySOR;
  
  private CharacterService characterService;

  public StorageService() {
    this.dummySOR = new DummySOR();
    characterService = new CharacterService();
  }

  /*
    Returns array of characters according to given criteria.
   */
  public JsonArray getAll(Map<String, String> paramMap) {
	  
	  String nameVal= paramMap.get(Constants.CHARACTER_NAME_PARAMETER);
	  String aliveVal=paramMap.get(Constants.IS_DEAD_PARAMETER);
	  String killCountVal=paramMap.get(Constants.KILL_COUNT_PARAMETER);
	  
	  System.out.println(dummySOR.getCharacters());
	  
	  
	  if(nameVal != null && aliveVal != null && killCountVal != null)
	  {
		  return characterService.getCharacters(nameVal, aliveVal, killCountVal);
	  }
	  
	  else if(nameVal != null && aliveVal != null)
	  {
	  return characterService.getCharacters(nameVal,aliveVal , Constants.CHARACTER_NAME_PARAMETER, Constants.IS_DEAD_PARAMETER);
	  
	  }
	  
	  else if(aliveVal != null && killCountVal != null) {
		  return characterService.getCharacters(aliveVal ,killCountVal, Constants.IS_DEAD_PARAMETER,Constants.KILL_COUNT_PARAMETER);
		  
	  }
	  
	  else if(nameVal != null && killCountVal != null) {
		  return characterService.getCharacters(nameVal ,killCountVal, Constants.CHARACTER_NAME_PARAMETER,Constants.KILL_COUNT_PARAMETER);
		  
		  
	  }
	  
	  else if(nameVal != null) 
	  {
		  return characterService.getCharacters(nameVal , Constants.CHARACTER_NAME_PARAMETER);
	  }
	  
	  else if(aliveVal != null) 
	  {
		  return characterService.getCharacters(aliveVal , Constants.IS_DEAD_PARAMETER);
	  }
	  
	  else if(killCountVal != null) 
	  {
		  return characterService.getCharacters(killCountVal , Constants.KILL_COUNT_PARAMETER);
	  }
	  
	  else {
		  return characterService.getCharacters();
	  }
	  
	  

    
  }

  /*
    Adds a new character to the database
   */
  public void add(JsonObject character) {
    this.dummySOR.addCharacter(character);
  }

}
