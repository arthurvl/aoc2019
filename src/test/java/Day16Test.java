import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {

    @Test
    void applySinglePhaseTo123456789() {
        assertEquals("48226158", Day16.applySinglePhase("12345678"));
    }

    @Test
    void applyTwoPhaseTo123456789() {
        assertEquals("34040438", Day16.applyMultiPhase("12345678", 2));
    }

    @Test
    void applyThreePhaseTo123456789() {
        assertEquals("03415518", Day16.applyMultiPhase("12345678", 3));
    }

    @Test
    void applyFourPhaseTo123456789() {
        assertEquals("01029498", Day16.applyMultiPhase("12345678", 4));
    }

    @Test
    void applyHundredPhaseExamples() {
        assertHundredPhaseStartsWith("24176176", "80871224585914546619083218645595");
        assertHundredPhaseStartsWith("73745418", "19617804207202209144916044189917");
        assertHundredPhaseStartsWith("52432133", "69317163492948606335995924319873");
    }

    private void assertHundredPhaseStartsWith(String expected, String input) {
        String after100Phases = Day16.applyMultiPhase(input, 100);
        String first8 = after100Phases.substring(0,8);

        assertEquals(expected, first8);
    }
}