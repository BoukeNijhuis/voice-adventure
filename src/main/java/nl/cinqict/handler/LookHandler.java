package nl.cinqict.handler;

import nl.cinqict.*;
import nl.cinqict.ObjectMap.Object;

public class LookHandler extends Handler {

    @Override
    public void updateState(Request request) {
        State state = request.getState();
        Parameters parameters = request.getParameters();
        Object object = ObjectMap.get(parameters.getObject());

        if (object != null) {
            reply = object.getDescription();
        } else {
            reply = WorldMap.getDescription(state.getPosition());
        }
    }
}
