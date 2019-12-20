import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {
    @ParameterizedTest
    @CsvSource(
            {"111111,1",
            "223450,0",
            "123789,0",
            "111123,1",
            "135679,0",
            "159999,1"})
    public void examplesMeetCriteria(String example, int met) {
        if (met != 0) {
            assertTrue(Day4.isValidPasswordPartOne(example));
        } else {
            assertFalse(Day4.isValidPasswordPartOne(example));
        }
    }

    @ParameterizedTest
    @CsvSource(
            {"111111,1",
             "223450,0",
             "123789,1",
             "111123,1",
             "135679,1",
             "159999,1"
            })
    public void exmplesMonotonicallyIncreasing(String example, int met) {
        if (met != 0) {
            assertTrue(Day4.monotonicallyIncreasing(example));
        } else {
            assertFalse(Day4.monotonicallyIncreasing(example));
        }
    }

    @ParameterizedTest
    @CsvSource(
            {"111111,1",
             "223450,1",
             "123789,0",
             "111123,1",
             "135679,0",
             "159999,1"
            }
    )
    public void examplesContainAdjacentDuplicate(String example, int met) {
        if (met != 0) {
            assertTrue(Day4.containsAdjacentEquals(example));
        } else {
            assertFalse(Day4.containsAdjacentEquals(example));
        }
    }

    @ParameterizedTest
    @CsvSource(
            {"111111,0",
            "223450,1",
            "123789,0",
            "111123,0",
            "135679,0",
            "159999,0",
            "112233,1",
            "123444,0",
            "111122,1",
            "122333,1"
            }
    )
    public void examplesContainAdjacentSetOfSize2(String example, int met) {
        if (met != 0) {
            assertTrue(Day4.containsAdjacentSetOfSize2(example));
        } else {
            assertFalse(Day4.containsAdjacentSetOfSize2(example));
        }
    }
}