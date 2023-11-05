package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import org.junit.jupiter.api.Test;

/**
 * Tests for the CompModifiedTime class
 */
class FileModifiedTimeComparatorTest {

  /**
   * Tests comparing files by last modified time
   *
   * @throws IOException handles exceptions from setTimes method
   */
  @Test
  void compare() throws IOException {
    Path p1a = Path.of("./Notes/CS101/arrays.md");
    Path p1b = Path.of("../pa02-Chris-N-Wu/Notes/CS101/arrays.md");
    Path p2 = Path.of("./Notes/CS202/vectors.md");

    BasicFileAttributeView attrsV1a = Files.getFileAttributeView(p1a, BasicFileAttributeView.class);
    BasicFileAttributeView attrsV1b = Files.getFileAttributeView(p1b, BasicFileAttributeView.class);
    BasicFileAttributeView attrsV2 = Files.getFileAttributeView(p2, BasicFileAttributeView.class);

    // setting the times of both files
    attrsV1a.setTimes(FileTime.fromMillis(100), FileTime.fromMillis(100),
        FileTime.fromMillis(1000));
    attrsV1b.setTimes(FileTime.fromMillis(100), FileTime.fromMillis(100),
        FileTime.fromMillis(1000));
    attrsV2.setTimes(FileTime.fromMillis(100000), FileTime.fromMillis(100000),
        FileTime.fromMillis(1000000));

    FileModifiedTimeComparator cmt = new FileModifiedTimeComparator();

    // testing reflexivity
    assertEquals(cmt.compare(p1a, p1b), 0);
    // testing when the first object's last modified time comes before the 2nd
    assertTrue(cmt.compare(p1a, p2) < 0);
    // testing when the 2nd object's last modified time comes after the 1st
    assertTrue(cmt.compare(p2, p1a) > 0);
    // testing exception thrown a path to file does not exist
    Path p3 = Path.of("./Something/NotPath");
    assertThrows(IllegalArgumentException.class, () -> cmt.compare(p1a, p3));
  }
}