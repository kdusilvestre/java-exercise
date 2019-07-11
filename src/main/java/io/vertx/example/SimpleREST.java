package io.vertx.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
@RestController
public class SimpleREST extends AbstractVerticle {
  @Autowired
  private StorageService storageService;

  @Override
  public void start() {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/character").handler(this::handleFetchingCharacters);
    router.post("/character").handler(this::handleAddingCharacters);
    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
  /**
   * Get character based on filter 
   * @param name the name
   * @param is_alive the killed
   * @param kill_count_range the killedBy
   * @return jsonArray the jsonArray
   */
  @GetMapping("/character")
  public JsonArray getCharacters(@RequestParam(value = "name", required=false) final String name, 
		  @RequestParam(value = "is_alive", required=false) final Boolean is_alive, 
		  @RequestParam(value = "kill_count_range", required=false) final String kill_count_range) {
	Consumer<? super Entry<String, Object>> jsonArrayConsumer = null;
	Map<String,String> paramMap = new HashMap<String,String>();
	if(name!=null) {
		paramMap.put("name", name);
	}else if(is_alive != null) {
	  paramMap.put("is_alive", String.valueOf(is_alive));
	}else if(kill_count_range!= null) {
	  paramMap.put("kill_count_range", kill_count_range);
	  
	}
	return storageService.getAll(paramMap);
  }
  /**
   * Insert the characters to Database
   * @param jsonObject the jsonObject
   */
  @PostMapping("/character")
  public void putCharacters(@RequestBody JsonObject jsonObject) {
	  storageService.add(jsonObject);
  }
  private void handleAddingCharacters(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    JsonObject characterJson = routingContext.getBodyAsJson();
    if (characterJson == null || characterJson.isEmpty() || 
    		characterJson.getString("characterName") == null) {
      sendError(400, response);
    } else {
      storageService.add(characterJson);
      response.end();
    }
  }

  // Responds with a JSON Array
  private void handleFetchingCharacters(RoutingContext routingContext) {
   try {
      MultiMap getParams = routingContext.request().params();
      Map<String, String> paramMap = getParams.entries()
              .stream()
              .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      JsonArray arr = storageService.getAll(paramMap);
      routingContext.response().putHeader("content-type", "application/json")
            .end(arr.encodePrettily());
    } catch (Exception e) {
      e.printStackTrace();
      sendError(500, routingContext.response());
    }

  }

  private void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }
}