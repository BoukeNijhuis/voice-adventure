package nl.cinqict;

import java.util.HashMap;

public class ObjectMap {

    private static HashMap<String, Object> objectMap = new HashMap<>();

    // todo: add locations

    static {
        add("bush", "Under the bush lies an old, iron handle.", false);
        add("handle", "The handle is made of iron.", true);
        add("well", "In the well lies a rusty, pointy sword.", false);
        add("sword", "The sword is rusty and pointy.", true);
        add("troll", "The troll is a big, lumbering beast. It looks hungry.", false);
        add("key", "The key looks used.", true);
        add("door", "The door made of sturdy wood.", false);
        add("castle", "The castle build with grey bricks and sports a number of yellow flags.", false);
    }

    private static void add(String name, String description, boolean isTransportable) {
        objectMap.put(name, new Object(name, description, isTransportable));
    }

    public static Object get(String name) {
        return objectMap.get(name);
    }

    public static class Object {

        private String name;
        private String description;
        private boolean isTransportable;

        public Object(String name, String description, boolean isPortable) {
            this.name = name;
            this.description = description;
            this.isTransportable = isPortable;
        }

        public String getDescription() {
            return description;
        }
    }
}
