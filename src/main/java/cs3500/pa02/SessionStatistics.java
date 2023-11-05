package cs3500.pa02;

/**
 * Represents the question statistics for a Study Session.
 */
public class SessionStatistics {
  private int questionsAnswered;
  private int questionsEasyToHard;
  private int questionsHardToEasy;
  private int totalHard;
  private int totalEasy;

  /**
   * Constructor for the Session Statistics class.
   */
  SessionStatistics() {
    this.questionsAnswered = 0;
    this.questionsEasyToHard = 0;
    this.questionsHardToEasy = 0;
    this.totalHard = 0;
    this.totalEasy = 0;
  }

  /**
   * Prints the statistics for this study session.
   */
  public void printStats() {
    System.out.println("Total # Questions Answered: " + this.questionsAnswered);
    System.out.println("# Questions From Easy To Hard: " + this.questionsEasyToHard);
    System.out.println("# Questions From Hard To Easy: " + this.questionsHardToEasy);
    System.out.println("Total Questions Hard In Bank: " + this.totalHard);
    System.out.println("Total Questions Easy In Bank: " + this.totalEasy);
  }

  /**
   * Increments the number of questions answered.
   */
  public void incrementAnswered() {
    this.questionsAnswered++;
  }

  /**
   * Increments the number of questions changed from easy to hard.
   * If a question is changed from easy to hard then hard to easy then easy to hard, that will
   * double count.
   */
  public void incrementEasyToHard() {
    this.questionsEasyToHard++;
  }

  /**
   * Increments the number of questions changed from hard to easy.
   */
  public void incrementHardToEasy() {
    this.questionsHardToEasy++;
  }

  /**
   * Sets the total number of questions hard in this test set at the end of a study session.
   *
   * @param num The number of hard questions.
   */
  public void setTotalHard(int num) {
    this.totalHard = num;
  }

  /**
   * Sets the total number of questions easy in this test set at the end of a study session.
   *
   * @param num The number of easy questions.
   */
  public void setTotalEasy(int num) {
    this.totalEasy = num;
  }
}
