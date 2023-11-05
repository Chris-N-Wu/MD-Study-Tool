package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Tests for the CompFileNames class
 */
class FileNameComparatorTest {

  /**
   * Tests comparing file names lexicographically
   */
  @Test
  void compare() {
    Path p1a = Path.of("./Notes/CS101/arrays.md");
    Path p1b = Path.of("../pa02-Chris-N-Wu/Notes/CS101/arrays.md");
    Path p2 = Path.of("./Notes/CS202/vectors.md");

    FileNameComparator cfn = new FileNameComparator();
    // testing reflexivity
    assertEquals(cfn.compare(p1a, p1b), 0);
    // testing when the first object precedes the 2nd lexicographically
    assertTrue(cfn.compare(p1a, p2) < 0);
    // testing when the second object succeeds the 1st lexicographically
    assertTrue(cfn.compare(p2, p1a) > 0);
  }
}