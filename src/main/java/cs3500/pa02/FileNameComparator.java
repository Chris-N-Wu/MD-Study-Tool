package cs3500.pa02;

import java.nio.file.Path;
import java.util.Comparator;

/**
 * Implements the comparison of files based on lexicographic order
 */
public class FileNameComparator implements Comparator<Path> {

  /**
   * Compares two filenames as strings lexicographically.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return A number less than 0 if o1 comes before o2, greater than 0 if o1 comes after o2, and 0
   *                if they are identical.
   */
  @Override
  public int compare(Path o1, Path o2) {
    String file1Name;
    String file2Name;

    // retrieving the names of both files
    file1Name = o1.getFileName().toString();
    file2Name = o2.getFileName().toString();

    return file1Name.compareTo(file2Name);
  }
}
