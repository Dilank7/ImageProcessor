package view;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Arrays;

import javax.swing.JPanel;

import model.Image;

/**
 * class for creating histograms which extends JPanel.
 */
class Histograms extends JPanel {
  private Image loadedImage;
  private final int width;
  private int maxVal;
  private int[] redHist;
  private int[] greenHist;
  private int[] blueHist;
  private int[] intenseHist;
  private int redMax;
  private int greenMax;
  private int blueMax;
  private int intensityMax;
  private int maxHeight = 500;

  /**
   * initializes loaded image and width.
   * @param loadedImage the image loaded
   * @param width the width
   */
  protected Histograms(Image loadedImage, int width) {
    this.loadedImage = loadedImage;
    this.width = width;
    if (loadedImage != null) {
      this.redHist = Image.imageToHistogram(loadedImage, "red");
      this.greenHist = Image.imageToHistogram(loadedImage, "green");
      this.blueHist = Image.imageToHistogram(loadedImage, "blue");
      this.intenseHist = Image.imageToHistogram(loadedImage, "intensity");

      this.redMax = Arrays.stream(redHist).max().getAsInt();
      this.greenMax = Arrays.stream(greenHist).max().getAsInt();
      this.blueMax = Arrays.stream(blueHist).max().getAsInt();
      this.intensityMax = Arrays.stream(intenseHist).max().getAsInt();


      this.maxVal = Math.max((Arrays.stream(intenseHist).max()).getAsInt(),
              Math.max((Arrays.stream(blueHist).max()).getAsInt(),
                      Math.max((Arrays.stream(redHist).max()).getAsInt(),
                              (Arrays.stream(greenHist).max()).getAsInt())));
    }
  }

  /**
   * replaces the image with a new imputed image.
   * @param newImage a new image
   */
  public void replaceImage(Image newImage) {
    this.loadedImage = newImage;
    if (newImage != null) {
      this.redHist = Image.imageToHistogram(newImage, "red");
      this.greenHist = Image.imageToHistogram(newImage, "green");
      this.blueHist = Image.imageToHistogram(newImage, "blue");
      this.intenseHist = Image.imageToHistogram(newImage, "intensity");

      this.redMax = Arrays.stream(redHist).max().getAsInt();
      this.greenMax = Arrays.stream(greenHist).max().getAsInt();
      this.blueMax = Arrays.stream(blueHist).max().getAsInt();
      this.intensityMax = Arrays.stream(intenseHist).max().getAsInt();

      this.maxVal = Math.max((Arrays.stream(intenseHist).max()).getAsInt(),
              Math.max((Arrays.stream(blueHist).max()).getAsInt(),
                      Math.max((Arrays.stream(redHist).max()).getAsInt(),
                              (Arrays.stream(greenHist).max()).getAsInt())));
    }
  }


  /**
   * class the draws the histogram.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Color red = new Color(255, 0, 0, 75);
    Color green = new Color(0, 255, 0, 75);
    Color blue = new Color(0, 0, 255, 75);
    Color grey = new Color(100, 100, 100, 75);

    if (loadedImage != null) {
      int histWidth = this.width / this.loadedImage.getMaxValue();
      int height;

      for (int x = 0; x < intenseHist.length; x++) {

        height = redHist[x] * maxHeight / redMax;
        g.setColor(red);
        g.fillRect(x * histWidth, maxHeight - height, histWidth, height);
        if ((redHist[x] * maxHeight / maxVal) >= maxHeight) {
          System.out.println(x + " " + x * histWidth + " " + (maxHeight - height)
                  +  " " + histWidth + " " + height + " " + new Color(255, 0, 0, 75));
        }

        height = greenHist[x] * maxHeight / greenMax;
        g.setColor(green);
        g.fillRect(x * histWidth, maxHeight - height, histWidth, height);
        if ((greenHist[x] * maxHeight / maxVal) >= maxHeight) {
          System.out.println(x + " " + x * histWidth + " " + (maxHeight - height)
                  +  " " + histWidth + " " + height + " " + new Color(255, 0, 0, 75));
        }

        height = blueHist[x] * maxHeight / blueMax;
        g.setColor(blue);
        g.fillRect(x * histWidth, maxHeight - height, histWidth, height);
        if ((blueHist[x] * maxHeight / maxVal) >= maxHeight) {
          System.out.println(x + " " + x * histWidth + " " + (maxHeight - height)
                  +  " " + histWidth + " " + height + " " + new Color(255, 0, 0, 75));
        }

        height = intenseHist[x] * maxHeight / intensityMax;
        g.setColor(grey);
        g.fillRect(x * histWidth, maxHeight - height, histWidth, height);
        if ((intenseHist[x] * maxHeight / maxVal) >= maxHeight) {
          System.out.println(x + " " + x * histWidth + " " + (maxHeight - height)
                  +  " " + histWidth + " " + height + " " + new Color(255, 0, 0, 75));
        }

      }
    }
  }
}