package io.vertx.example;

import java.util.Map.Entry;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class CharacterService {
	
	private DummySOR dummySOR;
	private JsonArray jsonArray;
	
	public CharacterService()
	{
		dummySOR = new DummySOR();
		jsonArray = new JsonArray();
		
	}
	
	public JsonArray getCharacters(String nameVal,String aliveVal,String killCountVal )
	{
		for(Entry<String, JsonObject> map : dummySOR.getCharacters().entrySet()){
			  if(map.getValue().getValue(Constants.CHARACTER_NAME_PARAMETER).equals(nameVal) && map.getValue().getValue(Constants.IS_DEAD_PARAMETER).equals(nameVal) && map.getValue().getValue(Constants.IS_DEAD_PARAMETER).equals(nameVal))
			  {
				  jsonArray.add(map.getValue());
			  }
		  }  
		
		return jsonArray;
		
	}
	
	public JsonArray getCharacters(String filterVal1,String filterval2,String filterkey1, String filterkey2 )
	{
		for(Entry<String, JsonObject> map : dummySOR.getCharacters().entrySet()){
			  if(map.getValue().getValue(filterkey1).equals(filterVal1) && map.getValue().getValue(filterkey2).equals(filterval2) )
			  {
				  jsonArray.add(map.getValue());
			  }
		  }  
		
		return jsonArray;
	}
	
	public JsonArray getCharacters(String filterVal, String filterkey )
	{
		for(Entry<String, JsonObject> map : dummySOR.getCharacters().entrySet()){
			  if(map.getValue().getValue(filterkey).equals(filterVal) )
			  {
				  jsonArray.add(map.getValue());
			  }
		  }  
		
		return jsonArray;
		
	}

	public JsonArray getCharacters() {
	
		JsonArray ja = new JsonArray();
		for(Entry<String, JsonObject> map : dummySOR.getCharacters().entrySet()){
			jsonArray.add(map.getValue());
		  }  
		
		return jsonArray;
		
	}
	

}
