package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests the WriteSpacedRepetition class
 */
class WriteSpacedRepetitionTest {

  /**
   * Compares a generated file with the expected output to test writeData.
   * @throws FileNotFoundException If the input path is to a file that doesn't exist
   */
  @Test
  void writeData() throws FileNotFoundException {
    Path pathTest = Path.of("./src/test/resources/results/basicQa.sr");
    WriteSpacedRepetition wsr = new WriteSpacedRepetition(pathTest);

    Question question1 =
        new Question(Difficulty.HARD, "This is a hard question", "This is a hard answer");
    Question question2 = new Question(Difficulty.HARD, "This is another hard question",
        "This is another hard answer");

    ArrayList<Question> questionSet = new ArrayList<>(Arrays.asList(question1, question2));
    wsr.writeData(questionSet);

    // Generating scanners to read files to ensure they are the same
    Path pathActual = Path.of("./src/test/resources/results/expectedBasicQa.sr");
    Scanner scTest = new Scanner(new FileInputStream(pathTest.toFile()));
    Scanner scActual = new Scanner(new FileInputStream(pathActual.toFile()));

    // checking that each line in the produced file is the same as the expected
    while (scActual.hasNext()) {
      assertEquals(scActual.next(), scTest.next());
    }

    // Preventing memory leakage.
    scTest.close();
    scActual.close();

    // Deletes file after test,
    if (pathTest.toFile().exists()) {
      pathTest.toFile().deleteOnExit();
    }

  }
}