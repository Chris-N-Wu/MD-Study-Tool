package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests both the WriteStudyGuide class and the abstract class it extends
 */
class WriteStudyGuideTest {
  WriteStudyGuide wsg;
  Scanner scActual;
  Scanner scTest;


  @BeforeEach
  void setUp() {
    wsg = new WriteStudyGuide();
  }


  /**
   * Tests the create file method.
   *
   * @throws FileNotFoundException If invalid path is given. This should not occur.
   */
  @Test
  void testCreateFileContent() throws FileNotFoundException {
    /*
    Testing results of known complex file (with edge cases)
     */
    // path to ONE file we want to test (multi file testing is done in tests above)
    Path pathToComplex = Path.of("./src/test/resources/samplefiles/setTwo/complexCases.md");

    // Generates our importantContent file from an input
    ArrayList<Path> pathsComplex = new ArrayList<>(List.of(pathToComplex));
    ReadStudyGuide rsg = new ReadStudyGuide();
    rsg.readAllFiles(pathsComplex);
    String importantContent = rsg.getImportantContents();
    String importantQa = rsg.getImportantQuestionAnswer();
    wsg.createFiles(Path.of("./src/test/resources/results/complexTest"), importantContent,
        importantQa);

    // Create scanners for both the created test and expected results file- study guide (.md)
    Path pathComplexMdTest = Path.of("./src/test/resources/results/complexTest.md");
    Path pathExpectedMdComplex = Path.of("./src/test/resources/results/expectedComplex.md");
    scTest = new Scanner(new FileInputStream(pathComplexMdTest.toFile()));
    scActual = new Scanner(new FileInputStream(pathExpectedMdComplex.toFile()));

    // checking that each line in the produced file is the same as the expected
    while (scActual.hasNext()) {
      assertEquals(scActual.next(), scTest.next());
    }

    // Create scanners for both the created test and expected results file- spaced repetition (.sr)
    Path pathComplexSrTest = Path.of("./src/test/resources/results/complexTest.sr");
    Path pathExpectedSrComplex = Path.of("./src/test/resources/results/expectedComplex.sr");
    scTest = new Scanner(new FileInputStream(pathComplexSrTest.toFile()));
    scActual = new Scanner(new FileInputStream(pathExpectedSrComplex.toFile()));

    // checking that each line in the produced file is the same as the expected
    while (scActual.hasNext()) {
      assertEquals(scActual.next(), scTest.next());
    }

    // Preventing memory leakage.
    scTest.close();
    scActual.close();
  }


  /**
   * Testing with a file that already exists.
   */
  @Test
  void testWriteStudyGuide() {
    Path pathToExistingFile = Path.of("./src/test/resources/results/expectedFileExists");
    Path pathToNewFile = Path.of("./src/test/resources/results/expectedFileExists (1).md");
    wsg.createFiles(pathToExistingFile, "test", "");
    // Because the file already exists, tests that an incrementing number is added
    assertTrue(pathToNewFile.toFile().exists());
  }

  /**
   * Testing the handling of duplicate file names.
   */
  @Test
  void testHandleDuplicateFiles() throws IOException {
    File file1 = new File("./src/test/resources/results/myTempFile.md");
    File file2 = new File("./src/test/resources/results/myTempFile (1).md");


    // Creates temporary files to be deleted at end of running
    if (!(file1.createNewFile() && file2.createNewFile())) {
      throw new IOException("Files Were Not Created");
    }

    // Attempts to make a file with the original path
    Path outputPathActual =
        wsg.handleDuplicateFiles(Path.of("./src/test/resources/results/myTempFile"), ".md");
    Path outputPathExpected = Path.of("./src/test/resources/results/myTempFile (2).md");
    assertEquals(outputPathExpected, outputPathActual);

    file1.deleteOnExit();
    file2.deleteOnExit();
  }

  /**
   * Tests that writing to file works
   *
   * @throws FileNotFoundException if scanner cannot fine the specified file.
   */
  @Test
  void testWriteToFile() throws FileNotFoundException {
    Path path = Path.of("./src/test/resources/results/myTempFile.txt");
    wsg.writeToFile(path, "some boring stuff");
    Scanner sc = new Scanner(path.toFile());

    assertEquals("some boring stuff", sc.nextLine());

    // cleanup
    sc.close();
    path.toFile().deleteOnExit();
  }

}