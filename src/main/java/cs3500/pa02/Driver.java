package cs3500.pa02;


//TODO: Ask in office hours if we need constructors in UML diagrams

import java.util.Scanner;

/**
 * <b>Controller</b>
 * This is the main driver of this project
 */
public class Driver {
  /**
   * Project entry point.
   *
   * @param args - If three arguments: [0] is the origin path, [1] is the sort flag,
   *             [2] is the output path.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      // calls our spaced repetition class and then starts a study session
      SpacedRepetition sr = new SpacedRepetition(new Scanner(System.in));
      sr.startStudySession();
    } else if (args.length == 3) {
      // calls our study guide class and then creates a new study guide
      StudyGuide sg = new StudyGuide(args[0], args[1], args[2]);
      sg.createStudyGuide();
    } else {
      throw new IllegalArgumentException(
          "Exactly Zero (or One*) OR Three Input Arguments Required");
    }

  }
}