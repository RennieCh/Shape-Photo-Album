package utility;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The PhotoAlbumLoader class provides static methods to load photo album data from a file.
 * It reads the file line by line, parsing commands to create shapes, take snapshots,
 * and perform various actions like moving, resizing, coloring, or removing shapes.
 */
public class PhotoAlbumLoader {

  /**
   * Loads photo album data from the specified file path into the given model.
   * This method reads the file line by line, interpreting each line as a command
   * to manipulate the photo album model.
   *
   * @param filePath The path of the file containing photo album commands.
   * @param model    The photo album model where the data will be loaded.
   */
  public static void loadFromFile(String filePath, IPhotoAlbumModel model) {
    try {
      List<String> lines = Files.readAllLines(Paths.get(filePath));
      for (String line : lines) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) continue; // Skip comments and empty lines

        String[] parts = line.split("\\s+");
        switch (parts[0].toLowerCase()) {
          case "shape":
            createShapeFromLine(line, model);
            break;
          case "snapshot":
            String description = line.substring("snapshot".length()).trim();
            model.takeSnapshot(description);
            break;
          case "move":
            moveShapeFromLine(line, model);
            break;
          case "resize":
            resizeShapeFromLine(line, model);
            break;
          case "color":
            changeShapeColorFromLine(line, model);
            break;
          case "remove":
            removeShapeFromLine(line, model);
            break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a shape from a line of text and adds it to the model.
   * The line format is expected to define the shape type, its identifier, size, dimensions,
   * and color.
   *
   * @param line  A line of text representing a command to create a shape.
   * @param model The photo album model where the shape will be added.
   */
  private static void createShapeFromLine(String line, IPhotoAlbumModel model) {
    // Example: shape B0 rectangle 80 424 100 326 0 0 0
    String[] parts = line.split("\\s+");
    String name = parts[1];
    ShapeType type = ShapeType.valueOf(parts[2].toUpperCase());
    double x = Double.parseDouble(parts[3]);
    double y = Double.parseDouble(parts[4]);
    double width = Double.parseDouble(parts[5]);
    double height = Double.parseDouble(parts[6]);
    double red = Double.parseDouble(parts[7]);
    double green = Double.parseDouble(parts[8]);
    double blue = Double.parseDouble(parts[9]);

    IShape shape = model.createShape(type, name, red, green, blue, Location.LEFT_CORNER, x,
            y, width, height);
    model.addShape(shape);
  }

  /**
   * Resizes a shape based on a line of text representing a resize command.
   *
   * @param line  A line of text representing a command to resize a shape.
   * @param model The photo album model containing the shape to be resized.
   */
  private static void resizeShapeFromLine(String line, IPhotoAlbumModel model) {
    String[] parts = line.split("\\s+");
    String shapeName = parts[1];
    double newWidth = Double.parseDouble(parts[2]);
    double newHeight = Double.parseDouble(parts[3]);

    model.resizeShape(shapeName, newWidth, newHeight);
  }

  /**
   * Changes the color of a shape based on a line of text representing a color change command.
   *
   * @param line  A line of text representing a command to change a shape's color.
   * @param model The photo album model containing the shape whose color will be changed.
   */
  private static void changeShapeColorFromLine(String line, IPhotoAlbumModel model) {
    String[] parts = line.split("\\s+");
    String shapeName = parts[1];
    double red = Double.parseDouble(parts[2]);
    double green = Double.parseDouble(parts[3]);
    double blue = Double.parseDouble(parts[4]);

    model.changeShapeColor(shapeName, red, green, blue);
  }

  /**
   * Moves a shape based on a line of text representing a move command.
   *
   * @param line  A line of text representing a command to move a shape.
   * @param model The photo album model containing the shape to be moved.
   */
  private static void moveShapeFromLine(String line, IPhotoAlbumModel model) {
    // Example: move ball 780 450
    String[] parts = line.split("\\s+");
    String shapeName = parts[1];
    double newX = Double.parseDouble(parts[2]);
    double newY = Double.parseDouble(parts[3]);

    model.moveShape(shapeName, newX, newY);
  }

  /**
   * Removes a shape from the model based on a line of text representing a remove command.
   *
   * @param line  A line of text representing a command to remove a shape.
   * @param model The photo album model from which the shape will be removed.
   */
  private static void removeShapeFromLine(String line, IPhotoAlbumModel model) {
    String[] parts = line.split("\\s+");
    String shapeName = parts[1];

    model.removeShape(shapeName);
  }
}
