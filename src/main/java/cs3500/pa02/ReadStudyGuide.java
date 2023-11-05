package cs3500.pa02;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * <b>Model</b>
 * Reads .md files.
 */
public class ReadStudyGuide extends AbstractReader {
  private boolean first = true;
  private final StringBuilder importantContents = new StringBuilder();
  private final StringBuilder importantQuestionAnswer = new StringBuilder();
  private final StringBuilder previousUnfinished = new StringBuilder();

  /**
   * Getter to access a private variable
   *
   * @return A string of the important contents in a .md file.
   */
  public String getImportantContents() {
    return importantContents.toString();
  }

  /**
   * Getter to access a private variable.
   *
   * @return A string of the question and answer blocks in a .md file.
   */
  public String getImportantQuestionAnswer() {
    return importantQuestionAnswer.toString();
  }

  /**
   * Cycles through each file and reads each content's to StringBuilders.
   *
   * @param inputFiles An ArrayList of file paths to .md files
   */
  public void readAllFiles(ArrayList<Path> inputFiles) {
    for (Path path : inputFiles) {
      this.readFile(path.toFile());
    }
  }

  /**
   * Reads a file, parses that string, and then write that content to a string
   *
   * @param file The input markdown file
   */
  public void readFile(File file) {
    // Initialize a Scanner to read the file
    Scanner sc = this.getScannerFile(file);

    // Use the Scanner to iterate through the file line-by-line and accumulate its
    // contents in a string
    StringBuilder contentStudyGuide = new StringBuilder();
    StringBuilder contentSpacedRepetition = new StringBuilder();

    while (sc.hasNextLine()) { // Check there is another unread line in the file
      StringBuilder importantPhrase = this.parseLine(sc.nextLine());
      if (importantPhrase.toString().contains(":::")) {
        int separator = importantPhrase.indexOf(":::");
        contentSpacedRepetition
            .append("HARD").append(System.lineSeparator())
            .append(this.stripLeadingHyphen(importantPhrase.substring(0, separator)))
            .append(System.lineSeparator())
            .append(
                importantPhrase.substring(separator + 3, importantPhrase.length()).stripLeading())
            .append(System.lineSeparator());
      } else {
        contentStudyGuide.append(importantPhrase); // Read the aforementioned line
      }
    }

    sc.close();

    importantContents.append(contentStudyGuide);
    importantQuestionAnswer.append(contentSpacedRepetition.toString().stripTrailing());
  }

  /**
   * Parses an individual line of text and returns only the text that conforms to the following:
   * 1. Is a header
   * 2. Is a marked as important (denoted as [[--]]).
   *
   * @param line the current working line
   * @return a StringBuilder object of the resulting line
   */
  private StringBuilder parseLine(String line) {
    Stack<Integer> importantIndexes = new Stack<>();
    StringBuilder important = new StringBuilder();

    for (int i = 0; i < line.length(); i += 1) {
      // handles headers
      if (line.startsWith("#")) {
        // handles the new line for headers at the start of a file
        if (first) {
          first = false;
          important.append(line).append(System.lineSeparator());
        } else {
          important.append(System.lineSeparator()).append(line).append(System.lineSeparator());
        }
        break;
      }

      // handles important phrases
      if (line.startsWith("[[", i)) {
        // handles the start of an important phrase/section
        importantIndexes.add(i);
        previousUnfinished.append(line.substring(i));
      } else if (line.startsWith("]]", i) && !importantIndexes.isEmpty()) {
        // identifies the closing of an important line
        handleSingleLine(line, i, important, importantIndexes);
      } else if (important.isEmpty() && !previousUnfinished.isEmpty()) {
        // identifies and appends important phrases that span multiple lines
        handleMultiLine(line, i, important);
      }
    }
    return important;
  }

  /**
   * Helper method for {@link #parseLine(String line) parseLine}. Identifies the closing of a
   * single important line and appends to StringBuilder object.
   *
   * @param line           The current line which the program is reading
   * @param i              The index of the current character which the program is reading
   * @param important      A string of important phrases
   * @param importantIndex Stores the index of the start of an important phrase
   */
  private void handleSingleLine(String line, int i, StringBuilder important,
                                Stack<Integer> importantIndex) {
    int pos = importantIndex.peek();

    // removes first (head)
    importantIndex.pop();

    int lengthOfPhrase = i - 1 - pos;
    int endingPosition = pos + 1 + lengthOfPhrase;

    // if multiple closing brackets, select rightmost in segment
    int j = i;
    while (j < line.length() - 2 && line.charAt(j + 2) == ']') {
      endingPosition++;
      j++;
    }

    // starting pos + 2 to remove extra space
    important.append("- ").append(line, pos + 2, endingPosition).append(System.lineSeparator());
    // resetting the previousUnfinished
    previousUnfinished.setLength(0);
  }

  /**
   * Helper method for {@link #parseLine(String line) parseLine}. identifies and appends important
   * phrases that span multiple lines
   *
   * @param line      The current line which the program is reading
   * @param i         The index of the current character which the program is reading
   * @param important A string of important phrases
   */
  private void handleMultiLine(String line, int i, StringBuilder important) {
    if (line.startsWith("]]", i)) {
      important.append("- ")
          .append(this.stripLeadingBrackets(previousUnfinished.toString()))
          .append(this.stripLeadingHyphen(this.stripFollowingBrackets(line, i)))
          .append(System.lineSeparator());

      previousUnfinished.setLength(0);
    } else if ((!(line.contains("[[") || line.contains("]]"))) && (i == line.length() - 2)) {
      previousUnfinished.append(this.stripLeadingHyphen(line));
    }
  }
}