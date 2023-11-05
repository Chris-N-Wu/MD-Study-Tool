package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests for the QuestionSet class
 */
class QuestionSetTest {

  @Test
  void getQuestionList() {
    Question question1 = new Question(Difficulty.EASY, "Question1", "Answer1");
    Question question2 = new Question(Difficulty.HARD, "Question2", "Answer2");

    ArrayList<Question> questions = new ArrayList<>(Arrays.asList(question1, question2));

    QuestionSet questionSet = new QuestionSet(questions);

    ArrayList<Question> expectedQuestions = new ArrayList<>(Arrays.asList(question2, question1));
    ArrayList<Question> returnedSet = questionSet.getQuestionList();

    // Checking that the HARD question has been placed before the EASY question
    // Checking that each field is the same as I have not overridden the equals method for Question
    assertEquals(expectedQuestions.get(0).getDifficulty(), returnedSet.get(0).getDifficulty());
    assertEquals(expectedQuestions.get(0).getQuestion(), returnedSet.get(0).getQuestion());
    assertEquals(expectedQuestions.get(0).getAnswer(), returnedSet.get(0).getAnswer());

    assertEquals(expectedQuestions.get(1).getDifficulty(), returnedSet.get(1).getDifficulty());
    assertEquals(expectedQuestions.get(1).getQuestion(), returnedSet.get(1).getQuestion());
    assertEquals(expectedQuestions.get(1).getAnswer(), returnedSet.get(1).getAnswer());
  }
}