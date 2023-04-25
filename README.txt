Assignment 8: Image Processing Extra credit
by Dilan Kudva and Isabella Taneja

All images used were created by Dilan Kudva and Isabella Taneja

Controller
Our controller contains the interface "ImageProcessorController" and a class "ImageControllerImpl"
The interface contains the method control which allows the controller to be accessed by the user
The class implements the interface and allows the user to write their commands for the program so
    that the function can run it. This class has four fields Readable Input, String[] string, and
    HashMap<String, Image> images = new HashMaps<>();, and a ArrayList<String> functions =
    new ArrayList<>(); Our method 'ImageControllerImpl' takes a readable input and adds the string
    of the function name. The methods within imageControllerImpl also include 'save' which
    saves the image, 'loadImage' which loads the image the user inputs, and the controller that
    runs all the functions from the user inputs in the method 'control'.

Model
Our model contains the classes 'image' and 'pixel' The class 'image' uses four fields
    Pixel[][] pixelArray, int width, int height, int maxValue. We have four matrixs sharpMatrix,
    blurMatrix, sepiaMatrix, greyScaleMatrix. We have five methods within this class:
    ImageFunc, brightenVal, darkenVal, saveImage, and loadImage.
    Image func is our main function that directs user commands to do what we want them to do
    it runs 'horizontal-flip', 'vertical-flip', 'value-component', 'intensity-component',
    'luma-component', 'red-component','green-component', 'blue-component', 'brighten', 'darken',
    'blur', 'sharpen', 'sepia', and 'greyscale'. The image class also contains the methods,
    'filterPixel' applies either the blur filter or sharpen pixel to a given pixel,
    'changeShadePixel' abstract version of brighten and darken pixel which takes user inputs to
    either darken or brighten a pixel by a certain degree, 'transformPixel' applies either the sepia
    transformation or the greyscale transformation to a given pixel, 'compPixel' Comp pixel takes a
    given image and takes the component given by the user, 'capVal' Makes a given value either 0 if
    it’s less than 0 or caps it at maxValue if it is greater, 'loadPPMImage' Loads an image based
    off of a scanner, 'getPixelAt' Gets a copy of a pixel at a provided point used for tests,
    'loadStandardImage' Creates an image from a buffered image provided by the java read function in
    our controller, 'bufferImage' It makes one images a buffered image which is a Java image for
    ImageIO.write, and 'toStringPPM' which saves the current image. The method 'getWidth',
    'getHeight', 'getMaxValue' which intialize the width, height, and max value.
    The method 'imageToHistogram' turns an image into histograms values. The 'equals' method checks
    if the two images are equal and values, heigh, widht, and maxValue.

    ** For extra credit **
    We added downscaleImage which takes an image and applies a downscale factor to the width and
    height. It takes in an Image, xFactor, and yFactor. The function maskImage takes the black
    pixels from a black and white version of the image and applies the filter to the new image.
    Takes in an image, the masked image, the function, and the value.


    The class 'Pixel' takes three fields int red, int green, and int blue. Has a method 'equal'
    which checks if two pixels are equal to each other. Method 'hashcode' which creates the hashcode
    for a pixel. And three methods 'getBlue', 'getRed', and 'getGreen' which gets the blue, red,
    and green values.

Main
Our main contains a method that takes a String[] args to Function to run the image editor
    using the controller and user inputs. Appends string message with message of the users command
    being executed.

View
Our view contains an interface that has two methods a toString represents the string sent after
    the user inputs showing that the image has changes. The second method is renderMessage which
    takes in a string message and represents the message we want to show after image has changed.
    The class View implements the IView interface which has two fields appendable and an image model
    The first view method checks that the appendable string is not null, the second view method
    checks if the image or appendable is null so the view can run, the method toSting  is the
    string that will be shown after a user command has occurred, and the last method renderMessage
    whcih is the message for the command that has taken place. Our next class 'Histograms' extends
    JPanel and has two fields image loaded image which is the image loaded and int width. A method
    'Histograms' which takes in a loadedImage and width which initializes loaded image and width.
    'replaceImage' which takes an inputed newImage and replaces the loaded image. The last class is
    'paintComponent' which draws the histogram and takes in graphics object. Our last class in view
    is 'ViewGUI' which extends JFrame implements ActionListener, ListSelectionListener. This class
    opens the main window, that has different elements illustrated in it. Class has multiple fields,
    mainPanel, fullPanel, mainScrollPane, fileOpenDisplay, fileSaveDisplay, listOfStrings,
    imageLabel, sc, loadedImage, bufferedImage, loadPath, savePath, commands, and hist. Our first
    method ViewGUI initializes the commands. Our method 'actionPerformed' takes in an action that
    needs to be done to the image and takes in an event. Method 'display' is in charge of displaying
    the given image. Our last method 'valueChange' takes in an event and changes the value of an
    image for brighten and darken.