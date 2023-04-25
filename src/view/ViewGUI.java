package view;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import model.Image;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import static javax.imageio.ImageIO.read;

/**
 * class that opens the main window, that has different elements illustrated in
 * it. extends JFrame and implements ActionListener and ListSelectionListener.
 */


public class ViewGUI extends JFrame implements ActionListener, ListSelectionListener {
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JList<String> listOfStrings;
  JLabel imageLabel;
  Scanner sc;
  Image loadedImage;
  BufferedImage bufferedImage;
  String loadPath;
  String savePath;
  HashMap<String, String> commands;
  Histograms hist;

  /**
   * initializes the commands for the view GUI.
   */
  public ViewGUI() {
    super();
    setTitle("Image Processor");
    setSize(1600, 800);

    commands = new HashMap<>();
    commands.put("Red Component", "red-component");
    commands.put("Blue Component", "blue-component");
    commands.put("Green Component", "green-component");
    commands.put("Value Component", "value-component");
    commands.put("Intensity Component", "intensity-component");
    commands.put("Luma Component", "luma-component");
    commands.put("Horizontal Flip", "horizontal-flip");
    commands.put("Vertical Flip", "vertical-flip");
    commands.put("Brighten", "brighten");
    commands.put("Darken", "darken");
    commands.put("Blur", "blur");
    commands.put("Sharpen", "sharpen");
    commands.put("Sepia", "sepia");
    commands.put("Greyscale", "greyscale");
    commands.put("Downscale", "downscale");

    hist = new Histograms(loadedImage, this.getWidth());

    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    JPanel fullPanel = new JPanel();
    fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.X_AXIS));
    mainPanel.add(fullPanel);
    mainPanel.add(hist);


    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    fullPanel.add(imagePanel);

    JPanel filePanel = new JPanel();

    filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.PAGE_AXIS));

    JScrollPane imageScrollPane;

    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(null);
    imageScrollPane.setPreferredSize(new Dimension(100, 600));
    imagePanel.add(imageScrollPane);

    //Selection lists
    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Edit Image"));
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    filePanel.add(selectionListPanel);

    DefaultListModel<String> dataForListOfStrings = new DefaultListModel<>();

    dataForListOfStrings.addElement("Red Component");
    dataForListOfStrings.addElement("Blue Component");
    dataForListOfStrings.addElement("Green Component");
    dataForListOfStrings.addElement("Value Component");
    dataForListOfStrings.addElement("Intensity Component");
    dataForListOfStrings.addElement("Luma Component");
    dataForListOfStrings.addElement("Horizontal Flip");
    dataForListOfStrings.addElement("Vertical Flip");
    dataForListOfStrings.addElement("Brighten");
    dataForListOfStrings.addElement("Darken");
    dataForListOfStrings.addElement("Blur");
    dataForListOfStrings.addElement("Sharpen");
    dataForListOfStrings.addElement("Sepia");
    dataForListOfStrings.addElement("Greyscale");
    dataForListOfStrings.addElement("Downscale");

    listOfStrings = new JList<>(dataForListOfStrings);
    listOfStrings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfStrings.addListSelectionListener(this);
    selectionListPanel.add(listOfStrings);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    filePanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open an image");
    fileOpenButton.setActionCommand("Open Image");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("Image load path will appear here");
    filePanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    filePanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save an image");
    fileSaveButton.setActionCommand("Save Image");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("Image save path will appear here");
    filesavePanel.add(fileSaveDisplay);

    fullPanel.add(filePanel);
  }

  /**
   * takes in an action to be done to the image.
   * @param event the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent event) {
    if (event.getActionCommand().equals("Open Image")) {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Images",
              "jpg", "gif", "jpeg", "png", "ppm");
      fchooser.setFileFilter(filter);

      if (fchooser.showOpenDialog(ViewGUI.this) == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        loadPath = f.getAbsolutePath();
        fileOpenDisplay.setText(loadPath);
        display(null);
      }
    } else if (event.getActionCommand().equals("Save Image")) {
      final JFileChooser fchooser = new JFileChooser(".");
      if (fchooser.showSaveDialog(ViewGUI.this) == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        savePath = f.getAbsolutePath();
        fileSaveDisplay.setText(f.getAbsolutePath());
        String format = savePath.substring(savePath.indexOf(".") + 1);
        if (format.equals("ppm")) {
          String ret = Image.toStringPPM(loadedImage);
          try {
            FileOutputStream fos = new FileOutputStream(savePath);
            fos.write((ret).getBytes());
            fos.close();
          } catch (IOException e) {
            System.out.println("Trouble Writing in File");
          }
        } else {
          BufferedImage saveImage = Image.bufferImage(loadedImage);
          try {
            FileOutputStream fos = new FileOutputStream(savePath);
            ImageIO.write(saveImage, format, fos);
            fos.flush();
          } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
          } catch (IOException e) {
            System.out.println("Trouble Writing in File");
          }
        }
      }
    }
  }

  /**
   * method that deplays the given image and histogram.
   * @param image the image.
   */
  public void display(Image image) {
    if (image != null) {
      bufferedImage = Image.bufferImage(loadedImage);
      imageLabel.setIcon(new ImageIcon(bufferedImage));
      hist.replaceImage(loadedImage);
    } else {
      String format = loadPath.substring(loadPath.indexOf(".") + 1);
      if (format.equals("ppm")) {
        try {
          sc = new Scanner(new FileInputStream(loadPath));
          loadedImage = Image.loadPPMImage(sc);
          bufferedImage = Image.bufferImage(loadedImage);
        } catch (FileNotFoundException e) {
          System.out.println("File Not Found");
        }
      } else {
        try {
          bufferedImage = read(new FileInputStream(loadPath));
          loadedImage = Image.loadStandardImage(bufferedImage);
        } catch (IOException e) {
          System.out.println("Trouble Writing in File");
        }
      }
      imageLabel.setIcon(new ImageIcon(bufferedImage));

      hist.replaceImage(loadedImage);
    }

    hist.repaint();
  }

  /**
   * changes the value for brighten and darken.
   * @param e the event that characterizes the change.
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    String command = commands.get(this.listOfStrings.getSelectedValue());
    if (!e.getValueIsAdjusting()) {
      int val;
      double val1;
      double val2;
      if (command.equals("brighten") || command.equals("darken")) {
        val = Integer.parseInt(JOptionPane.showInputDialog("How much do you want to " + command));
        this.loadedImage = Image.imageFunc(loadedImage, command, val);
      } else if (command.equals("downscale")) {
        val1 = Double.parseDouble(JOptionPane.showInputDialog(
                "How much do you want to change width"));
        val2 = Double.parseDouble(JOptionPane.showInputDialog(
                "How much do you want to change height"));

        try {
          this.loadedImage = Image.downscaleImage(loadedImage, val1, val2);
        } catch (IllegalArgumentException p) {
          this.renderMessage("Invalid Arguments");
        }
      } else {
        this.loadedImage = Image.imageFunc(loadedImage, command, 0);
      }
      display(loadedImage);
    }
  }

  /**
   * Renders a message to the view GUI.
   * @param message the message to be rendered.
   */
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
}
