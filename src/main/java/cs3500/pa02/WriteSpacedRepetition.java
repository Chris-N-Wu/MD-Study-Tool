package cs3500.pa02;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * <b>Model</b>
 * Writes {@link Question} to a spaced repetition file.
 */
public class WriteSpacedRepetition extends AbstractWriter {
  private final Path path;

  /**
   * Constructor
   *
   * @param path Path to the output file we are writing to.
   */
  WriteSpacedRepetition(Path path) {
    this.path = path;
  }

  /**
   * Writes questions into a formatted .sr file
   *
   * @param questions The input to write to file.
   */
  public void writeData(ArrayList<Question> questions) {
    String sr = this.parseData(questions);
    this.writeToFile(path, sr);
  }

  /**
   * Parses a question and formats a string that will be written to a .sr file
   *
   * @param questions {@link Question} to be translated ot file.
   * @return A string of properly formatted information.
   */
  private String parseData(ArrayList<Question> questions) {
    StringBuilder spacedRepetition = new StringBuilder();
    for (Question question : questions) {
      String difficulty = switch (question.getDifficulty()) {
        case EASY -> "EASY";
        case HARD -> "HARD";
      };

      spacedRepetition
          .append(difficulty)
          .append("\n")
          .append(question.getQuestion())
          .append("\n")
          .append(question.getAnswer())
          .append("\n\n");
    }

    return spacedRepetition.toString();
  }
}
