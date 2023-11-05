package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpacedRepetitionTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  /**
   * Sets up the stats class before each test and sets up the output streams.
   */
  @BeforeEach
  public void setUp() {
    // setting up the output streams
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  /**
   * Resets the output streams after each test.
   */
  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }


  /**
   * Tests basic switching of question difficulty and navigating through Question options.
   */
  @Test
  void testStartStudySessionNormal() {
    String inputs =
        "./src/test/resources/samplefiles/setQA/SimpleSrFileCopy.sr" + System.lineSeparator()
            + "2" + System.lineSeparator()  // Number of questions we want to practice
            + "1" + System.lineSeparator()  // Selecting Set Difficulty
            + "1" + System.lineSeparator()  // Selecting EASY
            + "2" + System.lineSeparator()  // Selecting Show Answer
            + "3" + System.lineSeparator()  // Selecting Proceed
            + "1" + System.lineSeparator()  // Selecting Set Difficulty
            + "2" + System.lineSeparator()  // Selecting HARD
            + "4" + System.lineSeparator(); // Selecting End

    String[] expectedOutputs = {
        "Welcome. Beginning Study Session...",
        "Enter Path To Spaced Repetition File: ",
        "How Many Questions Would You Like To Practice Today?: ",
        "Practicing 2 Questions",
        "1. HARD: This is a hard question",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "SELECT ONE:",
        "(1) EASY",
        "(2) HARD",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "Answer: This is a hard answer",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "2. EASY: This is an easy question",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "SELECT ONE:",
        "(1) EASY",
        "(2) HARD",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "END",
        "Total # Questions Answered: 2",
        "# Questions From Easy To Hard: 1",
        "# Questions From Hard To Easy: 1",
        "Total Questions Hard In Bank: 1",
        "Total Questions Easy In Bank: 1",
    };

    // initializing the scanner
    Scanner sc = new Scanner(inputs);
    // starting the SpacedRepetition session with our specific scanner
    SpacedRepetition sr = new SpacedRepetition(sc);
    sr.startStudySession();
    String output = outContent.toString();

    // Getting the outputs that startStudySession prints out
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    this.setUp();
    this.restoreStreams();

    /*
    Below test is to undo changes done in the part above.
    This allows for consistent testing between iterations
    (primarily switching from HARD to EASY, and the back)
     */

    String inputs2 = "./src/test/resources/samplefiles/setQA/SimpleSrFileCopy.sr"
        + System.lineSeparator()
        + "100" + System.lineSeparator()  // Number of Questiosn we want to practice
        + "1" + System.lineSeparator()  // Selecting Set Difficulty
        + "1" + System.lineSeparator()  // Selecting EASY
        + "3" + System.lineSeparator()  // Selecting Proceed
        + "1" + System.lineSeparator()  // Selecting Set Difficulty
        + "2" + System.lineSeparator()  // Selecting HARD
        + "4" + System.lineSeparator();  // Selecting END

    String[] expectedOutputs2 = {
        "Total # Questions Answered: 2",
        "# Questions From Easy To Hard: 1",
        "# Questions From Hard To Easy: 1",
        "Total Questions Hard In Bank: 1",
        "Total Questions Easy In Bank: 1",
    };


    // initializing the scanner
    Scanner sc2 = new Scanner(inputs2);
    // starting the SpacedRepetition session with our specific scanner
    SpacedRepetition sr2 = new SpacedRepetition(sc2);
    sr2.startStudySession();
    String output2 = outContent.toString();

    // Getting the outputs that startStudySession prints out
    String[] outputs2 = output2.split(System.lineSeparator());

    // Testing that the ending screen is correct.
    // Only checking the last five terminal messages as this test will
    int outputs2Length = outputs2.length;
    assertEquals(expectedOutputs2[0], outputs2[outputs2Length - 5]);
    assertEquals(expectedOutputs2[1], outputs2[outputs2Length - 4]);
    assertEquals(expectedOutputs2[2], outputs2[outputs2Length - 3]);
    assertEquals(expectedOutputs2[3], outputs2[outputs2Length - 2]);
    assertEquals(expectedOutputs2[4], outputs2[outputs2Length - 1]);
  }

  /**
   * Primarily tests changing the difficulty of a question to what it already is.
   * Also tests switching between options and the end screen.
   */
  @Test
  void testStartStudySessionSwitch() {
    String inputs = "./src/test/resources/samplefiles/setQA/SimpleSrFileCopy.sr"
        + System.lineSeparator()
        + "200" + System.lineSeparator()  // Number of Questions we want to practice
        + "1" + System.lineSeparator()  // Selecting Set Difficulty
        + "2" + System.lineSeparator()  // Selecting HARD
        + "3" + System.lineSeparator()  // Selecting Proceed
        + "1" + System.lineSeparator()  // Selecting Set Difficulty
        + "1" + System.lineSeparator()  // Selecting EASY
        + "3" + System.lineSeparator();  // Selecting Proceed

    String[] expectedOutputs = {
        "Welcome. Beginning Study Session...",
        "Enter Path To Spaced Repetition File: ",
        "How Many Questions Would You Like To Practice Today?: ",
        "Total Desired Questions Exceeds Total Number of Questions.",
        "Continuing with: 2 Questions",
        "1. HARD: This is a hard question",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "SELECT ONE:",
        "(1) EASY",
        "(2) HARD",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "2. EASY: This is an easy question",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "SELECT ONE:",
        "(1) EASY",
        "(2) HARD",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "Total # Questions Answered: 2",
        "# Questions From Easy To Hard: 0",
        "# Questions From Hard To Easy: 0",
        "Total Questions Hard In Bank: 1",
        "Total Questions Easy In Bank: 1",
    };

    // initializing the scanner
    Scanner sc = new Scanner(inputs);
    // starting the SpacedRepetition session with our specific scanner
    SpacedRepetition sr = new SpacedRepetition(sc);
    sr.startStudySession();
    String output = outContent.toString();

    // Getting the outputs that startStudySession prints out
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);
  }
}