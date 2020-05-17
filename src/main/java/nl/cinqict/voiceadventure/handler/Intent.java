package nl.cinqict.voiceadventure.handler;

public enum Intent {

    WELCOME_INTENT("WelcomeIntent", new WelcomeHandler()),
    LOOK_INTENT("LookIntent", new LookHandler()),
    MOVE_INTENT("MoveIntent", new MoveHandler()),
    PICKUP_INTENT("PickupIntent", new PickupHandler()),
    USE_INTENT("UseIntent", new UseHandler()),
    KILL_INTENT("KillIntent", new KillHandler()),
    INVENTORY_INTENT("InventoryIntent", new InventoryHandler());

    private final String intentName;
    private final Handler handler;

    Intent(String intentName, Handler handler) {
        this.intentName = intentName;
        this.handler = handler;
    }

    public static Intent getIntent(String intentName) {
        return Intent.valueOf(toScreamingSnakeCase(intentName));
    }

    private static String toScreamingSnakeCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        for (Character c : s.toCharArray()) {
            // add an underscore when there is a capital (but not for the first character)
            if (result.length() != 0 && Character.isUpperCase(c)) {
                result.append("_");
            }
            result.append(c.toString().toUpperCase());
        }

        return result.toString();
    }

    public Handler getHandler() {
        return handler;
    }

    public String getIntentName() {
        return intentName;
    }
}
