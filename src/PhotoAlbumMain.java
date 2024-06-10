import controller.IController;
import controller.PhotoAlbumController;
import controller.WebAlbumController;
import model.IPhotoAlbumModel;
import model.PhotoAlbumModel;
import view.GraphicalView;
import view.IView;
import view.WebView;

/**
 * The entry point of the photo album application. This class handles command-line arguments
 * to configure and launch the application with specified settings for input file, view type,
 * output path, and optional view dimensions.
 */
public class PhotoAlbumMain {

  /**
   * Parses command-line arguments to configure and start the photo album application.
   * Supports launching the application in different views based on the provided arguments.
   *
   * @param args Command-line arguments specifying input file, view type, output path,
   *             and optional dimensions for the graphical view.
   */
  public static void main(String[] args) {
    String inputFile = null;
    String viewType = null;
    String outputPath = null;
    int xmax = 1000; // Default width
    int ymax = 1000; // Default height

    // Parse command-line arguments
    for (int i = 0; i < args.length; i++) {
      switch (args[i].toLowerCase()) {
        case "-in":
          inputFile = args[++i];
          break;
        case "-view":
        case "-v":
          viewType = args[++i];
          break;
        case "-out":
          outputPath = args[++i];
          break;
        case "xmax":
          xmax = Integer.parseInt(args[++i]);
          break;
        case "ymax":
          ymax = Integer.parseInt(args[++i]);
          break;
      }
    }

    // Check for mandatory arguments
    if (inputFile == null || viewType == null) {
      System.err.println("Input file and view type are mandatory.");
      return;
    }

    // Launch the application based on the specified view type and settings
    runApplication(viewType, inputFile, outputPath, xmax, ymax);
  }

  /**
   * Initializes and starts the photo album application with the specified configuration.
   * Creates model, view, and controller components and links them together.
   *
   * @param viewType    The type of view to be used (e.g., "graphical", "web").
   * @param inputFile   The path to the input file containing shape and snapshot information.
   * @param outputPath  The path where the output (if applicable, like in web view) should be saved.
   * @param xmax        The width of the view area (applicable for graphical view).
   * @param ymax        The height of the view area (applicable for graphical view).
   */
  private static void runApplication(String viewType, String inputFile, String outputPath,
                                     int xmax, int ymax) {
    IPhotoAlbumModel model = PhotoAlbumModel.getInstance();
    IView view;
    IController controller;

    // Initialize view and controller based on the specified view type
    switch (viewType.toLowerCase()) {
      case "graphical":
        view = new GraphicalView(xmax, ymax);
        controller = new PhotoAlbumController(model, view, inputFile);
        break;
      case "web":
        view = new WebView(outputPath);
        controller = new WebAlbumController(model, view, inputFile);
        break;
      default:
        throw new IllegalArgumentException("Unsupported view type: " + viewType);
    }

    // Link the view and controller, and start the application
    view.setController(controller);
    controller.start();
  }

}

