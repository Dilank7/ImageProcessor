package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * represents the image characteristics.
 */
public class Image {
  private final Pixel[][] pixelArray;
  private final int width;
  private final int height;
  private final int maxValue;

  private static final int[][] sharpMatrix = {
          {-8, -8, -8, -8, -8},
          {-8, 4, 4, 4, -8},
          {6, 4, 1, 4, -8},
          {-8, 4, 4, 4, -8},
          {-8, -8, -8, -8, -8}
  };
  private static final int[][] blurMatrix = {
          {16, 8, 16},
          {8, 4, 8,},
          {16, 8, 16}
  };
  private static final double[][] sepiaMatrix = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168,},
          {0.272, 0.534, 0.131}
  };


  private static final double[][] greyScaleMatrix = {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722,},
          {0.2126, 0.7152, 0.0722}
  };

  /**
   * initializes the parameters of an image.
   *
   * @param pixelArray pixelArrays the image
   * @param width      the width of the image
   * @param height     the height of the image
   * @param maxValue   the max value a user can input
   */
  Image(Pixel[][] pixelArray, int width, int height, int maxValue) {
    this.pixelArray = pixelArray;
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
  }

  /**
   * Image func is our main function that directs user commands to do what we want them to do.
   *
   * @param image the image
   * @param func  the function entered
   * @param val   the value
   * @return the image
   * @throws IllegalArgumentException if any null
   */
  public static Image imageFunc(Image image, String func, int val) {
    Image newImage = new Image(new Pixel[image.width][image.height],
            image.width, image.height, image.maxValue);
    for (int y = 0; y < newImage.height; y++) {
      for (int x = 0; x < newImage.width; x++) {
        switch (func) {
          case "horizontal-flip":
            newImage.pixelArray[(newImage.width - 1) - x][y] = changeShadePixel(x, y, image, val);
            break;
          case "vertical-flip":
            newImage.pixelArray[x][(newImage.height - 1) - y] = changeShadePixel(x, y, image, val);
            break;
          case "value-component":
            newImage.pixelArray[x][y] = compPixel("value", image.getPixelAt(x, y));
            break;
          case "intensity-component":
            newImage.pixelArray[x][y] = compPixel("intensity", image.getPixelAt(x, y));
            break;
          case "luma-component":
            newImage.pixelArray[x][y] = compPixel("luma", image.getPixelAt(x, y));
            break;
          case "red-component":
            newImage.pixelArray[x][y] = compPixel("red", image.getPixelAt(x, y));
            break;
          case "green-component":
            newImage.pixelArray[x][y] = compPixel("green", image.getPixelAt(x, y));
            break;
          case "blue-component":
            newImage.pixelArray[x][y] = compPixel("blue", image.getPixelAt(x, y));
            break;
          case "brighten":
            newImage.pixelArray[x][y] = changeShadePixel(x, y, image, val);
            break;
          case "darken":
            newImage.pixelArray[x][y] = changeShadePixel(x, y, image, -val);
            break;
          case "blur":
            newImage.pixelArray[x][y] = filterPixel(x, y, image, blurMatrix);
            break;
          case "sharpen":
            newImage.pixelArray[x][y] = filterPixel(x, y, image, sharpMatrix);
            break;
          case "sepia":
            newImage.pixelArray[x][y] = transformPixel(x, y, image, sepiaMatrix);
            break;
          case "greyscale":
            newImage.pixelArray[x][y] = transformPixel(x, y, image, greyScaleMatrix);
            break;
          default:
            newImage.pixelArray[x][y] = image.getPixelAt(x, y);
            break;
        }
      }
    }
    return newImage;
  }

  /**
   * Takes an image and applies a downscale factor to the width and height.
   * @param image the image that is provided.
   * @param xFactor the amount the user would like to scale down the width of the image.
   * @param yFactor the amount the user would like to scale down the height of the image.
   * @return a new image with the new width and height.
   * @throws IllegalArgumentException if the width or height are invalid or null image is provided.
   */
  public static Image downscaleImage(Image image, double xFactor, double yFactor)
          throws IllegalArgumentException {
    if (xFactor > 1 || yFactor > 1 || xFactor <= 0 || yFactor <= 0 || image == null) {
      throw new IllegalArgumentException("Invalid Arguments");
    }

    Image ret = new Image(new Pixel[(int)
            (image.getWidth() * xFactor)][(int) (image.getHeight() * yFactor)],
            (int) (image.getWidth() * xFactor),
            (int) (image.getHeight() * yFactor), image.maxValue);

    for (int i = 0; i < ret.getWidth(); i++) {
      for (int j = 0; j < ret.getHeight(); j++) {
        double x = i / xFactor;
        double y = j / yFactor;

        Pixel pixelA = image.getPixelAt((int) x, (int) y);

        Pixel pixelB = image.getPixelAt((int) (Math.ceil(x) + 1
                >= image.getWidth() ? Math.ceil(x) : Math.ceil(x) + 1), (int) y);

        Pixel pixelC = image.getPixelAt((int) x, (int) (Math.ceil(y) + 1
                >= image.getHeight() ? Math.ceil(y) : Math.ceil(y) + 1));

        Pixel pixelD = image.getPixelAt((int) (Math.ceil(x) + 1 >= image.getWidth()
                        ? Math.ceil(x) : Math.ceil(x) + 1),
                (int) (Math.ceil(y) + 1 >= image.getHeight()
                        ? Math.ceil(y) : Math.ceil(y) + 1));

        double mRed = (pixelB.getRed() * (x - (int) x))
                + (pixelA.getRed() * ((Math.floor(x) + 1) - x));
        double nRed = (pixelD.getRed() * (x - (int) x))
                + (pixelC.getRed() * ((Math.floor(x) + 1) - x));

        double mGreen = (pixelB.getGreen() * (x - (int) x))
                + (pixelA.getGreen() * ((Math.floor(x) + 1) - x));
        double nGreen = (pixelD.getGreen() * (x - (int) x))
                + (pixelC.getGreen() * ((Math.floor(x) + 1) - x));

        double mBlue = (pixelB.getBlue() * (x - (int) x))
                + (pixelA.getBlue() * ((Math.floor(x) + 1) - x));
        double nBlue = (pixelD.getBlue() * (x - (int) x))
                + (pixelC.getBlue() * ((Math.floor(x) + 1) - x));

        int rRed = capVal(((int) (nRed * (y - ((int) y))
                + mRed * ((Math.floor(y) + 1) - y))), ret);
        int rGreen = capVal(((int) (nGreen * (y - ((int) y))
                + mGreen * ((Math.floor(y) + 1) - y))), ret);
        int rBlue = capVal(((int) (nBlue * (y - ((int) y)) +
                mBlue * ((Math.floor(y) + 1) - y))), ret);

        ret.pixelArray[i][j] = new Pixel(rRed, rGreen, rBlue);
      }
    }

    return ret;
  }

  /**
   * takes the black pixels from an image and applies the applied filter to those.
   * @param image the image that is provided.
   * @param maskImage the image with just the black pixels.
   * @param function the filter that is going to be applied.
   * @param val the value of that filter.
   * @return the masked image with the filter.
   * @throws IllegalArgumentException if any null.
   */
  public static Image maskImage(Image image, Image maskImage, String function, int val)
          throws IllegalArgumentException {
    if (image == null || maskImage.getWidth() != image.getWidth()
            || maskImage.getHeight() != image.getHeight() || val < 0) {
      throw new IllegalArgumentException("Invalid Arguments");
    }
    Image ret = new Image(new Pixel[image.getWidth()][image.getHeight()],
            image.getWidth(), image.getHeight(), image.getMaxValue());
    Pixel black = new Pixel(0, 0, 0);

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        if (maskImage.getPixelAt(x, y).equals(black)) {
          switch (function) {
            case "value-component":
              ret.pixelArray[x][y] = compPixel("value", image.getPixelAt(x, y));
              break;
            case "intensity-component":
              ret.pixelArray[x][y] = compPixel("intensity", image.getPixelAt(x, y));
              break;
            case "luma-component":
              ret.pixelArray[x][y] = compPixel("luma", image.getPixelAt(x, y));
              break;
            case "red-component":
              ret.pixelArray[x][y] = compPixel("red", image.getPixelAt(x, y));
              break;
            case "green-component":
              ret.pixelArray[x][y] = compPixel("green", image.getPixelAt(x, y));
              break;
            case "blue-component":
              ret.pixelArray[x][y] = compPixel("blue", image.getPixelAt(x, y));
              break;
            case "brighten":
              ret.pixelArray[x][y] = changeShadePixel(x, y, image, val);
              break;
            case "darken":
              ret.pixelArray[x][y] = changeShadePixel(x, y, image, -val);
              break;
            case "blur":
              ret.pixelArray[x][y] = filterPixel(x, y, image, blurMatrix);
              break;
            case "sharpen":
              ret.pixelArray[x][y] = filterPixel(x, y, image, sharpMatrix);
              break;
            case "sepia":
              ret.pixelArray[x][y] = transformPixel(x, y, image, sepiaMatrix);
              break;
            case "greyscale":
              ret.pixelArray[x][y] = transformPixel(x, y, image, greyScaleMatrix);
              break;
            default:
              ret.pixelArray[x][y] = image.getPixelAt(x, y);
              break;
          }
        } else {
          ret.pixelArray[x][y] = image.getPixelAt(x, y);
        }
      }
    }
    return ret;
  }

  /**
   * applies either the blur filter or sharpen pixel to a given pixel.
   *
   * @param x      the x position
   * @param y      the y position
   * @param image  the image given
   * @param kernel the kernel for sharpening or blurring.
   * @return a blurred or sharpened image.
   */
  private static Pixel filterPixel(int x, int y, Image image, int[][] kernel) {
    int rSum = 0;
    int gSum = 0;
    int bSum = 0;

    int c = kernel.length / 2;

    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel[0].length; j++) {
        int xVal = x - c + j;
        int yVal = y - c + i;
        if (xVal >= 0 && xVal < image.width && yVal >= 0 && yVal < image.height) {
          rSum += (image.getPixelAt(xVal, yVal).getRed()) / (kernel[i][j]);
          gSum += (image.getPixelAt(xVal, yVal).getGreen()) / (kernel[i][j]);
          bSum += (image.getPixelAt(xVal, yVal).getBlue()) / (kernel[i][j]);
        }
      }
    }

    return new Pixel(capVal(rSum, image), capVal(gSum, image), capVal(bSum, image));
  }

  /**
   * abstract version of brighten and darken pixel. Takes user inputs to either darken or
   * brighten a pixel by a certain degree.
   *
   * @param x     the x position
   * @param y     the y position
   * @param image the image given
   * @param val   the degree of change
   * @return a brightened or darkened image
   */
  private static Pixel changeShadePixel(int x, int y, Image image, int val) {
    return new Pixel(capVal(image.getPixelAt(x, y).getRed() + val, image),
            capVal(image.getPixelAt(x, y).getGreen() + val, image),
            capVal(image.getPixelAt(x, y).getBlue() + val, image));
  }

  /**
   * applies either the sepia transformation or the greyscale transformation to a given pixel.
   *
   * @param x              the x position
   * @param y              the y position
   * @param image          the image given
   * @param transformation the transformation wanted
   * @return a either sepia or greyscale transformation on image
   */
  private static Pixel transformPixel(int x, int y, Image image, double[][] transformation) {
    int rSum = (int) ((transformation[0][0] * image.getPixelAt(x, y).getRed())
            + (transformation[0][1] * image.getPixelAt(x, y).getGreen())
            + (transformation[0][2] * image.getPixelAt(x, y).getBlue()));
    int gSum = (int) ((transformation[1][0] * image.getPixelAt(x, y).getRed())
            + (transformation[1][1] * image.getPixelAt(x, y).getGreen())
            + (transformation[1][2] * image.getPixelAt(x, y).getBlue()));
    int bSum = (int) ((transformation[2][0] * image.getPixelAt(x, y).getRed())
            + (transformation[2][1] * image.getPixelAt(x, y).getGreen())
            + (transformation[2][2] * image.getPixelAt(x, y).getBlue()));

    return new Pixel(capVal(rSum, image), capVal(gSum, image), capVal(bSum, image));
  }

  /**
   * Comp pixel takes a given image and takes the component given by the user.
   *
   * @param comp  given component
   * @param pixel given pixel of image
   * @return returns the component of the image pixel
   */
  private static Pixel compPixel(String comp, Pixel pixel) {
    int n;
    switch (comp) {
      case "luma":
        n = (int) ((0.2126 * (pixel.getRed()))
                + (0.7152 * (pixel.getGreen())) + (0.0722 * (pixel.getBlue())));
        break;
      case "value":
        n = Math.max(pixel.getBlue(),
                Math.max(pixel.getRed(), pixel.getGreen()));
        break;
      case "intensity":
        n = (pixel.getBlue() + pixel.getRed() + pixel.getGreen()) / 3;
        break;
      case "red":
        n = pixel.getRed();
        break;
      case "green":
        n = pixel.getGreen();
        break;
      case "blue":
        n = pixel.getBlue();
        break;
      default:
        n = 0;
        break;
    }
    return new Pixel(n, n, n);
  }

  /**
   * Makes a given value either 0 if it’s less than 0 or caps it at maxValue if it is greater.
   *
   * @param val   the given value
   * @param image the given image
   * @return outputs the value if capped or not
   */
  private static int capVal(int val, Image image) {
    if (val < 0) {
      val = 0;
    }
    if (val > image.maxValue) {
      val = image.maxValue;
    }
    return val;
  }

  /**
   * Loads an image based off of a scanner.
   *
   * @param sc the scanner
   * @return the image using the scanner
   */
  public static Image loadPPMImage(Scanner sc) {
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Pixel[][] pixelArray = new Pixel[width][height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelArray[x][y] = new Pixel(r, g, b);
      }
    }

    Image image = new Image(pixelArray, width, height, maxValue);
    return image;
  }

  /**
   * Gets a copy of a pixel at a provided point used for tests.
   *
   * @param x The x coordinate of a pixel in an image
   * @param y The y coordinate of a pixel in an image
   * @return A copy of a pixel at the given coordinates
   * @throws IllegalArgumentException When the coordinates are out of the image bounds
   */
  public Pixel getPixelAt(int x, int y) throws IllegalArgumentException {
    if (x >= this.width || x < 0 || y >= this.height || y < 0) {
      throw new IllegalArgumentException("Coordinate " + x + ", " + y + " is not in image, " +
              "image width and height are: " + this.width + ", " + this.height);
    }
    Pixel copy = this.pixelArray[x][y];
    return copy;
  }

  /**
   * Creates an image from a buffered image provided by the java read function in our controller.
   *
   * @param image the image given
   * @return image from buffered image
   */
  public static Image loadStandardImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] pixelArray = new Pixel[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int a = image.getRGB(x, y);
        int r = (a >> 16) & 255;
        int g = (a >> 8) & 255;
        int b = (a) & 255;
        pixelArray[x][y] = new Pixel(r, g, b);
      }
    }
    return new Image(pixelArray, width, height, 255);
  }

  /**
   * It makes one images a buffered image which is a Java image for ImageIO.write.
   *
   * @param image the given image
   * @return a buffered version of given image
   */
  public static BufferedImage bufferImage(Image image) {
    BufferedImage ret = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < image.height; y++) {
      for (int x = 0; x < image.width; x++) {
        ret.setRGB(x, y,
                (new Color(image.getPixelAt(x, y).getRed(),
                        image.getPixelAt(x, y).getGreen(),
                        image.getPixelAt(x, y).getBlue()).getRGB()));
      }
    }

    return ret;
  }

  /**
   * saves the current image.
   *
   * @param image the current image.
   * @return the current image saved.
   */
  public static String toStringPPM(Image image) {
    StringBuilder ret = new StringBuilder();

    ret.append("P3\n# Created by Sonic 7 :)\n" + image.width
            + " " + image.height + "\n" + image.maxValue);

    for (int y = 0; y < image.height; y++) {
      for (int x = 0; x < image.width; x++) {
        ret.append(" " + image.getPixelAt(x, y).getRed());
        ret.append(" " + image.getPixelAt(x, y).getGreen());
        ret.append(" " + image.getPixelAt(x, y).getBlue());
      }
    }

    ret.append("\n");
    return ret.toString();
  }

  /**
   * gets the width.
   * @return the width
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * gets the height.
   * @return the height.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * gets the max value.
   * @return the max value.
   */
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * takes the values of an image and converts them into a histogram which changes when different
   * functions are applied to the image.
   * @param image represents the image given
   * @param func represents the function given
   * @return a histogram representation of the image
   */
  public static int[] imageToHistogram(Image image, String func) {
    int[] ret = new int[256];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        if ("red".equals(func)) {
          ret[image.getPixelAt(x, y).getRed()]++;
        } else if ("green".equals(func)) {
          ret[image.getPixelAt(x, y).getGreen()]++;
        } else if ("blue".equals(func)) {
          ret[image.getPixelAt(x, y).getBlue()]++;
        } else if ("intensity".equals(func)) {
          ret[(image.getPixelAt(x, y).getRed() + image.getPixelAt(x, y).getGreen()
                  + image.getPixelAt(x, y).getBlue()) / 3]++;
        }
      }
    }

    return ret;
  }

  /**
   * checks if the object height, width, and max value is equals to the image values.
   * @param o the object image
   * @return returns boolean if image is equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    Image that = (Image) o;

    if (this.height != that.height || this.width != that.width || this.maxValue != that.maxValue) {
      return false;
    }

    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        if (!(this.pixelArray[x][y].equals(that.pixelArray[x][y]))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * hashcode for equals.
   * @return hashcode
   */
  @Override
  public int hashCode() {
    int res = 0;
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        res += this.getPixelAt(x, y).hashCode();
      }
    }

    res += this.width * 234 + this.height * 9876 + this.maxValue * 10000;
    return res;
  }

}