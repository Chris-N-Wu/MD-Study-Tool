package cs3500.pa02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Abstract class for Readers
 */
public abstract class AbstractReader {
  public abstract void readFile(File file);

  /**
   * If a string has a leading hyphen, removes it.
   *
   * @param line The line which to remove leading a hyphen.
   * @return The string without a leading hyphen.
   */
  public String stripLeadingHyphen(String line) {
    if (line.stripLeading().charAt(0) == '-') {
      return line.stripLeading().substring(1).stripLeading();
    } else {
      return line.stripLeading();
    }
  }

  /**
   * If a string has opening square brackets "[[", removes them.
   *
   * @param line The line which to remove opening brackets from.
   * @return The string without the opening brackets
   */
  public String stripLeadingBrackets(String line) {
    if (line.stripLeading().startsWith("[[")) {
      return line.stripLeading().substring(2).stripLeading();
    } else {
      return line.stripLeading();
    }
  }

  /**
   * If a string has closing square brackets "]]", removes them.
   *
   * @param line The line which to remove closing brackets from.
   * @param i    The point in the line to stop searching.
   * @return The string without the following brackets.
   */
  public String stripFollowingBrackets(String line, int i) {
    if (line.substring(0, i).endsWith("]]")) {
      return line.substring(0, i - 2);
    } else {
      return line.substring(0, i);
    }
  }

  /**
   * Checks that the path to an object exists. Throws an exception otherwise.
   *
   * @param file The file to crawl through
   * @return A scanner object on the given file
   */
  public Scanner getScannerFile(File file) {
    // The file may not exist, in which case we need to handle that error (hence try-catch)
    try {
      return new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Error: File Does Not Exist");
    }
  }

}
