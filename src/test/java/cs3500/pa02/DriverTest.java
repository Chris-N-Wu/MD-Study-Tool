package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Driver (starting point) class
 */
class DriverTest {

  /**
   * Tests for the initial handling of arguments
   */
  @Test
  void testMain() {
    // tests when only given 2 arguments
    String[] args0 = {"a", "b", "c", "d"};
    Exception exceptionArg0 =
        assertThrows(IllegalArgumentException.class, () -> Driver.main(args0));
    assertEquals("Exactly Zero (or One*) OR Three Input Arguments Required",
        exceptionArg0.getMessage());

    // tests when only given 2 arguments
    String[] args2 = {"a", "b"};
    Exception exceptionArg2 =
        assertThrows(IllegalArgumentException.class, () -> Driver.main(args2));
    assertEquals("Exactly Zero (or One*) OR Three Input Arguments Required",
        exceptionArg2.getMessage());

    // tests when given 4 arguments
    String[] args4 = {"a", "b", "c", "d"};
    Exception exceptionArg4 =
        assertThrows(IllegalArgumentException.class, () -> Driver.main(args4));
    assertEquals("Exactly Zero (or One*) OR Three Input Arguments Required",
        exceptionArg4.getMessage());

    // tests when all arguments are legal
    String[] args =
          {"./src/test/resources/samplefiles/setOne", "FILENAME",
              "./src/test/resources/results/file"};
    Driver.main(args);
    Path path = Path.of("./src/test/resources/results/expectedFile.md");
    assertTrue(path.toFile().exists());
  }
}