package io.vertx.example;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
@Component
public class RandomCharacterGenerator {

    private static List<String> names = Arrays.asList("John","William","James","Charles","George","Frank","Joseph","Thomas","Henry","Robert","Edward","Harry","Walter","Arthur","Fred","Albert","Samuel","David","Louis","Joe","Charlie","Clarence","Richard","Andrew","Daniel","Ernest","Will","Jesse","Oscar","Lewis","Peter","Benjamin","Frederick","Willie","Alfred","Sam");

    private static String randomFullName() {
        return randomSingleName() + " " + randomSingleName() + " son of " + randomSingleName() + " " + randomSingleName();
    }

    private static String randomSingleName() {
        return names.get(between(0, names.size()-1));
    }

    private static JsonArray randomListOfNames(){
        return IntStream.range(0, between(1,5))
                .mapToObj(i -> randomSingleName())
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
    }

    private static boolean randomBool() {
        return between(0, 1) == 0;
    }

    private static int between(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public static JsonObject generateRandomCharacter() {
        return new JsonObject()
                .put("characterName", randomFullName())
                .put("actorName", randomFullName())
                .put("nickname", randomSingleName())
                .put("royal", randomBool())
                .put("killed", randomBool() ? randomListOfNames() : new JsonArray())
                .put("siblings", randomListOfNames())
                .put("killedBy", randomBool() ? randomListOfNames() : new JsonArray());
    }

    public static void main(String[] args) {
        IntStream.range(0, 10)
                .forEach(i -> System.out.println(generateRandomCharacter()));
    }

}
