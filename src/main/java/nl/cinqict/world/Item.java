package nl.cinqict.world;

import static nl.cinqict.world.Location.*;

public enum Item {

    BUSH("Under the bush lies an old, iron handle.", CLEARING, false),
    HANDLE("The handle is made of iron.", CLEARING, true),
    WELL("In the well lies a rusty, pointy sword.", Location.WELL, false),
    SWORD("The sword is rusty and pointy.", Location.WELL, true),
    TROLL("The troll is a big, lumbering beast. It looks hungry.", CAVE, false),
    KEY("The key looks used.", CAVE, true),
    DOOR("The door made of sturdy wood.", Location.CASTLE, false),
    CASTLE("The castle build with grey bricks and sports a number of yellow flags.", Location.CASTLE, false);

    String description;
    Location location;
    boolean portable;

    Item(String description, Location location, boolean portable) {
        this.description = description;
        this.location = location;
        this.portable = portable;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isPortable() {
        return portable;
    }
}
