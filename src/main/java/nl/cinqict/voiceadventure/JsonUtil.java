package nl.cinqict.voiceadventure;

import com.google.gson.*;
import nl.cinqict.voiceadventure.world.Item;

import java.util.HashSet;
import java.util.Set;

public class JsonUtil {

    private static final JsonParser jsonParser = new JsonParser();

    public static JsonObject getJsonObject(String jsonString) {
        return jsonParser.parse(jsonString).getAsJsonObject();
    }

    public static JsonArray getJsonArray(Set<Item> list) {
        JsonArray jsonArray = new JsonArray();

        for (Item item : list) {
            jsonArray.add(new JsonPrimitive(item.name()));
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
}
