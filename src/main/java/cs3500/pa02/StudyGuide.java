package cs3500.pa02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Handles checks for correct inputs and creates a study guide
 */
public class StudyGuide {
  private final Path inputPath;
  private final SortFlag sortFlag;
  private final Path outputPath;

  StudyGuide(String input, String sort, String output) {
    // handles errors for the input path
    try { // seeing if input path is acceptable
      this.inputPath = Path.of(input);
    } catch (InvalidPathException e) {
      throw new IllegalArgumentException("Error: Invalid Path to Input Files");
    }

    try { // seeing if sort flag is one of three (FILENAME, MODIFIED, or CREATION)
      sortFlag = Enum.valueOf(SortFlag.class, sort.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "Error: Sort Flag Must Either Be FILENAME, MODIFIED, or CREATION");
    }

    try { // seeing if output path is acceptable
      this.outputPath = Path.of(output);
    } catch (InvalidPathException e) {
      throw new IllegalArgumentException("Error: Invalid Path to Output File");
    }
  }

  /**
   * Method to create study guide. Uses sort flag to create study guide.
   */
  public void createStudyGuide() {
    MdFileWalkerVisitor md = new MdFileWalkerVisitor();

    // retrieves our files
    try {
      Files.walkFileTree(inputPath, md);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    // obtaining a list of markdown files in directory
    ArrayList<Path> allMd = md.getAllMdFiles();

    // handling sort flags
    switch (sortFlag) {
      case FILENAME -> allMd.sort(new FileNameComparator());
      case MODIFIED -> allMd.sort(new FileModifiedTimeComparator());
      case CREATION -> allMd.sort(new FileCreatedTimeComparator());
      default -> System.err.println("Error: Missing SortFlag case");
    }

    // creating our markdown study guide
    ReadStudyGuide read = new ReadStudyGuide();
    read.readAllFiles(allMd);
    WriteStudyGuide wsr = new WriteStudyGuide();
    wsr.createFiles(outputPath, read.getImportantContents(), read.getImportantQuestionAnswer());

  }
}
