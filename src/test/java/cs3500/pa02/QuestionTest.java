package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Question class
 */
class QuestionTest {

  /**
   * Testing the getDifficulty method
   */
  @Test
  void getDifficulty() {
    Question questionHard = new Question(Difficulty.HARD, "Question", "Answer");
    Question questionEasy = new Question(Difficulty.EASY, "Question", "Answer");

    // Testing both questions for both difficulties.
    assertEquals(Difficulty.HARD, questionHard.getDifficulty());
    assertEquals(Difficulty.EASY, questionEasy.getDifficulty());
  }

  /**
   * Testing the setDifficulty method
   */
  @Test
  void setDifficulty() {
    Question questionHard = new Question(Difficulty.HARD, "Question", "Answer");
    Question questionEasy = new Question(Difficulty.EASY, "Question", "Answer");

    // Testing setting difficulties from HARD to EASY
    questionHard.setDifficulty(Difficulty.EASY);
    assertEquals(Difficulty.EASY, questionHard.getDifficulty());

    // Testing setting difficulties from EASY to HARD
    questionEasy.setDifficulty(Difficulty.HARD);
    assertEquals(Difficulty.HARD, questionEasy.getDifficulty());
  }

  /**
   * Testing the getQuestion method
   */
  @Test
  void getQuestion() {
    Question questionHard = new Question(Difficulty.HARD, "Question1", "Answer1");
    Question questionEasy = new Question(Difficulty.EASY, "Question2", "Answer2");

    assertEquals("Question1", questionHard.getQuestion());
    assertEquals("Question2", questionEasy.getQuestion());
  }

  /**
   * Testing the getAnswer method
   */
  @Test
  void getAnswer() {
    Question questionHard = new Question(Difficulty.HARD, "Question1", "Answer1");
    Question questionEasy = new Question(Difficulty.EASY, "Question2", "Answer2");

    assertEquals("Answer1", questionHard.getAnswer());
    assertEquals("Answer2", questionEasy.getAnswer());
  }
}