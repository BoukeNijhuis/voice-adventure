package nl.cinqict.voiceadventure.world;

public enum Item {

    UNKNOWN("This is special item that used when the specified item does not exist", null, false),
    DOOR("The door made of sturdy wood.", Location.CASTLE, false),
    WINNER("Not a real item, but this item enabled me to delete a constructor :-)", Location.CASTLE, false),
    KEY("The key looks used.", Location.CAVE, true, DOOR, "You finished the game!", WINNER),
    TROLL("The troll is a big, lumbering beast. It looks hungry.", Location.CAVE, false),
    SWORD("The sword is rusty and pointy.", Location.WELL, true, TROLL, "You attack the troll with your sword. It fights back but it is no match for your sword fighting skills. You kill the troll swiftly and take the key.", KEY),
    WELL("In the well lies a wooden bucket with a rusty, pointy sword inside. The bucket is still attached to the lift mechanism.", Location.WELL, false),
    HANDLE("The handle is made of iron.", Location.CLEARING, true, WELL, "The handle seems to be part of the well. You use the handle to lift the bucket and you pickup the sword.", SWORD),
    BUSH("Under the bush lies an old, iron handle.", Location.CLEARING, false),
    CASTLE("The castle build with grey bricks and sports a number of yellow flags.", Location.CASTLE, false);

    String description;
    Location location;
    boolean portable;
    Item canBeUsedOn;
    String useReply;
    Item useReward;

    Item(String description, Location location, boolean portable) {
        this(description, location, portable, null, null, null);
    }

    Item(String description, Location location, boolean portable, Item canBeUsedOn, String useReply, Item useReward) {
        this.description = description;
        this.location = location;
        this.portable = portable;
        this.canBeUsedOn = canBeUsedOn;
        this.useReply = useReply;
        this.useReward = useReward;
    }

    public static Item valueOfNullSafe(String s) {
        try {
            if (s != null && !s.isEmpty()) {
                return Item.valueOf(s);
            } else {
                return Item.UNKNOWN;
            }
        } catch (IllegalArgumentException e) {
            return Item.UNKNOWN;
        }
    }

    public String getName() {
        return name().toLowerCase();
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

    public boolean canBeUsedOn(Item otherItem) {
        return this.canBeUsedOn == otherItem;
    }

    public String getUseReply() {
        return useReply;
    }

    public Item getUseReward() {
        return useReward;
    }
}
