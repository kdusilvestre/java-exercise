package io.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SimpleREST extends AbstractVerticle {

  private StorageService storageService = new StorageService();

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