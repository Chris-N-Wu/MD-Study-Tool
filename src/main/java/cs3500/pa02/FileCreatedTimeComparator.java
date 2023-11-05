package cs3500.pa02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;

/**
 * Implements the comparison of files based on creation time
 */
public class FileCreatedTimeComparator implements Comparator<Path> {

  /**
   * Compares the creation time of two paths (files)
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return less than 0 if file 1 is created before file 2,
   *                =0 if file 1 is created at the same time as file 2,
   *                >0 if file 1 is created after file 2
   */
  @Override
  public int compare(Path o1, Path o2) {
    BasicFileAttributes attrs1;
    BasicFileAttributes attrs2;

    // attempts to read the attributes of both files
    try {
      attrs1 = Files.readAttributes(o1, BasicFileAttributes.class);
      attrs2 = Files.readAttributes(o2, BasicFileAttributes.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("File Does Not Exist");
    }

    // retrieves the creation time of both files
    FileTime file1 = attrs1.creationTime();
    FileTime file2 = attrs2.creationTime();

    return file1.compareTo(file2);
  }
}
