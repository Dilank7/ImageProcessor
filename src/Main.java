import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;

import control.ImageControllerImpl;
import view.View;
import view.ViewGUI;


/**
 * Main class to run the image editor with text.
 */
public class Main {

  /**
   * Function to run the image editor using the controller and user inputs.
   * @param args Are user inputs.
   */
  public static void main(String[] args) throws IOException {

    if (args.length >= 1) {
      Appendable appendable = System.out;
      View view = new View(appendable);
      ImageControllerImpl controller;
      if ("-text".equals(args[0])) {
        controller = new ImageControllerImpl(new InputStreamReader(System.in), view);
        controller.control();
      } else if ("-file".equals(args[0])) {
        String file = args[1];
        controller = new ImageControllerImpl(new FileReader(file), view);
        controller.control();
      } else {
        throw new IllegalStateException("Unexpected value: " + args[0]);
      }
    }

    if (args.length == 0) {
      ViewGUI.setDefaultLookAndFeelDecorated(false);
      ViewGUI frame = new ViewGUI();

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

      } catch (UnsupportedLookAndFeelException e) {
        System.out.println("Error");
      } catch (ClassNotFoundException e) {
        System.out.println("Error");
      } catch (InstantiationException e) {
        System.out.println("Error");
      } catch (IllegalAccessException e) {
        System.out.println("Error");
      } catch (Exception e) {
        System.out.println("Error");
      }
    }
  }
}

