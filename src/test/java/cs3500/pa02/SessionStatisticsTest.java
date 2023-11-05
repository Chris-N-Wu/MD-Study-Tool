package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionStatisticsTest {
  private SessionStatistics stats;
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

    //initializing the session statistics
    this.stats = new SessionStatistics();
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
   * Tests the printStats method. Tested through side effects of methods below.
   */
  @Test
  void testPrintStats() {
    this.stats.printStats();

    String expectedStats = "Total # Questions Answered: 0" + System.lineSeparator()
        + "# Questions From Easy To Hard: 0" + System.lineSeparator()
        + "# Questions From Hard To Easy: 0" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests incrementing the # of questions answered.
   */
  @Test
  void testIncrementAnsweredOne() {
    String expectedStats = "Total # Questions Answered: 1" + System.lineSeparator()
        + "# Questions From Easy To Hard: 0" + System.lineSeparator()
        + "# Questions From Hard To Easy: 0" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    this.stats.incrementAnswered();
    this.stats.printStats();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests incrementing the # of questions answered.
   * Testing with incrementing twice. Must be in a new test method as the output stream test gets
   * messed up with multiple in one method.
   */
  @Test
  void testIncrementAnsweredTwo() {
    this.stats.incrementAnswered();
    this.stats.incrementAnswered();
    this.stats.printStats();

    String expectedStats = "Total # Questions Answered: 2" + System.lineSeparator()
        + "# Questions From Easy To Hard: 0" + System.lineSeparator()
        + "# Questions From Hard To Easy: 0" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests incrementing the # of questions from easy to hard.
   */
  @Test
  void testIncrementEasyToHardOne() {
    String expectedStats = "Total # Questions Answered: 0" + System.lineSeparator()
        + "# Questions From Easy To Hard: 1" + System.lineSeparator()
        + "# Questions From Hard To Easy: 0" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    this.stats.incrementEasyToHard();
    this.stats.printStats();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests incrementing the # of questions from easy to hard.
   * Testing with incrementing twice. Must be in a new test method as the output stream test gets
   * messed up with multiple in one method.
   */
  @Test
  void testIncrementEasyToHardTwo() {
    this.stats.incrementEasyToHard();
    this.stats.incrementEasyToHard();
    this.stats.printStats();

    String expectedStats = "Total # Questions Answered: 0" + System.lineSeparator()
        + "# Questions From Easy To Hard: 2" + System.lineSeparator()
        + "# Questions From Hard To Easy: 0" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests incrementing the # of questions hard to easy.
   */
  @Test
  void testIncrementHardToEasyOne() {
    String expectedStats = "Total # Questions Answered: 0" + System.lineSeparator()
        + "# Questions From Easy To Hard: 0" + System.lineSeparator()
        + "# Questions From Hard To Easy: 1" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    this.stats.incrementHardToEasy();
    this.stats.printStats();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests incrementing the # of questions hard to easy.
   * Testing with incrementing twice. Must be in a new test method as the output stream test gets
   * messed up with multiple in one method.
   */
  @Test
  void testIncrementHardToEasyTwo() {
    this.stats.incrementHardToEasy();
    this.stats.incrementHardToEasy();
    this.stats.printStats();

    String expectedStats = "Total # Questions Answered: 0" + System.lineSeparator()
        + "# Questions From Easy To Hard: 0" + System.lineSeparator()
        + "# Questions From Hard To Easy: 2" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests setting the total number of questions hard.
   */
  @Test
  void testSetTotalHard() {
    String expectedStats = "Total # Questions Answered: 0" + System.lineSeparator()
        + "# Questions From Easy To Hard: 0" + System.lineSeparator()
        + "# Questions From Hard To Easy: 0" + System.lineSeparator()
        + "Total Questions Hard In Bank: 100" + System.lineSeparator()
        + "Total Questions Easy In Bank: 0" + System.lineSeparator();

    this.stats.setTotalHard(100);
    this.stats.printStats();

    assertEquals(expectedStats, outContent.toString());
  }

  /**
   * Tests setting the total number of questions easy.
   */
  @Test
  void testSetTotalEasy() {
    String expectedStats = "Total # Questions Answered: 0" + System.lineSeparator()
        + "# Questions From Easy To Hard: 0" + System.lineSeparator()
        + "# Questions From Hard To Easy: 0" + System.lineSeparator()
        + "Total Questions Hard In Bank: 0" + System.lineSeparator()
        + "Total Questions Easy In Bank: 100" + System.lineSeparator();

    this.stats.setTotalEasy(100);
    this.stats.printStats();

    assertEquals(expectedStats, outContent.toString());
  }
}