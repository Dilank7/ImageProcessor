package view;

import java.io.IOException;

import model.Image;

/**
 * Creates a view of the image implementing the interface view.
 */
public class View implements IView {

  // the string shown to the user that will change
  private Appendable appendable;

  // represent the image
  private Image imageModel;

  /**
   * checks that the appendable string is not null.
   * @param appendable represents the string added to appendable object
   * @throws IOException if null
   */

  public View(Appendable appendable) throws IllegalArgumentException {
    if (appendable == null) {
      throw new IllegalArgumentException("cannot be null");
    } else {
      this.appendable = appendable;
    }
  }

  /**
   * checks if the image or appendable is null so the view can run.
   * @param imageModel the image in database
   * @param appendable the string that will be shown
   * @throws IllegalArgumentException if image or appendable is null
   */
  public View(Image imageModel, Appendable appendable) throws IllegalArgumentException {
    if (appendable == null || imageModel == null) {
      throw new IllegalArgumentException("cannot be null");
    } else {
      this.appendable = appendable;
    }
  }

  /**
   * is the string that will be shown after a user command has occurred.
   * @return string will let the user know the command has occurred
   */
  @Override
  public String toString() {
    StringBuilder answer = new StringBuilder();
    return answer.toString();
  }

  /**
   * The message for the command that has taken place.
   * @param message represents the string added to appendable object
   * @throws IOException if command is invalid
   */
  @Override
  public void renderMessage(String message) throws IOException {
    appendable.append(this + message);
  }
}
