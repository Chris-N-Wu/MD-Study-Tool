package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the ReadStudyGuide class and the abstract class it extends
 */
class ReadStudyGuideTest {
  ReadStudyGuide rsg;

  @BeforeEach
  void setUp() {
    this.rsg = new ReadStudyGuide();
  }

  /**
   * Testing getting the important contents in a .md file.
   * Edge cases are more extensively tested in {@link StudyGuideTest}.
   */
  @Test
  void testGetImportantContents() {
    Path pathToSimple = Path.of("./src/test/resources/samplefiles/setThree/simpleImportant.md");
    ArrayList<Path> pathList = new ArrayList<>(List.of(pathToSimple));

    String expectedImportant = "- This is important" + System.lineSeparator()
        + "- This is also important but on multiple lines" + System.lineSeparator();

    rsg.readAllFiles(pathList);
    assertEquals(expectedImportant, rsg.getImportantContents());
  }

  /**
   * Testing getting the question and answer blocks in a .md file.
   */
  @Test
  void testGetImportantQuestionAnswer() {
    Path pathToSimple = Path.of("./src/test/resources/samplefiles/setThree/simpleQA.md");
    ArrayList<Path> pathList = new ArrayList<>(List.of(pathToSimple));

    String expectedImportant = "HARD" + System.lineSeparator()
        + "This is a question" + System.lineSeparator()
        + "And this is an answer" + System.lineSeparator() + System.lineSeparator()
        + "HARD" + System.lineSeparator()
        + "This is another question" + System.lineSeparator()
        + "But on multiple lines";

    rsg.readAllFiles(pathList);
    assertEquals(expectedImportant, rsg.getImportantQuestionAnswer());
  }

  /**
   * Testing the readAllFiles method. Method is void, thus only testing exception.
   * Results of this method is verified extensively through testGetImportantContent,
   * testGetImportantQuestionAnswer, and other test methods.
   */
  @Test
  void testReadAllFiles() {
    // testing exception thrown with a file that does not exist
    Path path1 = Path.of("./src/test/resources/samplefiles/setGiven/FAKE.md");
    ArrayList<Path> pathsBad = new ArrayList<>(List.of(path1));

    Exception exceptionArg1 = assertThrows(RuntimeException.class,
        () -> rsg.readAllFiles(pathsBad));
    assertEquals("Error: File Does Not Exist", exceptionArg1.getMessage());
  }

  @Test
  void testStripLeadingBrackets() {
    // asserts that when there are leading brackets, they are removed
    assertEquals("nothing", rsg.stripLeadingBrackets("  [[  nothing"));
    // asserts that when there is no leading brackets, nothing is removed
    assertEquals("nothing", rsg.stripLeadingBrackets("nothing"));
  }

  /**
   * Testing the strip following brackets method
   */
  @Test
  void testStripFollowingBrackets() {
    // asserts that when there are following brackets, they are stripped
    assertEquals("nothing", rsg.stripFollowingBrackets("nothing]]", 9));
    // asserts that when there is no following brackets, nothing is stripped
    assertEquals("nothing", rsg.stripFollowingBrackets("nothing", 7));
  }
}