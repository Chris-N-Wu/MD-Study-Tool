package cs3500.pa02;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * <b>View</b>
 * Class with methods to display information to a user
 */
public class Display {
  private final Scanner sc;

  /**
   * Constructor
   *
   * @param sc Scanner object to be used to read files.
   */
  Display(Scanner sc) {
    this.sc = sc;
  }

  public void printWelcome() {
    System.out.println("Welcome. Beginning Study Session...");
  }

  /**
   * Prints out the number of questions selected to be practiced this session.
   *
   * @param questionNum The number of questions the user wants to practice.
   * @param error       Represents whether a user inputted a number greater than the total number of
   *                    questions. False represents that a user inputted greater than the total
   *                    number of questions. True otherwise.
   */
  public void printQuestionNum(int questionNum, boolean error) {
    if (error) {  // if the user inputted a # greater than the total number of questions.
      System.out.println("Total Desired Questions Exceeds Total Number of Questions.");
      System.out.println("Continuing with: " + questionNum + " Questions");
    } else {  // if the user inputted a # within the limit
      System.out.println("Practicing " + questionNum + " Questions");
    }
  }

  /**
   * Prints a formatted question statement with its order (number) and difficulty.
   *
   * @param curr     The current question number that the study session is on.
   * @param question The question to be printed.
   */
  public void printQuestion(int curr, Question question) {
    System.out.println(curr + ". " + question.getDifficulty() + ": " + question.getQuestion());
  }

  /**
   * Prints a formatted line including a question's answer.
   *
   * @param question The current question the study session is on.
   */
  public void printAnswer(Question question) {
    System.out.println("Answer: " + question.getAnswer());
  }

  /**
   * Prompts the user for a path to a spaced repetition file, .sr.
   *
   * @return The inputted path.
   */
  public Path promptSrPath() {
    // continues prompting the user until a valid input is provided.
    while (true) {
      System.out.println("Enter Path To Spaced Repetition File: ");

      try {
        Path pathToSr = Path.of(sc.nextLine());

        // Ensures path is correct.
        if (pathToSr.getFileName().toString().endsWith(".sr")
            && pathToSr.toFile().exists()) {
          return pathToSr;
        } else {
          // If file does not exist, prompts user to re-enter path
          System.out.println("Invalid File. Please Re-Enter.");
        }

      } catch (InvalidPathException e) {
        // If path is invalid, prompts user to re-enter path
        System.out.println("Invalid Path. Please Re-Enter.");
      }
    }
  }

  /**
   * Prompts the user for the number of questions which they want to answer.
   *
   * @return The inputted number of questions.
   */
  public int promptNumQuestions() {
    // continues prompting the user until a valid response has been inputted.
    while (true) {
      System.out.println("How Many Questions Would You Like To Practice Today?: ");

      // only returns a number once a legal number has been input (an integer >= 0).
      try {
        int inputNumQuestions = Integer.parseInt(sc.nextLine());
        if (inputNumQuestions > 0) {
          return inputNumQuestions;
        } else {
          System.out.println("Cannot Practice Zero Or Fewer Questions");
        }
      } catch (NumberFormatException e) {
        // If the input is not an integer, prompts again for an integer.
        System.out.println("Provide an Integer. [0-9]+");
      }
    }
  }

  /**
   * Prompts the user to select an option with respect to a problem.
   *
   * @return An enumeration representing the possible question options.
   */
  public QuestionOptions promptQuestionOptions() {
    // continues prompting the user until a valid response has been inputted.
    while (true) {
      // Printing the list of options
      String options = "SELECT ONE:" + System.lineSeparator()
          + "(1) Set Difficulty" + System.lineSeparator()
          + "(2) Show Answer" + System.lineSeparator()
          + "(3) Proceed To Next" + System.lineSeparator()
          + "(4) End Session";
      System.out.println(options);

      try {
        int selectedOption = Integer.parseInt(sc.nextLine());
        // converts an inputted integer into an QuestionOptions enumeration
        if (selectedOption == 1) {
          return QuestionOptions.SETDIFFICULTY;
        } else if (selectedOption == 2) {
          return QuestionOptions.SHOWANSWER;
        } else if (selectedOption == 3) {
          return QuestionOptions.PROCEED;
        } else if (selectedOption == 4) {
          return QuestionOptions.END;
        } else {
          System.out.println("Invalid Selection. Re-Select.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Enter an Integer (1, 2, 3, or 4). Re-Select.");
      }
    }
  }

  /**
   * Prompts the user to set the difficulty of a question.
   *
   * @return An enumeration representing the difficulty of a question. Either EASY or HARD.
   */
  public Difficulty promptSetDifficulty() {
    // continues prompting the user until a valid response has been inputted.
    while (true) {
      System.out.println("SELECT ONE:" + System.lineSeparator()
          + "(1) EASY" + System.lineSeparator()
          + "(2) HARD");

      try {
        int selectedOption = Integer.parseInt(sc.nextLine());
        // Converts an integer input into a Difficulty enumeration. Either EASY or HARD.
        if (selectedOption == 1) {
          return Difficulty.EASY;
        } else if (selectedOption == 2) {
          return Difficulty.HARD;
        } else {
          System.out.println("Invalid Selection. Re-Select");
        }
      } catch (NumberFormatException e) {
        System.out.println("Enter an Integer (1 or 2) Re-Select.");
      }
    }
  }
}
