package nl.cinqict.voiceadventure.handler;

import java.lang.reflect.InvocationTargetException;

public enum Intent {

    WELCOME_INTENT("WelcomeIntent", WelcomeHandler.class),
    LOOK_INTENT("LookIntent", LookHandler.class),
    MOVE_INTENT("MoveIntent", MoveHandler.class),
    PICKUP_INTENT("PickupIntent", PickupHandler.class),
    USE_INTENT("UseIntent", UseHandler.class),
    INVENTORY_INTENT("InventoryIntent", InventoryHandler.class);

    private String intentName;
    private Class<Handler> handler;

    Intent(String intentName, Class handler) {
        this.intentName = intentName;
        this.handler = handler;
    }

    public static Intent getIntent(String intentName) {
        final Intent[] intents = Intent.values();

        // TODO: inefficient
        for (Intent intent : intents) {
            if (intent.intentName.equals(intentName)) {
                return intent;
            }
        }

        return null;
    }

    public Handler getHandler() {
        try {
            return handler.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return new DefaultHandler();
        }
    }

    public String getIntentName() {
        return intentName;
    }
}
