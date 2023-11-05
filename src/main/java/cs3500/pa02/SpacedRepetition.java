package cs3500.pa02;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <b>Controller</b>
 * The primary point of interaction with a user of the Spaced Repetition study tool.
 */
public class SpacedRepetition {
  private final Scanner sc;
  private final Display display;
  private final SessionStatistics stats;
  private ArrayList<Question> questionList;
  private Path pathToSr;
  int totalDesiredQuestions;

  /**
   * Constructor for the Spaced Repetition study tool.
   */
  SpacedRepetition(Scanner sc) {
    this.sc = sc;
    this.display = new Display(sc);
    this.stats = new SessionStatistics();
  }

  /**
   * Begins a Study Session on the input .sr file from the user.
   */
  public void startStudySession() {
    this.initializeSession();

    cycleQuestions:
    for (int numReviewed = 0; numReviewed < totalDesiredQuestions; numReviewed++) {
      Question currentQuestion = questionList.get(numReviewed);
      this.stats.incrementAnswered();

      display.printQuestion(numReviewed + 1, currentQuestion);

      boolean stayOnQuestion = true;
      while (stayOnQuestion) {
        switch (display.promptQuestionOptions()) {
          case SETDIFFICULTY -> // Set Difficulty
              this.handleSetDifficulty(currentQuestion);
          case SHOWANSWER -> // Show Answer
              display.printAnswer(currentQuestion);
          case PROCEED -> // Proceed to Next
              stayOnQuestion = false;
          case END -> { // End Study Session
            System.out.println("END");
            break cycleQuestions;
          }
          default -> System.err.println("Error: Missing QuestionOption case");
        }
      }
    }
    // closing the scanner to prevent memory leak
    sc.close();

    this.concludeSession();
  }

  /**
   * Sets the beginning conditions for a Study Session.
   */
  private void initializeSession() {
    this.display.printWelcome();
    this.pathToSr = this.display.promptSrPath();
    ReadSpacedRepetition rsr = new ReadSpacedRepetition(pathToSr);
    QuestionSet questionSet = new QuestionSet(rsr.getQuestions());
    this.questionList = questionSet.getQuestionList();

    // Gathering user input for number of questions to practice
    this.totalDesiredQuestions = this.handleQuestionsDesiredInput(questionList.size());

  }

  /**
   * Closes out a Study Session. Prints session statistics and end message.
   */
  private void concludeSession() {
    int totalHard = 0;
    for (Question question : questionList) {
      if (question.getDifficulty().equals(Difficulty.HARD)) {
        totalHard++;
      }
    }

    this.stats.setTotalHard(totalHard);
    this.stats.setTotalEasy(questionList.size() - totalHard);

    // Prints the statistics board
    this.stats.printStats();

    // Re-writing the questions to the origin .sr file
    WriteSpacedRepetition wsr = new WriteSpacedRepetition(pathToSr);
    wsr.writeData(questionList);
  }

  /**
   * Takes an input from a user and sets that number as the total number of questions that the user
   * will be prompted on. If the inputted number is greater than the total number of questions in
   * the set, the latter number will be used.
   *
   * @param max The total number of questions in the set.
   * @return Either the max number of questions, or the user's input, whichever is lower.
   */
  private int handleQuestionsDesiredInput(int max) {
    int totalDesiredQuestions = display.promptNumQuestions();

    if (totalDesiredQuestions > max) {
      display.printQuestionNum(max, true);
      return max;
    } else {
      display.printQuestionNum(totalDesiredQuestions, false);
      return totalDesiredQuestions;
    }
  }

  /**
   * Handles the input from a user and translates that into a modifying the question's difficulty
   *
   * @param question The current prompted question.
   */
  private void handleSetDifficulty(Question question) {
    // Modifying a Question based on user input
    switch (display.promptSetDifficulty()) {
      case EASY -> {
        // changing stats if the question is not already hard
        if (question.getDifficulty().equals(Difficulty.HARD)) {
          this.stats.incrementHardToEasy();
        }
        question.setDifficulty(Difficulty.EASY);
      }
      case HARD -> {
        // changing stats if the question is not already easy
        if (question.getDifficulty().equals(Difficulty.EASY)) {
          this.stats.incrementEasyToHard();
        }
        question.setDifficulty(Difficulty.HARD);
      }
      default -> System.err.println("Error: Missing Difficulty case");
    }
  }

}



