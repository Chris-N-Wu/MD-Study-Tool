package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests for the StudyGuide class
 */
class StudyGuideTest {

  /**
   * Tests the combination of ReadStudyGuide and WriteStudyGuide
   */
  @Test
  public void testConstructor() throws FileNotFoundException {
    /*
    Testing expected outputs
     */
    Scanner scActual;
    Scanner scTest;

    // testing results of provided files (arrays.md and vectors.md)
    StudyGuide sg1 = new StudyGuide("./src/test/resources/samplefiles/setGiven", "FILENAME",
        "./src/test/resources/results/fileGivenTest");
    sg1.createStudyGuide();

    try { // The file may not exist, in which case we need to handle that error (hence try-catch)
      Path path = Path.of("./src/test/resources/results/fileGivenTest.md");
      scTest = new Scanner(new FileInputStream(path.toFile()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // reading the file with the expected results
    try {
      Path path = Path.of("./src/test/resources/results/expectedCombine.md");
      scActual = new Scanner(new FileInputStream(path.toFile()));
    } catch (InvalidPathException e) {
      throw new RuntimeException(e);
    }

    // Asserting that both files are identical
    while (scActual.hasNext()) {
      assertEquals(scActual.next(), scTest.next());
    }

    /*
    Testing results of known basic file
     */
    // creating the test file
    StudyGuide sg2 = new StudyGuide("./src/test/resources/samplefiles/setOne", "FILENAME",
        "./src/test/resources/results/fileTest");
    sg2.createStudyGuide();

    try { // The file may not exist, in which case we need to handle that error (hence try-catch)
      Path path = Path.of("./src/test/resources/results/fileTest.md");
      scTest = new Scanner(new FileInputStream(path.toFile()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // reading the file with the expected results
    try { // The file may not exist, in which case we need to handle that error (hence try-catch)
      Path path = Path.of("./src/test/resources/results/expectedFile.md");
      scActual = new Scanner(new FileInputStream(path.toFile()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Asserting that both files are identical
    while (scActual.hasNext()) {
      assertEquals(scTest.next(), scActual.next());
    }
  }

  /**
   * Tests for the StudyGuide constructor. Tests the expected exceptions produced.
   */
  @Test
  public void testConstructorExceptions() {
    // tests when illegal characters are in the 1st argument
    IllegalArgumentException exceptionArg1 = assertThrows(IllegalArgumentException.class,
        () -> new StudyGuide(
            "#%&{}\\<>*?/ $!'\":@+`|=_\\:\\/\\}>___{:/.N#\\0%&\0{}\\<>*?/ $!'\":@+`|=_\\:\\/\\}"
                + ">_otes//.#%&{}\\<>*?/ $!'\":@+`|=_\\:\\/\\}>_md.s{!.md.",
            "2",
            "./src/test/resources/"));
    assertEquals("Error: Invalid Path to Input Files", exceptionArg1.getMessage());

    // tests when a number too large is provided for the sorting flag
    IllegalArgumentException exceptionArg2a = assertThrows(IllegalArgumentException.class,
        () -> new StudyGuide("./src/test/resources", "3", "./src/test/resources/"));
    assertEquals("Error: Sort Flag Must Either Be FILENAME, MODIFIED, or CREATION",
        exceptionArg2a.getMessage());

    // tests when the input sorting flag is not a number
    IllegalArgumentException exceptionArg2b = assertThrows(IllegalArgumentException.class,
        () -> new StudyGuide("./src/test/resources", "text here", "./src/text/resources/"));
    assertEquals("Error: Sort Flag Must Either Be FILENAME, MODIFIED, or CREATION",
        exceptionArg2b.getMessage());

    // tests when illegal characters are in the 3rd argument
    IllegalArgumentException exceptionArg3 = assertThrows(IllegalArgumentException.class,
        () -> new StudyGuide("./src/test/resources", "FILENAME",
            "./src/text_\\:#\\0%&\0{}\\<>*?/ $!\\'\":@+`|=_:\\:.|.<>/resources/.m"
                + "m.md#%&{}\\<>*?/ $!'\":@+`|=_\\:\\/\\}>_"));
    assertEquals("Error: Invalid Path to Output File", exceptionArg3.getMessage());
  }

  /**
   * Tests for the construct method
   */
  @Test
  public void testCreateStudyGuide() {
    // testing that the creation of study guides work
    // also testing each sort flag
    StudyGuide sgFilename =
        new StudyGuide("./src/test/resources/samplefiles/setOne/headers.md", "FILENAME",
            "./src/test/resources/results/fileTest0");
    StudyGuide sgModified =
        new StudyGuide("./src/test/resources/samplefiles/setOne/headers.md", "MODIFIED",
            "./src/test/resources/results/fileTest1");
    StudyGuide sgCreation =
        new StudyGuide("./src/test/resources/samplefiles/setOne/headers.md", "CREATION",
            "./src/test/resources/results/fileTest2");

    sgFilename.createStudyGuide();
    sgModified.createStudyGuide();
    sgCreation.createStudyGuide();

    // testing with sort FILENAME
    assertTrue(Path.of("./src/test/resources/results/fileTest0.md").toFile().exists());
    // testing with sort MODIFIED
    assertTrue(Path.of("./src/test/resources/results/fileTest1.md").toFile().exists());
    // testing with sort 2 CREATION
    assertTrue(Path.of("./src/test/resources/results/fileTest2.md").toFile().exists());
  }
}