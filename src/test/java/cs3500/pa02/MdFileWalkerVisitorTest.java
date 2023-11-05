package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Testing some parts of the MdFileWalkerClass
 */
class MdFileWalkerVisitorTest {

  /**
   * Tests that this class collects the correct files.
   *
   * @throws IOException handles exception from walkFileTree
   */
  @Test
  void testWalker() throws IOException {
    MdFileWalkerVisitor md = new MdFileWalkerVisitor();
    Path path = Path.of("./src/test/resources/samplefiles/");
    Files.walkFileTree(path, md);

    ArrayList<Path> allMd = md.getAllMdFiles();
    allMd.sort(new FileNameComparator());

    Path arrays = Path.of("./src/test/resources/samplefiles/setGiven/arrays.md");
    Path vectors = Path.of("./src/test/resources/samplefiles/setGiven/vectors.md");
    Path headers = Path.of("./src/test/resources/samplefiles/setOne/headers.md");
    Path importantTag = Path.of("./src/test/resources/samplefiles/setOne/importantTag.md");
    Path mlit = Path.of("./src/test/resources/samplefiles/setOne/multiLineImportantTag.md");
    Path complex = Path.of("./src/test/resources/samplefiles/setTwo/complexCases.md");
    Path simpImportant = Path.of("./src/test/resources/samplefiles/setThree/simpleImportant.md");
    Path simpQa = Path.of("./src/test/resources/samplefiles/setThree/simpleQA.md");

    ArrayList<Path> expectedMd =
        new ArrayList<>(
            Arrays.asList(arrays, complex, headers, importantTag, mlit, simpImportant, simpQa,
                vectors));

    // testing that the gathered # of files is correct
    assertEquals(8, allMd.size());
    assertArrayEquals(expectedMd.toArray(), allMd.toArray());
  }
}