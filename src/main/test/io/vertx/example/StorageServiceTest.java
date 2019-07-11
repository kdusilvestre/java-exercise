package io.vertx.example;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.mockito.Mockito;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.DummySOR;
import io.vertx.example.FakeDB;
import io.vertx.example.RandomCharacterGeneratorTest;
import io.vertx.example.StorageService;

import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@SpringBootTest

@RunWith(MockitoJUnitRunner.class)
public class StorageServiceTest {
	JsonObject jsonObject ;

	RandomCharacterGeneratorTest randomCharacterGenerator;
	@Autowired
	private StorageService storageService ;
	@InjectMocks
	private DummySOR dummySOR ;
	
	
	@BeforeEach
	public void init() {
		randomCharacterGenerator = new RandomCharacterGeneratorTest();
		jsonObject =randomCharacterGenerator.generateRandomCharacter();
		
		//DummySOR dummySOR = Mockito.mock(DummySOR.class);
		Map<String, JsonObject> temp = new ConcurrentHashMap<String, JsonObject>();
		temp.put(jsonObject.getString("characterName"), jsonObject);
		
		
	}
	
	@Test
	void getAllTest() {
		Map<String,String> hashMap = new HashMap();
		
		
		FakeDB fakeDB = Mockito.mock(FakeDB.class);
		Map<String, JsonObject> temp = new ConcurrentHashMap<String, JsonObject>();
		temp.put(jsonObject.getString("characterName"), jsonObject);
		
		when(fakeDB.values()).thenReturn(temp.values());
		//when(dummySOR.getCharacters()).thenReturn(temp.values());
		
		
		

		assertNotNull(storageService.getAll(hashMap));
		
	}
	@Test
	void getNameFilterTest() {
		Map<String,String> hashMap = new HashMap();
		hashMap.put("name", "Marc Rissmann");
		
		FakeDB fakeDB = Mockito.mock(FakeDB.class);
		Map<String, JsonObject> temp = new ConcurrentHashMap<String, JsonObject>();
		temp.put(jsonObject.getString("characterName"), jsonObject);
		
		when(fakeDB.values()).thenReturn(temp.values());
		//when(dummySOR.getCharacters()).thenReturn(temp.values());
		
		
		//System.out.println(storageService.getAll(hashMap));

		assertNotNull(storageService.getAll(hashMap));
		
	}
	@Test
	void getAliveFilterTest() {
		Map<String,String> hashMap = new HashMap();
		hashMap.put("is_alive", "true");
		
		FakeDB fakeDB = Mockito.mock(FakeDB.class);
		Map<String, JsonObject> temp = new ConcurrentHashMap<String, JsonObject>();
		temp.put(jsonObject.getString("characterName"), jsonObject);
		
		when(fakeDB.values()).thenReturn(temp.values());
		//when(dummySOR.getCharacters()).thenReturn(temp.values());
		
		
		//System.out.println(storageService.getAll(hashMap));

		assertNotNull(storageService.getAll(hashMap));
		
	}
	
	@Test
	void getKillCountFilterTest() {
		Map<String,String> hashMap = new HashMap();
		hashMap.put("kill_count_range", "2,3");
		
		
		FakeDB fakeDB = Mockito.mock(FakeDB.class);
		Map<String, JsonObject> temp = new ConcurrentHashMap<String, JsonObject>();
		temp.put(jsonObject.getString("characterName"), jsonObject);
		
		when(fakeDB.values()).thenReturn(temp.values());
		//when(dummySOR.getCharacters()).thenReturn(temp.values());
		
		
		//System.out.println(storageService.getAll(hashMap));

		assertNotNull(storageService.getAll(hashMap));
		
	}
}
