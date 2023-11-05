package cs3500.pa02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Abstract class for Writers
 */
public abstract class AbstractWriter {
  /**
   * Prevents file writer from overwriting files that exist with the same file name by adding an
   * incrementing number to the end of the file's name.
   *
   * @param file      The path to the file to be written,
   * @param extension The extension of the file.
   * @return A path representing the new filename. Returns the same filename if the name is not
   *        already in use.
   */
  public Path handleDuplicateFiles(Path file, String extension) {
    Path path = Path.of(file + extension);

    // checks if the file already exists
    // If the file already exists, appends an incrementing number to end of file name
    int num = 1;
    while (path.toFile().exists()) {
      path = Path.of(file + " (" + num + ")" + extension);
      num++;
    }

    return path;
  }

  /**
   * Creates a new file and writes to it with the provided String data.
   *
   * @param path The path to the location of which the data should be written to.
   * @param data The data to be converted into a file.
   */
  public void writeToFile(Path path, String data) {
    // Convert String to data for writing ("raw" byte data)
    byte[] byteData = data.getBytes();

    // The path may not exist, or we may not have permissions to write to it,
    // in which case we need to handle that error (hence try-catch)
    try {
      // Built-in convenience method for writing data to a file. Markdown is really just plain text
      // with some special syntax, so you can add `.md` to the file-path to write a Markdown file.
      if (data.length() != 0) {
        Files.write(path, byteData);
      }
    } catch (IOException e) {
      throw new RuntimeException("Error: Invalid Output Path");
    }
  }
}
