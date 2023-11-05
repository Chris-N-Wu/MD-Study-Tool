package cs3500.pa02;

import java.nio.file.Path;

/**
 * <b>Model</b>
 * Writer for the StudyGuide class.
 */
public class WriteStudyGuide extends AbstractWriter {
  /**
   * Writes information to files.
   */
  public void createFiles(Path file, String studyGuide, String spacedRepetition) {
    // Add .md to the end of the file path.
    Path pathStudyGuide = handleDuplicateFiles(file, ".md");
    Path pathSpacedRepetition = this.handleDuplicateFiles(file, ".sr");

    this.writeToFile(pathStudyGuide, studyGuide);
    this.writeToFile(pathSpacedRepetition, spacedRepetition);
  }
}
