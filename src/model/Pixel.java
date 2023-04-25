package model;

/**
 * represents a pixel on the image.
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * initializes the params below.
   * @param red set to red
   * @param green set to green
   * @param blue set to blue
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * checks if two pixels are equal.
   * @param o object pixel
   * @return boolean if the pixels are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    Pixel that = (Pixel) o;
    return (this.red == that.red && this.blue == that.blue && this.green == that.green);
  }

  /**
   * creates a hashcode for a pixel.
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return 67 * red + 45 * blue + 9 * green;
  }

  /**
   * gets the blue value.
   * @return blue
   */
  public int getBlue() {
    return blue;
  }

  /**
   * gets the red value.
   * @return red
   */
  public int getRed() {
    return red;
  }

  /**
   * gets the green value.
   * @return green
   */
  public int getGreen() {
    return green;
  }
}
