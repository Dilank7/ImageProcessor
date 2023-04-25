package view;

import java.io.IOException;


/**
 * Interface for creating an image view.
 */
public interface IView {

  /**
   * represents the string sent after the user inputs showing that the image has changes.
   * @return the message to user that the image has changed
   */
  String toString();

  /**
   * represents the message we want to show after image has changed.
   * @param message represents the string added to appendable object
   * @throws IOException when the command is invalid
   */
  void renderMessage(String message) throws IOException;

}
