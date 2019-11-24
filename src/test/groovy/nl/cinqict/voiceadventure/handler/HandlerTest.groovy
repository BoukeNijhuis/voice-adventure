package nl.cinqict.voiceadventure.handler

import nl.cinqict.voiceadventure.message.Parameters
import nl.cinqict.voiceadventure.message.Request
import nl.cinqict.voiceadventure.message.State
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class HandlerTest extends Specification {

    protected Request request = Mock()
    protected State state = Mock()
    protected Parameters parameters = Mock()

    protected void setup() {
        request.getState() >> state
        request.getParameters() >> parameters
    }
}
