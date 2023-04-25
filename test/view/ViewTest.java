package view;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * tests for view.
 */
public class ViewTest {

  @Test
  public void TestRenderMessage() {
    Appendable a = new StringBuilder();
    View view = new View(a);
    assertEquals(a.toString(), "");
    try {
      view.renderMessage("Hello There");
      assertEquals(a.toString(), "Executed: Hello There");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestNullAppendableView() {
    View view = new View(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestNullImageView() {
    Appendable a = new StringBuilder();
    View view = new View(null, a);
  }
}