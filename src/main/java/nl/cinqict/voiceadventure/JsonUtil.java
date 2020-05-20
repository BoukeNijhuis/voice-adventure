package nl.cinqict.voiceadventure;

import com.google.gson.*;
import nl.cinqict.voiceadventure.world.Item;
import nl.cinqict.voiceadventure.world.Location;

import java.util.HashSet;
import java.util.Set;

public class JsonUtil {

    private static final JsonParser jsonParser = new JsonParser();

    public static JsonObject getJsonObject(String jsonString) {
        return jsonParser.parse(jsonString).getAsJsonObject();
    }

    public static <T extends Enum<T>> JsonArray getJsonArray(Set<? extends Enum<T>> list) {
        JsonArray jsonArray = new JsonArray();

        for (Enum<T> e : list) {
            jsonArray.add(new JsonPrimitive(e.name()));
        }

        return jsonArray;
    }

    public static Set<Item> getItemSet(JsonArray jsonArray) {
        Set<Item> itemSet = new HashSet<>();

        for (JsonElement jsonElement : jsonArray) {
            itemSet.add(Item.valueOf(jsonElement.getAsString()));
        }

        return itemSet;
    }

    public static <T extends Enum<T>> Set<T> getSet(Class<T> clazz, JsonArray jsonArray) {
        Set<T> set = new HashSet<>();

        for (JsonElement jsonElement : jsonArray) {
            set.add(T.valueOf(clazz, jsonElement.getAsString()));
        }

        return set;
    }

    public static <T extends Enum<T>> Set<T> getSet(Class<T> clazz, JsonObject stateParameters, String key) {
        Set<T> set = new HashSet<>();

        try {
            JsonArray jsonArray = stateParameters.get(key).getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                set.add(T.valueOf(clazz, jsonElement.getAsString()));
            }

            return set;
        } catch (NullPointerException e) {
            // if the key is not there, return an empty Set
            return set;
        }
    }

    public static JsonArray getAsJsonArrayNullSafe(JsonObject jsonObject, String key) {
        JsonArray jsonArray = jsonObject.getAsJsonArray(key);
        return (jsonArray != null ? jsonArray : new JsonArray());
    }
}
