
import model.Color;
import model.IPhotoAlbumModel;
import model.IShape;
import model.Location;
import model.PhotoAlbumModel;
import model.ShapeType;

/**
 * Demonstrates the functionality of the Photo Album Model by creating shapes,
 * manipulating their properties, and taking snapshots of their states at various points.
 * This class is designed to simulate a photo album of geometric shapes through a series
 * of transformations and state changes, outputting the state of the model at key points
 * to the console.
 */
public class Main {
  public static void main(String[] args) {

    // This is text output rendering of my model
    System.out.println("Model Simulation Start Here: ");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    // Initialize the photo album model and create shapes.
    IPhotoAlbumModel album = PhotoAlbumModel.getInstance();
    IShape rectangle = album.createShape(ShapeType.RECTANGLE, "R",
            1.0, 0.0, 1.0
            , Location.MIN_CORNER, 200, 200, 50, 100);
    IShape oval = album.createShape(ShapeType.OVAL, "O", 0, 0, 1
            , Location.CENTER, 500, 100, 60, 30);

    // Add shapes to the album.
    album.addShape(rectangle);
    album.addShape(oval);

    // Display the initial state of the shapes.
    System.out.println(album.getShapeByName("R"));
    System.out.println("\n");
    System.out.println(album.getShapeByName("O"));

    // take a snapshot
    album.takeSnapshot("After first selfie");

    // Deliberate delay to ensure a different timestamp
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // transforming the shapes
    album.moveShape("R", 100, 300);
    album.resizeShape("R", 25, 100);
    album.changeShapeColor("R", 0, 1, 0);

    // Display the state of the model after transformations.
    System.out.println("\n");
    System.out.println("(NB: After moving and resizing and changing color of rectangle)");
    System.out.println("\n");
    System.out.println(album.getShapeByName("R"));
    System.out.println("\n");
    System.out.println(album.getShapeByName("O"));

    album.takeSnapshot("2nd selfie");
    // Deliberate delay to ensure a different timestamp
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // moving Oval
    album.moveShape("O", 500, 400);
    System.out.println("\n");
    System.out.println("(NB: After moving Oval)");
    System.out.println("\n");
    System.out.println(album.getShapeByName("R"));
    System.out.println("\n");
    System.out.println(album.getShapeByName("O"));

    album.takeSnapshot("");
    // Deliberate delay to ensure a different timestamp
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // removing rectangle
    album.removeShape("R");
    System.out.println("\n");
    System.out.println("(NB: After removing the rectangle)");
    System.out.println("\n");
    System.out.println(album.getShapeByName("O"));

    album.takeSnapshot("Selfie after removing the rectangle from");
    // Deliberate delay to ensure a different timestamp
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // Print a list of snapshot in album
    System.out.println("\n");
    System.out.println("(NB: Snapshots are similar to \"still pictures\" or "
            + "selfies of the shape system)");
    System.out.println("\n");
    System.out.println(album.toString());

    // Print a list of snapshot detail in album
    System.out.println("\n");
    System.out.println("(NB: Snapshot details)");
    System.out.println("\n");
    System.out.println(album.printAllSnapshots());

    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println("Model Simulation End Here. ");
  }
}