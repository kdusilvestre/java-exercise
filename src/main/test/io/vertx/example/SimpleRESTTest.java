package io.vertx.example;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.example.RandomCharacterGeneratorTest;
import io.vertx.example.SimpleREST;
import io.vertx.example.StorageService;

@ExtendWith(SpringExtension.class)

public class SimpleRESTTest {
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	RandomCharacterGeneratorTest randomCharacterGenerator;
	
	JsonObject jsonObject ;
	
	@BeforeEach
	public void init() {
		jsonObject = randomCharacterGenerator.generateRandomCharacter();
	}
    
	
	@Test
	public void testGetCharacters() throws RestClientException, MalformedURLException{
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("names", null);
		StorageService storageService = Mockito.mock(StorageService.class);
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(jsonObject);
		Mockito.when(storageService.getAll(hashMap)).thenReturn(jsonArray);
		SimpleREST simpleRest = new SimpleREST();
		
	}
	
}
