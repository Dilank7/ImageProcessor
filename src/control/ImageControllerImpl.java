package control;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Image;
import view.View;

import static javax.imageio.ImageIO.read;


/**
 * represents the ImageController implementation to runs the users commands for different functions.
 */
public class ImageControllerImpl implements ImageProcessorController {
  private final Readable input;
  private String[] string;
  private static final HashMap<String, Image> images = new HashMap<>();
  private final ArrayList<String> functions = new ArrayList<>();

  private View view;

  /**
   * implementation of the controller with input and view.
   *
   * @param input readable input from user
   * @param view  view that will be shown
   */
  public ImageControllerImpl(Readable input, View view) {
    this.input = input;
    this.view = view;
    this.string = new String[100];
    this.addAllFunctions();
  }

  /**
   * just takes readable.
   *
   * @param input the readable input from the user
   */
  public ImageControllerImpl(Readable input) {
    this.input = input;
    this.string = new String[100];
    this.addAllFunctions();
  }

  private void addAllFunctions() {
    functions.add("load");
    functions.add("save");
    functions.add("horizontal-flip");
    functions.add("vertical-flip");
    functions.add("value-component");
    functions.add("intensity-component");
    functions.add("luma-component");
    functions.add("red-component");
    functions.add("green-component");
    functions.add("blue-component");
    functions.add("brighten");
    functions.add("darken");
    functions.add("blur");
    functions.add("sharpen");
    functions.add("sepia");
    functions.add("greyscale");
    functions.add("downscale");
    functions.add("quit");
    functions.add("q");
  }

  /**
   * saves the image.
   * @param savePath    the name of the new saved image
   * @param imageToSave the image the user wants to save
   * @throws IOException if null
   */
  private void saveImage(String savePath, String imageToSave) throws IOException {
    if (images.containsKey(imageToSave)) {
      String format = savePath.substring(savePath.indexOf(".") + 1);
      if (format.equals("ppm")) {
        String ret = Image.toStringPPM(images.get(imageToSave));
        try {
          FileOutputStream fos = new FileOutputStream(savePath);
          fos.write((ret).getBytes());
          fos.close();
          view.renderMessage("Saved " + imageToSave + " into " + savePath + "\n");
        } catch (IOException e) {
          view.renderMessage("Couldn't save image\n");
        }
      } else {
        BufferedImage saveImage = Image.bufferImage(images.get(imageToSave));
        try {
          FileOutputStream fos = new FileOutputStream(savePath);
          ImageIO.write(saveImage, format, fos);
          fos.flush();
          view.renderMessage("Saved " + imageToSave + " into " + savePath + "\n");
        } catch (FileNotFoundException e) {
          view.renderMessage(imageToSave + " is an invalid path.\n");
        } catch (IOException e) {
          view.renderMessage("Rendering failed for: " + imageToSave + "\n");
        }
      }
    } else {
      view.renderMessage("Can't save a file that doesn't exist\n");
    }
  }

  /**
   * loads the given image.
   *
   * @param imageToLoad   the image the user wants to load.
   * @param loadImageName the name of the image.
   * @throws IOException if null
   */
  private void loadImage(String imageToLoad, String loadImageName) throws IOException {
    try {
      BufferedImage image = read(new FileInputStream(imageToLoad));
      Image loadedImage = Image.loadStandardImage(image);
      images.put(loadImageName, loadedImage);
      view.renderMessage("Width of image: " + loadedImage.getWidth() + "\n");
      view.renderMessage("Height of image: " + loadedImage.getHeight() + "\n");
      view.renderMessage("Maximum value of a color in this file (usually 255): "
              + loadedImage.getMaxValue() + "\n");
      view.renderMessage("Loaded " + imageToLoad + " as: " + loadImageName + "\n");
    } catch (NullPointerException e) {
      try {
        Scanner sc;
        sc = new Scanner(new FileInputStream(imageToLoad));
        Image loadedImage = Image.loadPPMImage(sc);
        images.put(loadImageName, loadedImage);
        view.renderMessage("Width of image: " + loadedImage.getWidth() + "\n");
        view.renderMessage("Height of image: " + loadedImage.getHeight() + "\n");
        view.renderMessage("Maximum value of a color in this file (usually 255): "
                + loadedImage.getMaxValue() + "\n");
        view.renderMessage("Loaded " + imageToLoad + " as: " + loadImageName + "\n");
      } catch (FileNotFoundException f) {
        view.renderMessage("File not found in system try again\n");
      }
    }
  }


  /**
   * the controller that runs all the functions from the user inputs.
   *
   * @throws IOException if anything is null.
   */
  @Override
  public void control() throws IOException {
    Scanner scan = new Scanner(input);
    while (scan.hasNextLine()) {
      int counter = 0;
      this.string = new String[100];

      Scanner scannedCommand = new Scanner(scan.nextLine());

      while (scannedCommand.hasNext()) {
        this.string[counter] = scannedCommand.next();
        counter++;
      }

      String func = this.string[0];

      if (!functions.contains(func)) {
        view.renderMessage("Function undefined\n");
      } else {
        if (func.equals("quit") || func.equals("q")) {
          view.renderMessage("Have a nice day!\n");
          return;
        } else if (func.equals("save")) {
          saveImage(this.string[1], this.string[2]);
        } else if (func.equals("load")) {
          loadImage(this.string[1], this.string[2]);
        } else {
          if(string[4] != null) {
            if (string[0].equals("downscale")) {
              try {
                images.put(string[4], Image.downscaleImage(images.get(string[3]),
                        Double.parseDouble(string[1]), Double.parseDouble(string[2])));
                view.renderMessage("Created new image " + string[4] + " using function " + func
                        + " on " + string[3] + "\n");
              } catch (IllegalArgumentException e) {
                view.renderMessage("Illegal Arguments");
              }
            } else {
              images.put(string[4], Image.maskImage(images.get(string[2]), images.get(string[3]),
                      func, Integer.parseInt(string[1])));
              view.renderMessage("Created new image " + string[4] + " using function " + func
                      + " on " + string[1] + " with mask image " + string[2] + "\n");
            }
          } else {
            if (string[3] == null) {
              if (images.containsKey(string[1])) {
                images.put(string[2], Image.imageFunc(images.get(string[1]), func, 0));
                view.renderMessage("Created new image " + string[2] + " using function " + func
                        + " on " + string[1] + "\n");
              } else {
                view.renderMessage("File doesn't exist\n");
              }
            } else {
              if (string[0].equals("darken") || string[0].equals("brighten")) {
                if (images.containsKey(string[2])) {
                  images.put(string[3], Image.imageFunc(images.get(string[2]), func,
                          Integer.parseInt(string[1])));
                  view.renderMessage("Created new image: " + string[3] + " using function " + func
                          + " on " + string[2] + "\n");
                } else {
                  view.renderMessage("File doesn't exist to brighten or darken\n");
                }
              } else {
                if (images.containsKey(string[1]) && images.containsKey(string[2])) {
                  images.put(string[3], Image.maskImage(images.get(string[1]),
                          images.get(string[2]), func, 0));
                  view.renderMessage("Created new image: " + string[3] + " using function " + func
                          + " on " + string[1] + " with mask image " + string[2] + "\n");
                } else {
                  view.renderMessage("Image or mask image doesn't exist\n");
                }
              }
            }
          }
        }
      }
    }
  }
}


