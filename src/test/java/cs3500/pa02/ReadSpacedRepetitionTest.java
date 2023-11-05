package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests ReadSpacedRepetition methods
 */
class ReadSpacedRepetitionTest {

  @Test
  void testConstructorException() {
    Exception exceptionArg0 =
        assertThrows(IllegalArgumentException.class,
            () -> new ReadSpacedRepetition(Path.of("./somePath/me.md")));
    assertEquals("File is not an SR file",
        exceptionArg0.getMessage());
  }

  /**
   * Tests the getQuestion method.
   */
  @Test
  void testGetQuestions() {
    Path pathToSr = Path.of("./src/test/resources/samplefiles/setQA/SimpleSrFile.sr");
    ReadSpacedRepetition rsr = new ReadSpacedRepetition(pathToSr);
    Question questionHard =
        new Question(Difficulty.HARD, "This is a hard question", "This is a hard answer");
    Question questionEasy =
        new Question(Difficulty.EASY, "This is an easy question", "This is an easy answer");

    ArrayList<Question> expectedQuestions =
        new ArrayList<>(Arrays.asList(questionHard, questionEasy));
    ArrayList<Question> actualQuestions = rsr.getQuestions();

    assertEquals(2, actualQuestions.size());

    // Not using assertArrayEquals as I have not overridden the equals method for a question
    // Checking individually the results of getQuestions is the same, but testing each field
    assertEquals(expectedQuestions.get(0).getDifficulty(), actualQuestions.get(0).getDifficulty());
    assertEquals(expectedQuestions.get(1).getDifficulty(), actualQuestions.get(1).getDifficulty());

    assertEquals(expectedQuestions.get(0).getQuestion(), actualQuestions.get(0).getQuestion());
    assertEquals(expectedQuestions.get(1).getQuestion(), actualQuestions.get(1).getQuestion());

    assertEquals(expectedQuestions.get(0).getAnswer(), actualQuestions.get(0).getAnswer());
    assertEquals(expectedQuestions.get(1).getAnswer(), actualQuestions.get(1).getAnswer());
  }
}