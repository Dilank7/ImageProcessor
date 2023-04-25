package control;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import view.View;

import static org.junit.Assert.assertEquals;

/**
 * tests for image controller implementation.
 */
public class ImageControllerImplTest {

  @Test
  public void TestBrightenDarkenController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\nbrighten 10 happy happybrighter" +
            "\ndarken 10 happybrighter happydarker");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: Created new image: happybrighter using function brighten on happy\n" +
            "Executed: Created new image: happydarker using function darken on happybrighter\n");
  }

  @Test
  public void TestFlipController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\nhorizontal-flip happy happyflip\nvertical-flip happyflip happydiagonal");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: Created new image happyflip using function horizontal-flip on happy\n" +
            "Executed: Created new image happydiagonal " +
            "using function vertical-flip on happyflip\n");
  }

  @Test
  public void TestComponentsController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\nluma-component happy happyluma" +
            "\nintensity-component happyluma happyintenseluma" +
            "\nvalue-component happyintenseluma happyintensevalueluma");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: Created new image happyluma using function luma-component on happy\n" +
            "Executed: Created new image " +
            "happyintenseluma using function intensity-component on happyluma\n" +
            "Executed: Created new image " +
            "happyintensevalueluma using function value-component on happyintenseluma\n");
  }

  @Test
  public void TestColorComponentsController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\nred-component happy happyred" +
            "\ngreen-component happyred happyredgreen" +
            "\nblue-component happyredgreen happyredgreenblue");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: Created new image happyred using function red-component on happy\n" +
            "Executed: Created new image happyredgreen " +
            "using function green-component on happyred\n" +
            "Executed: Created new image happyredgreenblue " +
            "using function blue-component on happyredgreen\n");
  }

  @Test
  public void TestImagesNotExistController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\nred-component happyface happyred");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: File doesn't exist\n");
  }

  @Test
  public void TestFunctionsNotExistController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\npurple-component happyface happypurple" +
            "\ndiagonal-flip happy happyredgreen" +
            "\nsuper-component happy happyredgreenblue");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: Function undefined\n" +
            "Executed: Function undefined\n" +
            "Executed: Function undefined\n");
  }

  @Test
  public void TestLoadNotExistController() throws IOException {
    Readable r = new StringReader("load images/Sad.ppm sad");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: File not found in system try again\n");
  }

  @Test
  public void TestSaveNonExistentImageController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\nred-component happy happyred" +
            "\nsave images/superhappyimage.ppm superhappy");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: Created new image happyred using function red-component on happy\n" +
            "Executed: Can't save a file that doesn't exist\n");
  }

  @Test
  public void TestSaveNonExistentLocationController() throws IOException {
    Readable r = new StringReader("load images/Happy.ppm happy" +
            "\nred-component happy happyred" +
            "\nsave res/superhappyimage.ppm happyred");
    Appendable a = new StringBuilder();
    View view = new View(a);
    ImageControllerImpl editImage = new ImageControllerImpl(r, view);
    editImage.control();
    assertEquals(a.toString(), "Executed: Width of image: 64\n" +
            "Executed: Height of image: 64\n" +
            "Executed: Maximum value of a color in this file (usually 255): 255\n" +
            "Executed: Loaded images/Happy.ppm as: happy\n" +
            "Executed: Created new image happyred using function red-component on happy\n" +
            "Executed: Couldn't save image\n");
  }
}