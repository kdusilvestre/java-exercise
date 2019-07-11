package io.vertx.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.vertx.core.json.JsonObject;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DummySORTest {
	JsonObject jsonObject ;

	@Autowired
	DummySOR dummySOR;
	
	@BeforeEach
	void init() {
		jsonObject =RandomCharacterGenerator.generateRandomCharacter();
		
	}	
	
	@Test
	void testgetCharacters() {
		FakeDB characters= Mockito.mock(FakeDB.class);
		Map<String, JsonObject> temp = new ConcurrentHashMap<String, JsonObject>();
		temp.put(jsonObject.getString("characterName"), jsonObject);
		when(characters.values()).thenReturn(temp.values());

		assertNotNull(dummySOR.getCharacters());
	}
}
