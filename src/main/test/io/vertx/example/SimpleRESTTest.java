package io.vertx.example;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class SimpleRESTTest {
	
	@Autowired
    private TestRestTemplate restTemplate ;
     
    @LocalServerPort
    int randomServerPort;

    @Test
	void testGetCharacters() throws RestClientException, MalformedURLException, URISyntaxException{
		URI uri = new URI("http://localhost:"+randomServerPort+"/character");
		ResponseEntity<String> result=restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		
	}
	
	@Test
	void testPostCharacters() throws URISyntaxException {
		URI uri = new URI("http://localhost:"+randomServerPort+"/character");
		ResponseEntity<String> result=restTemplate.postForEntity(uri, 
				RandomCharacterGenerator.generateRandomCharacter(), String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
}
