package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisplayTest {
  private Display display;
  private Scanner sc;
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

    // initializing the scanner
    this.sc = new Scanner(System.in);

    //initializing the session statistics
    this.display = new Display(this.sc);
  }

  /**
   * Resets the output streams after each test.
   */
  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);

    sc.close();
  }

  /**
   * Tests the welcome message
   */
  @Test
  void testPrintWelcome() {
    display.printWelcome();
    String expectedWelcome = "Welcome. Beginning Study Session..." + System.lineSeparator();
    assertEquals(expectedWelcome, outContent.toString());
  }

  /**
   * Tests the number of questions verification message when the number of questions is over
   * the number of total questions in the test bank. Denoted by the boolean.
   */
  @Test
  void printQuestionNumTrue() {
    display.printQuestionNum(10, true);
    String expectedQuestionNum =
        "Total Desired Questions Exceeds Total Number of Questions." + System.lineSeparator()
            + "Continuing with: 10 Questions" + System.lineSeparator();
    assertEquals(expectedQuestionNum, outContent.toString());
  }

  /**
   * Tests the number of questions verification message when the number of questions is not over
   * the number of total questions in the test bank. Denoted by the boolean.
   */
  @Test
  void printQuestionNumFalse() {
    display.printQuestionNum(100, false);

    // Expected console message
    String expectedQuestionNum = "Practicing 100 Questions" + System.lineSeparator();
    assertEquals(expectedQuestionNum, outContent.toString());
  }

  /**
   * Tests showing a formatted question string to the user in the terminal.
   */
  @Test
  void printQuestion() {
    Question tempQuestion = new Question(Difficulty.HARD, "Question", "Answer");
    display.printQuestion(10, tempQuestion);

    // Expected console message
    String expectedPrintQuestion = "10. HARD: Question" + System.lineSeparator();
    assertEquals(expectedPrintQuestion, outContent.toString());
  }

  /**
   * Tests showing a formatted answer string to the user in the terminal.
   */
  @Test
  void printAnswer() {
    Question tempQuestion = new Question(Difficulty.HARD, "Question", "Answer");
    display.printAnswer(tempQuestion);

    // Expected console message
    String expectedPrintQuestion = "Answer: Answer" + System.lineSeparator();
    assertEquals(expectedPrintQuestion, outContent.toString());
  }

  /**
   * Tests the promptSrPath method. Tests each branch of the while loop with multiple different
   * input paths, with only the last one being valid.
   */
  @Test
  void promptSrPath() {
    // Setting the inputs and expected outputs
    String inputs =
        "./src/test/java/resources/results/expectedComplex.md" + System.lineSeparator()
            + "./src/test/java/resources/results/expectedComplexAA.sr" + System.lineSeparator()
            + "./src/test/jav>!a/rces/resu\0lts/expect<>ed\0<>Complex.sr" + System.lineSeparator()
            + "./src/test/resources/results/expectedComplex.sr";

    String[] expectedOutputs =
          {"Enter Path To Spaced Repetition File: ",
              "Invalid File. Please Re-Enter.",
              "Enter Path To Spaced Repetition File: ",
              "Invalid File. Please Re-Enter.",
              "Enter Path To Spaced Repetition File: ",
              "Invalid Path. Please Re-Enter.",
              "Enter Path To Spaced Repetition File: "};

    // initializing the scanner
    this.sc = new Scanner(inputs);
    // starting the Display with our specific scanner
    this.display = new Display(this.sc);

    Path outputPath = display.promptSrPath();
    String output = outContent.toString();

    // Getting the outputs that promptSrPath prints out
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    Path expectedOutputPath = Path.of("./src/test/resources/results/expectedComplex.sr");

    // Testing that the output path is correct
    assertEquals(expectedOutputPath, outputPath);

    sc.close();
  }

  /**
   * Tests the promptNumQuestions method.
   * Tests with expected errors then a valid answer. Errors do not crash the program, but are
   * printed out to the user, then the user is prompted to re-input.
   */
  @Test
  void promptNumQuestionsErrors() {
    String inputs = "-2" + System.lineSeparator() + "abc" + System.lineSeparator() + "30";
    String[] expectedOutputs = {
        "How Many Questions Would You Like To Practice Today?: ",
        "Cannot Practice Zero Or Fewer Questions",
        "How Many Questions Would You Like To Practice Today?: ",
        "Provide an Integer. [0-9]+",
        "How Many Questions Would You Like To Practice Today?: "};

    // initializing the scanner
    this.sc = new Scanner(inputs);
    // starting the Display with our specific scanner
    this.display = new Display(this.sc);

    // gets the outputs from the method and terminal
    int outputInt = display.promptNumQuestions();
    String output = outContent.toString();

    // Getting the outputs that promptNumQuestions prints out
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    // Testing that the output number is correct
    assertEquals(30, outputInt);

    sc.close();
  }


  /**
   * Tests first with incorrect options selected then into a valid input.
   */
  @Test
  void promptQuestionOptionsErrors() {
    String inputs = "5" + System.lineSeparator() + "ABC" + System.lineSeparator() + "1";
    String[] expectedOutputs = {"SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "Invalid Selection. Re-Select.",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session",
        "Enter an Integer (1, 2, 3, or 4). Re-Select.",
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session"};

    // Setting up our scanners and display for this specific test.
    // Cannot use scanner initialized beforehand as scanner must take in the input string.
    this.sc = new Scanner(inputs);
    this.display = new Display(this.sc);

    QuestionOptions outputOption = this.display.promptQuestionOptions();

    String output = outContent.toString();
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    // Testing this input will output correct QuestionOption
    assertEquals(QuestionOptions.SETDIFFICULTY, outputOption);

    sc.close();
  }

  /**
   * Tests with a value that should correlate with Show Answer
   */
  @Test
  void promptQuestionOptions2() {
    String inputs = "2";
    String[] expectedOutputs = {"SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session"};

    // Setting up our scanners and display for this specific test.
    // Cannot use scanner initialized beforehand as scanner must take in the input string.
    this.sc = new Scanner(inputs);
    this.display = new Display(this.sc);

    QuestionOptions outputOption = this.display.promptQuestionOptions();

    // Getting the terminal outputs
    String output = outContent.toString();
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    // Testing this input will output correct QuestionOption
    assertEquals(QuestionOptions.SHOWANSWER, outputOption);

    sc.close();
  }

  /**
   * Tests with a value that should correlate with Proceed To Next
   */
  @Test
  void promptQuestionOption3() {
    String inputs = "3";
    String[] expectedOutputs = {
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session"};

    // Setting up our scanners and display for this specific test.
    // Cannot use scanner initialized beforehand as scanner must take in the input string.
    this.sc = new Scanner(inputs);
    this.display = new Display(this.sc);

    QuestionOptions outputOption = this.display.promptQuestionOptions();

    // Getting the terminal outputs
    String output = outContent.toString();
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    // Testing this input will output correct QuestionOption
    assertEquals(QuestionOptions.PROCEED, outputOption);

    sc.close();
  }

  /**
   * Tests with a value that should correlate with End Session
   */
  @Test
  void promptQuestionOption4() {
    String inputs = "4";
    String[] expectedOutputs = {
        "SELECT ONE:",
        "(1) Set Difficulty",
        "(2) Show Answer",
        "(3) Proceed To Next",
        "(4) End Session"};

    // Setting up our scanners and display for this specific test.
    // Cannot use scanner initialized beforehand as scanner must take in the input string.
    this.sc = new Scanner(inputs);
    this.display = new Display(this.sc);

    QuestionOptions outputOption = this.display.promptQuestionOptions();

    // Getting the terminal outputs
    String output = outContent.toString();
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    // Testing this input will output correct QuestionOption
    assertEquals(QuestionOptions.END, outputOption);

    sc.close();
  }

  /**
   * Tests the promptSetDifficulty method.
   * Tests first with values that will pass an error (in the form of a print statement), then a
   * valid input.
   */
  @Test
  void promptSetDifficultyErrors() {
    String inputs = "40" + System.lineSeparator() + "abc" + System.lineSeparator() + "1";
    String[] expectedOutputs = {
        "SELECT ONE:",
        "(1) EASY",
        "(2) HARD",
        "Invalid Selection. Re-Select",
        "SELECT ONE:",
        "(1) EASY",
        "(2) HARD",
        "Enter an Integer (1 or 2) Re-Select.",
        "SELECT ONE:",
        "(1) EASY",
        "(2) HARD"};

    // initializing the scanner
    this.sc = new Scanner(inputs);
    // starting the Display with our specific scanner
    this.display = new Display(this.sc);

    // gets the outputs from the method and terminal
    Difficulty outputDifficulty = display.promptSetDifficulty();
    String output = outContent.toString();

    // Getting the outputs that promptNumQuestions prints out
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    // Testing that the output number is correct
    assertEquals(Difficulty.EASY, outputDifficulty);

    sc.close();
  }

  /**
   * Tests the promptSetDifficulty method.
   * Tests first with value to return Difficulty.HARD.
   */
  @Test
  void promptSetDifficulty2() {
    String inputs = "2";
    String[] expectedOutputs = {"SELECT ONE:",
        "(1) EASY",
        "(2) HARD"};

    // initializing the scanner
    this.sc = new Scanner(inputs);
    // starting the Display with our specific scanner
    this.display = new Display(this.sc);

    // gets the outputs from the method and terminal
    Difficulty outputDifficulty = display.promptSetDifficulty();
    String output = outContent.toString();

    // Getting the outputs that promptNumQuestions prints out
    String[] outputs = output.split(System.lineSeparator());

    // Testing that each output line is correct.
    assertArrayEquals(expectedOutputs, outputs);

    // Testing that the output number is correct
    assertEquals(Difficulty.HARD, outputDifficulty);

    sc.close();
  }
}