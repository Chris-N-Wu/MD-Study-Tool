package cs3500.pa02;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <b>Model</b>
 * Represents a list of sorted questions based on difficulty
 */
public class QuestionSet {
  private final ArrayList<Question> unsortedQuestions;
  private final ArrayList<Question> easyQuestions;
  private final ArrayList<Question> hardQuestions;

  /**
   * Base constructor
   *
   * @param allQuestions Passed in ArrayList of questions
   */
  QuestionSet(ArrayList<Question> allQuestions) {
    this.unsortedQuestions = allQuestions;
    this.easyQuestions = new ArrayList<>();
    this.hardQuestions = new ArrayList<>();

    // sorts questions into buckets
    this.sortDifficulty();
    // randomize the order within each bucket
    this.randomizeOrder();
  }

  /**
   * Gets the completed list of questions.
   *
   * @return An ArrayList containing questions that are sorted first by {@link Difficulty#HARD}
   *        then {@link  Difficulty#EASY}
   */
  public ArrayList<Question> getQuestionList() {

    // Adding all questions to the queue
    // adding the easy questions first because Queues are First-In-First-Out
    ArrayList<Question> questionList = new ArrayList<>();
    questionList.addAll(hardQuestions);
    questionList.addAll(easyQuestions);

    // Generating a deep copy of a list of questions
    ArrayList<Question> questionListCopy = new ArrayList<>();
    for (Question question : questionList) {
      Question questionCopy =
          new Question(question.getDifficulty(), question.getQuestion(), question.getAnswer());

      questionListCopy.add(questionCopy);
    }

    // Returns a deep copy of questions
    return questionListCopy;
  }

  /**
   * Randomizes the order of both buckets of questions
   */
  private void randomizeOrder() {
    Collections.shuffle(easyQuestions);
    Collections.shuffle(hardQuestions);
  }

  /**
   * Sorts the questions into buckets based on difficulty. {@link Difficulty#HARD} and
   * {@link  Difficulty#EASY}
   */
  private void sortDifficulty() {
    for (Question question : this.unsortedQuestions) {
      switch (question.getDifficulty()) {
        case EASY -> easyQuestions.add(question);
        case HARD -> hardQuestions.add(question);
        default -> System.err.println("Error: Missing Difficulty case"); //todo: do we need this
      }
    }
  }
}
