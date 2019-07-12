package io.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleREST extends AbstractVerticle {
	
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	  private StorageService storageService = context.getBean("storageService", StorageService.class);
	  
  @Override
  public void start() {
    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());
    router.get("/character").handler(this::handleFetchingCharacters);
    router.post("/character").handler(this::handleAddingCharacters);

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }

  private void handleAddingCharacters(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();

    JsonObject characterJson = routingContext.getBodyAsJson();

    if (characterJson == null || characterJson.isEmpty() || characterJson.getString("characterName") == null) {
      sendError(400, response);
    } else {
      storageService.add(characterJson);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      

      response.end("Added character "+characterJson.getString("characterName")+" on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
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