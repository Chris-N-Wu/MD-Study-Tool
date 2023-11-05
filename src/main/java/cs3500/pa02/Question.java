package cs3500.pa02;

/**
 * <b>Model</b>
 * Represents a singular question.
 */
public class Question {
  private Difficulty difficulty;
  private final String question;
  private final String answer;

  /**
   * Default constructor
   *
   * @param difficulty The difficulty of a question (EITHER EASY or HARD)
   * @param question   The question
   * @param answer     The answer
   */
  Question(Difficulty difficulty, String question, String answer) {
    this.difficulty = difficulty;
    this.question = question;
    this.answer = answer;
  }

  /**
   * Getter for Difficulty
   *
   * @return An enum representing the difficulty of the question
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  /**
   * Sets the {@link Difficulty} of this question
   *
   * @param difficulty The desired difficulty level.
   */
  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Getter for this question
   *
   * @return A string containing the question
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Getter for this question's answer
   *
   * @return A string containing the answer to this question
   */
  public String getAnswer() {
    return answer;
  }

}
