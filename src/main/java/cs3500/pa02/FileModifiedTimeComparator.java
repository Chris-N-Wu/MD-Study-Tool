package cs3500.pa02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;

/**
 * Implements the comparison of files based on last modified time order
 */
public class FileModifiedTimeComparator implements Comparator<Path> {

  /**
   * Compares the last modified times of two paths (files)
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return less than 0 if file 1 was last modified before file 2
   *                =0 if file 1 was last modified at the same time as file 2
   *                >0 if file 1 was last modified after file 2
   */
  @Override
  public int compare(Path o1, Path o2) {
    BasicFileAttributes attrs1;
    BasicFileAttributes attrs2;

    // tries to read the attributes of both files
    try {
      attrs1 = Files.readAttributes(o1, BasicFileAttributes.class);
      attrs2 = Files.readAttributes(o2, BasicFileAttributes.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("File Does Not Exist");
    }

    // accesses the last modified time of both files
    FileTime file1 = attrs1.lastModifiedTime();
    FileTime file2 = attrs2.lastModifiedTime();

    return file1.compareTo(file2);
  }
}
