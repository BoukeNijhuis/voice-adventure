package nl.cinqict.voiceadventure.handler;

import nl.cinqict.voiceadventure.message.Parameters;
import nl.cinqict.voiceadventure.message.Request;
import nl.cinqict.voiceadventure.message.State;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

class HandlerTest {

    Request request;
    State state;
    Parameters parameters;

    @BeforeEach
    void setup() {
        request = Mockito.mock(Request.class);
        state = Mockito.mock(State.class);
        parameters = Mockito.mock(Parameters.class);
        when(request.getState()).thenReturn(state);
        when(request.getParameters()).thenReturn(parameters);
    }
}
