package cs3500.pa02;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads a Spaced Repetition (.sr) file and translates it into a set of {@link Question}.
 */
public class ReadSpacedRepetition extends AbstractReader {
  private final ArrayList<Question> questions;

  /**
   * Constructor for ReadSpacedRepetition
   *
   * @param path Path to file where the Spaced Repetition (.sr) file is stored
   */
  ReadSpacedRepetition(Path path) {
    // Ensure that a .sr file has been provided
    String filename = path.toString();
    if (!filename.endsWith(".sr")) {
      throw new IllegalArgumentException("File is not an SR file");
    }

    this.questions = new ArrayList<>();

    this.readFile(path.toFile());
  }


  /**
   * Retrieves questions
   *
   * @return an ArrayList containing {@link Question} from a .sr file
   */
  public ArrayList<Question> getQuestions() {
    return questions;
  }

  /**
   * Reads questions from a given .sr file. .sr files should not be manually created,
   * but generated through {@link ReadStudyGuide}.
   */
  public void readFile(File file) {
    // Initialize a Scanner to read the file
    Scanner sc = this.getScannerFile(file);

    while (sc.hasNextLine()) {
      // We can do this because we are assuming that we are given a properly formatted .sr file
      // .sr files should not be manually created, but generated through ReadStudyGuide
      try {
        String difficulty = sc.nextLine();
        if (difficulty.equals("EASY")) {
          questions.add(new Question(Difficulty.EASY, sc.nextLine(), sc.nextLine()));
        } else if (difficulty.equals("HARD")) {
          questions.add(new Question(Difficulty.HARD, sc.nextLine(), sc.nextLine()));
        }

      } catch (IllegalStateException e) {
        throw new IllegalStateException("Error: .sr file is improperly formatted");
      }
    }
  }
}
