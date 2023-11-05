package cs3500.pa02;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Implementation of FileWalker interface
 */
public class MdFileWalkerVisitor implements FileVisitor<Path> {
  ArrayList<Path> allMdFiles = new ArrayList<>();

  /**
   * Retrieves markdown files in Path
   *
   * @return ArrayList of paths to only Markdown files
   */
  public ArrayList<Path> getAllMdFiles() {
    return allMdFiles;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    return CONTINUE;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    String fileName = file.getFileName().toString();
    // checks that file is a markdown file
    if (fileName.endsWith(".md")) {
      allMdFiles.add(file);
    }
    return CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    return CONTINUE;
  }

  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    return CONTINUE;
  }
}
