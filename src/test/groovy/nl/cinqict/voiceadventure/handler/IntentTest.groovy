package nl.cinqict.voiceadventure.handler

import spock.lang.Specification

class IntentTest extends Specification {

    void "test screaming snakecase converter"() {
        expect:
        Intent.toScreamingSnakeCase(input) == result
        where:
        input           | result
        "WelcomeIntent" | "WELCOME_INTENT"
        "welcomeIntent" | "WELCOME_INTENT"
        null            | null
        ""              | ""
        "a"             | "A"
        "aa"            | "AA"
        "AA"            | "A_A"
    }
}
