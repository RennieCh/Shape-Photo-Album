package view;

import controller.IController;
import model.IShape;
import model.Oval;
import model.Rectangle;
import model.Snapshot;

import java.awt.*;
import java.io.Writer;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

/**
 * WebView provides an HTML view for the photo album application.
 * It generates an HTML file with SVG representations of the snapshots
 * and their contained shapes.
 */
public class WebView implements IView {
  private IController controller;
  private final String outputPath;

  /**
   * Constructs a WebView instance with the specified output path for the HTML file.
   *
   * @param outputPath The path where the HTML file should be saved. Must end with ".html".
   * @throws IllegalArgumentException if the outputPath does not end with ".html".
   */
  public WebView(String outputPath) throws IllegalArgumentException {
    if (!outputPath.endsWith(".html")) {
      throw new IllegalArgumentException("Output path must end with .html");
    }
    this.outputPath = outputPath;
  }

  /**
   * Sets the controller for this view. This method allows the view to communicate with the
   * controller, which handles user actions and updates the model accordingly.
   *
   * @param controller The controller instance that this view will interact with.
   */
  @Override
  public void setController(IController controller) {
    this.controller = controller;
  }

  /**
   * Getter function to retrieve the controller.
   */
  @Override
  public IController getController() {
    return this.controller;
  }

  /**
   * Refreshes the web view to reflect any changes in the model's state or due to user interaction.
   */
  @Override
  public void refresh() {
    generateHTMLFile(outputPath);
  }

  /**
   * Generates an HTML file at the specified outputPath. This file includes SVG
   * representations of each snapshot and its shapes.
   *
   * @param outputPath The path where the HTML file is saved.
   */
  protected void generateHTMLFile(String outputPath) {
    List<Snapshot> snapshots = controller.getModel().listSnapshots();

    try (Writer writer = getWriter(outputPath)) { // Use getWriter here
      startHTML(writer);
      for (Snapshot snapshot : snapshots) {
        writeSnapshot(writer, snapshot);
      }
      endHTML(writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected Writer getWriter(String outputPath) throws IOException {
    return new FileWriter(outputPath);
  }


  /**
   * Writes the initial HTML structure to the file.
   *
   * @param writer The FileWriter instance used for writing to the file.
   * @throws IOException if an I/O error occurs.
   */
  private void startHTML(Writer writer) throws IOException {
    writer.write("<!DOCTYPE html>\n<html><head><title>Photo Album</title></head><body>\n");
  }

  /**
   * Writes the SVG representation of a snapshot to the file.
   *
   * @param writer   The FileWriter instance used for writing to the file.
   * @param snapshot The snapshot to represent in SVG.
   * @throws IOException if an I/O error occurs.
   */
  private void writeSnapshot(Writer writer, Snapshot snapshot) throws IOException {
    Dimension dimensions = calculateDimensions(snapshot);
    writer.write("<div class='snapshot'>\n");
    writer.write(String.format("<svg width='%f' height='%f' xmlns='http://www.w3.org/2000/svg'>\n",
            dimensions.getWidth(), dimensions.getHeight()));
    writeBackground(writer, dimensions);
    writeHeader(writer, snapshot);
    for (IShape shape : snapshot.getShapes()) {
      writer.write(convertShapeToSVG(shape));
    }
    writer.write("</svg>\n</div>\n");
  }

  /**
   * Writes the closing tags of the HTML structure to the file.
   *
   * @param writer The FileWriter instance used for writing to the file.
   * @throws IOException if an I/O error occurs.
   */
  private void endHTML(Writer writer) throws IOException {
    writer.write("</body></html>\n");
  }

  /**
   * Calculates the dimensions needed for the SVG canvas based on the shapes within a snapshot.
   * This method ensures that all shapes are visible within the SVG viewbox by adjusting the
   * dimensions to fit all shapes with additional margins.
   *
   * @param snapshot The snapshot whose shapes are to be displayed.
   * @return A Dimension object representing the width and height needed for the SVG canvas.
   */
  private Dimension calculateDimensions(Snapshot snapshot) {
    double maxWidth = 100; // Starting width for margins
    double maxHeight = 160; // Starting height to accommodate text space and margins
    for (IShape shape : snapshot.getShapes()) {
      // Additional margin
      maxWidth = Math.max(maxWidth, shape.getPoints().getX() + shape.getWidth() + 100);
      // Additional text space and margin
      maxHeight = Math.max(maxHeight, shape.getPoints().getY() + shape.getHeight() + 100);
    }
    return new Dimension((int) maxWidth, (int) maxHeight);
  }

  /**
   * Writes the SVG background rectangle to the FileWriter. This background
   * serves as a visual boundary and background for the shapes.
   *
   * @param writer     The FileWriter instance used for writing to the file.
   * @param dimensions The dimensions of the SVG canvas.
   * @throws IOException if an I/O error occurs during writing.
   */
  private void writeBackground(Writer writer, Dimension dimensions) throws IOException {
    writer.write(String.format("<rect width='%f' height='%f' fill='rgb(137,207,240)' "
            + "stroke='red' stroke-width='5'/>\n", dimensions.getWidth(), dimensions.getHeight()));
  }

  /**
   * Writes the header information for a snapshot into the FileWriter. The header includes
   * the timestamp and description of the snapshot, formatted as text within the SVG.
   *
   * @param writer   The FileWriter instance used for writing to the file.
   * @param snapshot The snapshot whose information is to be written.
   * @throws IOException if an I/O error occurs during writing.
   */
  private void writeHeader(Writer writer, Snapshot snapshot) throws IOException {
    writer.write(String.format("<text x='10' y='20' fill='black' "
            + "style='font-weight:bold; font-size:20px;'>%s</text>\n", snapshot.getTimestamp()));
    writer.write(String.format("<text x='10' y='45' fill='black' "
            + "style='font-size:16px;'>Description: %s</text>\n", snapshot.getDescription()));
  }

  /**
   * Converts a Rectangle shape into an SVG rectangle element string.
   *
   * @param rectangle The Rectangle object to be converted.
   * @return A string representing the Rectangle object in SVG format.
   */
  private String convertRectangleToSVG(Rectangle rectangle) {
    int xMarginOffset = 3;
    int yMarginOffset = 63;
    double x = rectangle.getPoints().getX() + xMarginOffset;
    double y = rectangle.getPoints().getY() + yMarginOffset;
    return String.format("<rect x='%f' y='%f' width='%f' height='%f' " +
            "style='fill:rgb(%d,%d,%d)'/>\n", x, y, rectangle.getWidth(),
            rectangle.getHeight(), (int) rectangle.getColor().getR(),
            (int) rectangle.getColor().getG(),
            (int) rectangle.getColor().getB());
  }

  /**
   * Converts an Oval shape into an SVG ellipse element string.
   *
   * @param oval The Oval object to be converted.
   * @return A string representing the Oval object in SVG format.
   */
  private String convertOvalToSVG(Oval oval) {
    int xMarginOffset = 3;
    int yMarginOffset = 63;
    double x = oval.getPoints().getX() + oval.getWidth() / 2 + xMarginOffset;
    double y = oval.getPoints().getY() + oval.getHeight() / 2 + yMarginOffset;
    return String.format("<ellipse cx='%f' cy='%f' rx='%f' ry='%f' style='fill:rgb(%d,%d,%d)'/>\n",
            x, y, oval.getWidth() / 2, oval.getHeight() / 2, (int) oval.getColor().getR(),
            (int) oval.getColor().getG(), (int) oval.getColor().getB());
  }

  /**
   * Converts an IShape instance into its SVG representation. This method delegates
   * to specific methods based on the instance type of the shape.
   *
   * @param shape The shape to be converted to SVG format.
   * @return A string containing the SVG representation of the shape.
   * @throws IllegalArgumentException if the shape type is not supported.
   */
  private String convertShapeToSVG(IShape shape) {
    if (shape instanceof Rectangle) {
      return convertRectangleToSVG((Rectangle) shape);
    } else if (shape instanceof Oval) {
      return convertOvalToSVG((Oval) shape);
    }
    throw new IllegalArgumentException("Unsupported shape type: "
            + shape.getClass().getSimpleName());
  }
}
