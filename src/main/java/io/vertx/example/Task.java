package io.vertx.example;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.Callable;

import org.springframework.web.client.RestTemplate;

public class Task  implements Callable<Result> {
	private final String name;
	 
	public Task(String name) {
        this.name = name;
    }
 
    @Override
    public Result call() throws Exception
    {
       Date dt = new Date();
       Long lg =	dt.getTime();
       RestTemplate restTemplate =new RestTemplate();
       URI uri = new URI("http://localhost:8080/character");
       restTemplate.postForEntity(uri, 
				RandomCharacterGenerator.generateRandomCharacter(), String.class);
       Date dt1 = new Date();
	   Long lg1 =	dt1.getTime();
       return new Result(this.name,lg1-lg );
    }

}
