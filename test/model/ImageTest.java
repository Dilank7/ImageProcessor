package model;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

import static javax.imageio.ImageIO.read;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * tests for image.
 */
public class ImageTest {

  @Test
  public void TestBrightenFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleBrighter = Image.imageFunc(sample, "brighten", 10);

    Pixel pixelSample = sample.getPixelAt(0,0);
    Pixel pixelBrighter = sampleBrighter.getPixelAt(0,0);

    // A white pixel.
    assertEquals(pixelSample.getRed(), pixelBrighter.getRed());
    assertEquals(pixelSample.getGreen(), pixelBrighter.getGreen());
    assertEquals(pixelSample.getBlue(), pixelBrighter.getBlue());

    Pixel pixelSampleMiddle = sample.getPixelAt(32,8);

    Pixel pixelBrighterMiddle = sampleBrighter.getPixelAt(32,8);

    // Not a white pixel.
    assertEquals(pixelSampleMiddle.getRed(), pixelBrighterMiddle.getRed() - 10);
    assertEquals(pixelSampleMiddle.getGreen(), pixelBrighterMiddle.getGreen() - 10);
    assertEquals(pixelSampleMiddle.getBlue(), pixelBrighterMiddle.getBlue() - 10);
  }

  @Test
  public void TestDarkenFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleDarker = Image.imageFunc(sample, "darken", 10);

    Pixel pixelSample = sample.getPixelAt(31,60);
    Pixel pixelDarker = sampleDarker.getPixelAt(31,60);

    // A black pixel.
    assertEquals(pixelSample.getRed(), pixelDarker.getRed());
    assertEquals(pixelSample.getGreen(), pixelDarker.getGreen());
    assertEquals(pixelSample.getBlue(), pixelDarker.getBlue());

    Pixel pixelSampleMiddle = sample.getPixelAt(8,32);

    Pixel pixelDarkerMiddle = sampleDarker.getPixelAt(8,32);

    // Not a black pixel.
    assertEquals(pixelSampleMiddle.getRed(), pixelDarkerMiddle.getRed() + 1);
    assertEquals(pixelSampleMiddle.getGreen(), pixelDarkerMiddle.getGreen() + 10);
    assertEquals(pixelSampleMiddle.getBlue(), pixelDarkerMiddle.getBlue() + 10);
  }

  @Test
  public void TestHorizontalFlipFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleFlip = Image.imageFunc(sample, "horizontal-flip", 0);

    Pixel pixelSample = sample.getPixelAt(0,0);
    Pixel pixelFlip = sampleFlip.getPixelAt(63,0);

    assertEquals(pixelSample.getRed(), pixelFlip.getRed());
    assertEquals(pixelSample.getGreen(), pixelFlip.getGreen());
    assertEquals(pixelSample.getBlue(), pixelFlip.getBlue());

    Pixel pixelSampleMiddle = sample.getPixelAt(8,32);

    Pixel pixelFlipMiddle = sampleFlip.getPixelAt(8,32);

    assertNotEquals(pixelSampleMiddle.getRed(), pixelFlipMiddle.getRed());
    assertNotEquals(pixelSampleMiddle.getGreen(), pixelFlipMiddle.getGreen());
    assertNotEquals(pixelSampleMiddle.getBlue(), pixelFlipMiddle.getBlue());
  }

  @Test
  public void TestVerticalFlipFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleFlip = Image.imageFunc(sample, "vertical-flip", 0);

    Pixel pixelSample = sample.getPixelAt(0,0);
    Pixel pixelFlip = sampleFlip.getPixelAt(0,63);

    assertEquals(pixelSample.getRed(), pixelFlip.getRed());
    assertEquals(pixelSample.getGreen(), pixelFlip.getGreen());
    assertEquals(pixelSample.getBlue(), pixelFlip.getBlue());

    Pixel pixelSampleMiddle = sample.getPixelAt(8,5);

    Pixel pixelFlipMiddle = sampleFlip.getPixelAt(8,5);

    assertNotEquals(pixelSampleMiddle.getRed(), pixelFlipMiddle.getRed());
    assertNotEquals(pixelSampleMiddle.getGreen(), pixelFlipMiddle.getGreen());
    assertNotEquals(pixelSampleMiddle.getBlue(), pixelFlipMiddle.getBlue());
  }

  @Test
  public void TestValueCompFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleComp = Image.imageFunc(sample, "value-component", 0);

    Pixel pixelValue = sampleComp.getPixelAt(0,0);


    int e = Math.max(sample.getPixelAt(0,0).getBlue(),
            Math.max(sample.getPixelAt(0,0).getRed(), sample.getPixelAt(0,0).getGreen()));

    assertEquals(e, pixelValue.getRed());
    assertEquals(e, pixelValue.getGreen());
    assertEquals(e, pixelValue.getBlue());

    Pixel pixelCompMiddle = sampleComp.getPixelAt(32,32);
    Pixel pixelSampleMiddle = sample.getPixelAt(32,32);

    int m = Math.max(sample.getPixelAt(32,32).getBlue(),
            Math.max(sample.getPixelAt(32,32).getRed(), sample.getPixelAt(32,32).getGreen()));

    assertEquals(m, pixelCompMiddle.getRed());
    assertEquals(m, pixelCompMiddle.getGreen());
    assertEquals(m, pixelCompMiddle.getBlue());

    assertNotEquals(pixelSampleMiddle.getRed(), pixelCompMiddle.getRed());
    assertNotEquals(pixelSampleMiddle.getGreen(), pixelCompMiddle.getGreen());
    assertEquals(pixelSampleMiddle.getBlue(), pixelCompMiddle.getBlue());
  }

  @Test
  public void TestIntensityCompFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleComp = Image.imageFunc(sample, "intensity-component", 0);

    Pixel pixelComp = sampleComp.getPixelAt(0,0);


    int e = (sample.getPixelAt(0,0).getBlue()
            + sample.getPixelAt(0,0).getRed() + sample.getPixelAt(0,0).getGreen()) / 3;

    assertEquals(e, pixelComp.getRed());
    assertEquals(e, pixelComp.getGreen());
    assertEquals(e, pixelComp.getBlue());

    Pixel pixelCompMiddle = sampleComp.getPixelAt(32,32);

    int m = (sample.getPixelAt(32,32).getBlue()
            + sample.getPixelAt(32,32).getRed() + sample.getPixelAt(32,32).getGreen()) / 3;

    assertEquals(m, pixelCompMiddle.getRed());
    assertEquals(m, pixelCompMiddle.getGreen());
    assertEquals(m, pixelCompMiddle.getBlue());
  }

  @Test
  public void TestLumaCompFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleComp = Image.imageFunc(sample, "luma-component", 0);

    Pixel pixelComp = sampleComp.getPixelAt(0,0);


    int e = (int) ((0.2126 * (sample.getPixelAt(0,0).getRed()))
            + (0.7152 * (sample.getPixelAt(0,0).getGreen()))
            + (0.0722 * (sample.getPixelAt(0,0).getBlue())));


    assertEquals(e, pixelComp.getRed());
    assertEquals(e, pixelComp.getGreen());
    assertEquals(e, pixelComp.getBlue());

    Pixel pixelCompMiddle = sampleComp.getPixelAt(32,32);

    int m = (int) ((0.2126 * (sample.getPixelAt(32,32).getRed()))
            + (0.7152 * (sample.getPixelAt(32,32).getGreen()))
            + (0.0722 * (sample.getPixelAt(32,32).getBlue())));

    assertEquals(m, pixelCompMiddle.getRed());
    assertEquals(m, pixelCompMiddle.getGreen());
    assertEquals(m, pixelCompMiddle.getBlue());
  }

  @Test
  public void TestRedCompFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleComp = Image.imageFunc(sample, "red-component", 0);

    Pixel pixelComp = sampleComp.getPixelAt(0,0);

    int e = sample.getPixelAt(0,0).getRed();

    assertEquals(e, pixelComp.getRed());
    assertEquals(e, pixelComp.getGreen());
    assertEquals(e, pixelComp.getBlue());

    Pixel pixelCompMiddle = sampleComp.getPixelAt(32,32);

    int m = sample.getPixelAt(32,32).getRed();

    assertEquals(m, pixelCompMiddle.getRed());
    assertEquals(m, pixelCompMiddle.getGreen());
    assertEquals(m, pixelCompMiddle.getBlue());
  }

  @Test
  public void TestGreenCompFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);
    Image sampleComp = Image.imageFunc(sample, "green-component", 0);

    Pixel pixelComp = sampleComp.getPixelAt(0,0);

    int e = sample.getPixelAt(0,0).getGreen();

    assertEquals(e, pixelComp.getRed());
    assertEquals(e, pixelComp.getGreen());
    assertEquals(e, pixelComp.getBlue());

    Pixel pixelCompMiddle = sampleComp.getPixelAt(32,32);

    int m = sample.getPixelAt(32,32).getGreen();

    assertEquals(m, pixelCompMiddle.getRed());
    assertEquals(m, pixelCompMiddle.getGreen());
    assertEquals(m, pixelCompMiddle.getBlue());
  }

  @Test
  public void TestBlueCompFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    Image sampleComp = Image.imageFunc(sample, "blue-component", 0);

    Pixel pixelComp = sampleComp.getPixelAt(0,0);

    int e = sample.getPixelAt(0,0).getBlue();

    assertEquals(e, pixelComp.getRed());
    assertEquals(e, pixelComp.getGreen());
    assertEquals(e, pixelComp.getBlue());

    Pixel pixelCompMiddle = sampleComp.getPixelAt(32,32);

    int m = sample.getPixelAt(32,32).getBlue();

    assertEquals(m, pixelCompMiddle.getRed());
    assertEquals(m, pixelCompMiddle.getGreen());
    assertEquals(m, pixelCompMiddle.getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetPixelAtNegX() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    int fail = sample.getPixelAt(-1,0).getGreen();
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetPixelAtNegY() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    int fail = sample.getPixelAt(0,-1).getGreen();
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetPixelAtImageSizeX() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    int fail = sample.getPixelAt(64,0).getGreen();
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetPixelAtImageSizeY() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    int fail = sample.getPixelAt(0,64).getGreen();
  }

  @Test
  public void TestGreyscaleFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    Image sampleComp = Image.imageFunc(sample, "luma-component", 0);

    Pixel pixelComp = sampleComp.getPixelAt(0,0);

    Image sampleGreyscale = Image.imageFunc(sample, "greyscale", 0);

    Pixel pixelGreyscale = sampleGreyscale.getPixelAt(0,0);


    int e = (int) ((0.2126 * (sample.getPixelAt(0,0).getRed()))
            + (0.7152 * (sample.getPixelAt(0,0).getGreen()))
            + (0.0722 * (sample.getPixelAt(0,0).getBlue())));


    assertEquals(pixelComp.getRed(), pixelGreyscale.getRed());
    assertEquals(pixelComp.getGreen(), pixelGreyscale.getGreen());
    assertEquals(pixelComp.getBlue(), pixelGreyscale.getBlue());

    assertEquals(e, pixelGreyscale.getRed());
    assertEquals(e, pixelGreyscale.getGreen());
    assertEquals(e, pixelGreyscale.getBlue());

    Pixel pixelGreyscaleMiddle = sampleGreyscale.getPixelAt(32,32);
    Pixel pixelCompMiddle = sampleComp.getPixelAt(32,32);

    int m = (int) ((0.2126 * (sample.getPixelAt(32,32).getRed()))
            + (0.7152 * (sample.getPixelAt(32,32).getGreen()))
            + (0.0722 * (sample.getPixelAt(32,32).getBlue())));

    assertEquals(m, pixelGreyscaleMiddle.getRed());
    assertEquals(m, pixelGreyscaleMiddle.getGreen());
    assertEquals(m, pixelGreyscaleMiddle.getBlue());

    assertEquals(pixelCompMiddle.getRed(), pixelGreyscaleMiddle.getRed());
    assertEquals(pixelCompMiddle.getGreen(), pixelGreyscaleMiddle.getGreen());
    assertEquals(pixelCompMiddle.getBlue(), pixelGreyscaleMiddle.getBlue());
  }

  @Test
  public void TestSepiaFunc() {
    Image sample;
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    Image sampleSepia = Image.imageFunc(sample, "sepia", 0);

    Pixel pixelSepia = sampleSepia.getPixelAt(0,0);

    int rStart = (int) ((0.393 * (sample.getPixelAt(0,0).getRed()))
            + (0.769 * (sample.getPixelAt(0,0).getGreen()))
            + (0.189 * (sample.getPixelAt(0,0).getBlue())));
    int gStart = (int) ((0.349 * (sample.getPixelAt(0,0).getRed()))
            + (0.686 * (sample.getPixelAt(0,0).getGreen()))
            + (0.186 * (sample.getPixelAt(0,0).getBlue())));
    int bStart = (int) ((0.272 * (sample.getPixelAt(0,0).getRed()))
            + (0.534 * (sample.getPixelAt(0,0).getGreen()))
            + (0.131 * (sample.getPixelAt(0,0).getBlue())));

    if (rStart > 255) {
      rStart = 255;
    }
    if (gStart > 255) {
      gStart = 255;
    }
    if (bStart > 255) {
      bStart = 255;
    }

    assertEquals(pixelSepia.getRed(), rStart);
    assertEquals(pixelSepia.getGreen(), gStart);
    assertEquals(pixelSepia.getBlue(), bStart);

    Pixel pixelSepiaMiddle = sampleSepia.getPixelAt(32,32);

    int rMid = (int) ((0.393 * (sample.getPixelAt(32,32).getRed()))
            + (0.769 * (sample.getPixelAt(32,32).getGreen()))
            + (0.189 * (sample.getPixelAt(32,32).getBlue())));
    int gMid = (int) ((0.349 * (sample.getPixelAt(32,32).getRed()))
            + (0.686 * (sample.getPixelAt(32,32).getGreen()))
            + (0.186 * (sample.getPixelAt(32,32).getBlue())));
    int bMid = (int) ((0.272 * (sample.getPixelAt(32,32).getRed()))
            + (0.534 * (sample.getPixelAt(32,32).getGreen()))
            + (0.131 * (sample.getPixelAt(32,32).getBlue())));

    if (rMid > 255) {
      rMid = 255;
    }
    if (gMid > 255) {
      gMid = 255;
    }
    if (bMid > 255) {
      bMid = 255;
    }

    assertEquals(rMid, pixelSepiaMiddle.getRed());
    assertEquals(gMid, pixelSepiaMiddle.getGreen());
    assertEquals(bMid, pixelSepiaMiddle.getBlue());
  }

  @Test
  public void TestBlurFunc() {
    Image sample;
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    Image sampleBlur = Image.imageFunc(sample, "blur", 0);

    Pixel pixelBlur = sampleBlur.getPixelAt(0,0);

    assertEquals(pixelBlur.getRed(), 140);
    assertEquals(pixelBlur.getGreen(), 140);
    assertEquals(pixelBlur.getBlue(), 140);

    Pixel pixelBlurMiddle = sampleBlur.getPixelAt(32,32);

    assertEquals(231, pixelBlurMiddle.getRed());
    assertEquals(245, pixelBlurMiddle.getGreen());
    assertEquals(247, pixelBlurMiddle.getBlue());
  }

  @Test
  public void TestSharpenFunc() {
    Image sample;
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    Image sampleSharp = Image.imageFunc(sample, "sharpen", 0);

    Pixel pixelSharp = sampleSharp.getPixelAt(0,0);

    assertEquals(pixelSharp.getRed(), 255);
    assertEquals(pixelSharp.getGreen(), 255);
    assertEquals(pixelSharp.getBlue(), 255);

    Pixel pixelSharpMiddle = sampleSharp.getPixelAt(25,25);

    assertEquals(0, pixelSharpMiddle.getRed());
    assertEquals(255, pixelSharpMiddle.getGreen());
    assertEquals(255, pixelSharpMiddle.getBlue());
  }

  @Test
  public void TestLoadFunc() {
    Pixel pixelX = new Pixel(255,255,255);
    Pixel pixelY = new Pixel(255,255,255);

    assertEquals(pixelX, pixelY);

    Image sample1;
    Image sample2;
    Image sample3;
    Image sample4;
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample1 = Image.loadPPMImage(sc);


    try {
      sample2 = Image.loadStandardImage(read(new FileInputStream("images/Happy.jpeg")));
    } catch (NullPointerException e) {
      sample2 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      sample3 = Image.loadStandardImage(read(new FileInputStream("images/Happy.png")));
    } catch (NullPointerException e) {
      sample3 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      sample4 = Image.loadStandardImage(read(new FileInputStream("images/Happy.jpg")));
    } catch (NullPointerException e) {
      sample4 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(sample1, sample3);
    assertEquals(sample2, sample4);
    assertEquals(sample2, sample3);
  }

  @Test
  public void TestHistogram() {
    Image sample1;
    Image sample2;
    Scanner sc1;
    Scanner sc2;

    try {
      sc1 = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample1 = Image.loadPPMImage(sc1);

    try {
      sc2 = new Scanner(new FileInputStream("images/Happy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample2 = Image.loadPPMImage(sc2);

    int[] samp1ResI = Image.imageToHistogram(sample1, "intensity");
    int[] samp1ResR = Image.imageToHistogram(sample1, "red");
    int[] samp1ResG = Image.imageToHistogram(sample1, "green");
    int[] samp1ResB = Image.imageToHistogram(sample1, "blue");

    assertEquals(samp1ResR.length, samp1ResI.length);
    assertEquals(samp1ResB.length, samp1ResG.length);


    assertEquals(Arrays.stream(samp1ResI).sum(), Arrays.stream(samp1ResR).sum());
    assertEquals(Arrays.stream(samp1ResB).sum(), Arrays.stream(samp1ResG).sum());
    assertEquals(Arrays.stream(samp1ResR).sum(), Arrays.stream(samp1ResB).sum());


    Image sepiaHappy = Image.imageFunc(sample1, "sepia", 0);
    int[] sepResI = Image.imageToHistogram(sepiaHappy, "intensity");
    int[] sepResR = Image.imageToHistogram(sepiaHappy, "red");
    int[] sepResB = Image.imageToHistogram(sepiaHappy, "green");
    int[] sepResG = Image.imageToHistogram(sepiaHappy, "blue");

    assertEquals(Arrays.stream(sepResI).sum(), Arrays.stream(sepResR).sum());
    assertEquals(Arrays.stream(sepResB).sum(), Arrays.stream(sepResG).sum());
    assertEquals(Arrays.stream(sepResI).sum(), Arrays.stream(sepResG).sum());
    assertEquals(Arrays.stream(samp1ResI).sum(), Arrays.stream(sepResG).sum());

    int[] samp2ResI = Image.imageToHistogram(sample2, "intensity");
    int[] samp2ResR = Image.imageToHistogram(sample2, "red");
    int[] samp2ResG = Image.imageToHistogram(sample2, "green");
    int[] samp2ResB = Image.imageToHistogram(sample2, "blue");

    assertEquals(Arrays.stream(samp1ResI).sum(), Arrays.stream(samp2ResI).sum());
    assertEquals(Arrays.stream(samp1ResR).sum(), Arrays.stream(samp2ResR).sum());
    assertEquals(Arrays.stream(samp1ResG).sum(), Arrays.stream(samp2ResG).sum());
    assertEquals(Arrays.stream(samp1ResB).sum(), Arrays.stream(samp2ResB).sum());

  }


  @Test
  public void TestSavePNG() throws IOException {
    Image sample1;
    Image sample2;

    Image res1;
    Scanner sc;

    try {
      sample1 = Image.loadStandardImage(read(new FileInputStream("images/Happy.png")));
    } catch (NullPointerException e) {
      sample1 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      sc = new Scanner(new FileInputStream("images/sepiaHappy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample2 = Image.loadPPMImage(sc);


    Image imageSep = Image.imageFunc(sample1, "sepia", 0);


    BufferedImage saveImage = Image.bufferImage(imageSep);

    FileOutputStream fos = new FileOutputStream("images/sepiaHappy.png");
    ImageIO.write(saveImage, "png", fos);
    fos.flush();

    try {
      res1 = Image.loadStandardImage(read(new FileInputStream("images/sepiaHappy.png")));
    } catch (NullPointerException e) {
      res1 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    File cleanUp = new File("images/sepiaHappy.png");
    cleanUp.delete();

    assertEquals(res1, sample2);


  }

  @Test
  public void TestSaveJPEG() throws IOException {
    Image sample1;
    Image sample2;

    Image res1;
    Scanner sc;

    try {
      sample1 = Image.loadStandardImage(read(new FileInputStream("images/Happy.png")));
    } catch (NullPointerException e) {
      sample1 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      sc = new Scanner(new FileInputStream("images/sepiaHappy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample2 = Image.loadPPMImage(sc);


    Image imageSep = Image.imageFunc(sample1, "sepia", 0);


    BufferedImage saveImage = Image.bufferImage(imageSep);

    FileOutputStream fos = new FileOutputStream("images/sepiaHappy.jpeg");
    ImageIO.write(saveImage, "jpeg", fos);
    fos.flush();

    try {
      res1 = Image.loadStandardImage(read(new FileInputStream("images/sepiaHappy.jpeg")));
    } catch (NullPointerException e) {
      res1 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    File cleanUp = new File("images/sepiaHappy.jpeg");
    cleanUp.delete();

    assertEquals(res1, sample2);
  }

  @Test
  public void TestSaveJPG() throws IOException {
    Image sample1;
    Image sample2;

    Image res1;
    Scanner sc;

    try {
      sample1 = Image.loadStandardImage(read(new FileInputStream("images/Happy.png")));
    } catch (NullPointerException e) {
      sample1 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      sc = new Scanner(new FileInputStream("images/sepiaHappy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample2 = Image.loadPPMImage(sc);


    Image imageSep = Image.imageFunc(sample1, "sepia", 0);


    BufferedImage saveImage = Image.bufferImage(imageSep);

    FileOutputStream fos = new FileOutputStream("images/sepiaHappy.jpg");
    ImageIO.write(saveImage, "jpg", fos);
    fos.flush();

    try {
      res1 = Image.loadStandardImage(read(new FileInputStream("images/sepiaHappy.jpg")));
    } catch (NullPointerException e) {
      res1 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    File cleanUp = new File("images/sepiaHappy.jpg");
    cleanUp.delete();

    assertEquals(res1, sample2);
  }

  @Test
  public void TestSavePPM() throws IOException {
    Image sample1;
    Image sample2;

    Image res1;
    Scanner sc;
    Scanner sc2;

    try {
      sample1 = Image.loadStandardImage(read(new FileInputStream("images/Happy.png")));
    } catch (NullPointerException e) {
      sample1 = null;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      sc = new Scanner(new FileInputStream("images/sepiaHappy.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample2 = Image.loadPPMImage(sc);


    Image imageSep = Image.imageFunc(sample1, "sepia", 0);

    String ret = Image.toStringPPM(imageSep);
    try {
      FileOutputStream fos = new FileOutputStream("images/sepiaHappy1.ppm");
      fos.write((ret).getBytes());
      fos.close();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

    try {
      sc2 = new Scanner(new FileInputStream("images/sepiaHappy1.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    res1 = Image.loadPPMImage(sc2);

    File cleanUp = new File("images/sepiaHappy1.ppm");
    cleanUp.delete();

    assertEquals(res1, sample2);
  }

  @Test
  public void TestDownScaleFunc() {
    Image sample;
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("images/SepiaHisoka.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    Image sampleDownScale = Image.downscaleImage(sample, 0.75, 0.5);

    assertEquals(sampleDownScale.getWidth(), (int) (sample.getWidth() * 0.75));
    assertEquals(sampleDownScale.getHeight(), (int) (sample.getHeight() * 0.5));
  }

  @Test
  public void TestMaskImage() {
    Image sample;
    Image sampleMask;
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("images/Hisoka.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sample = Image.loadPPMImage(sc);

    try {
      sc = new Scanner(new FileInputStream("images/HisokaRedComponent.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
    sampleMask = Image.loadPPMImage(sc);

    Image sampleMaskImp = Image.maskImage(sample, sampleMask, "sepia", 0);
    Image sampleImp = Image.imageFunc(sample, "sepia", 0);

    assertNotEquals(sampleMaskImp, sampleImp);
    assertNotEquals(sampleMaskImp, sample);

    assertEquals(sampleMaskImp.getPixelAt(0, 0), sample.getPixelAt(0, 0));
  }
}
