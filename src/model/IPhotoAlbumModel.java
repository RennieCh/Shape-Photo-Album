package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

/**
 * The IPhotoAlbumModel interface defines the operations for managing a collection of shapes
 * and their states (snapshots) within a photo album model. It supports adding, modifying,
 * and removing shapes, as well as capturing the current state of shapes in snapshots.
 */
public interface IPhotoAlbumModel {

  /**
   * Resets the photo album by clearing all shapes and snapshots.
   */
  void reset();

  /**
   * Creates and returns a shape with the specified properties.
   *
   * @param type     The type of shape to create.
   * @param name     The name of the shape.
   * @param red    The red color value of the shape.
   * @param green   The green color value of the shape.
   * @param blue    The blue color value of the shape.
   * @param location The location reference for the shape.
   * @param x        The x-coordinate of the shape's position.
   * @param y        The y-coordinate of the shape's position.
   * @param width    The width of the shape.
   * @param height   The height of the shape.
   * @return The newly created shape.
   */
  IShape createShape(ShapeType type, String name, double red, double green, double blue,
                     Location location, double x, double y, double width, double height);

  /**
   * Adds a shape to the photo album.
   *
   * @param shape The shape to add.
   */
  void addShape(IShape shape);

  /**
   * Changes the color of a shape identified by name.
   *
   * @param name     The name of the shape to modify.
   * @param red    The red color value of the shape.
   * @param green   The green color value of the shape.
   * @param blue    The blue color value of the shape.
   */
  void changeShapeColor(String name, double red, double green, double blue);

  /**
   * Moves a shape identified by name to new coordinates.
   *
   * @param name The name of the shape to move.
   * @param newX The new x-coordinate for the shape.
   * @param newY The new y-coordinate for the shape.
   */
  void moveShape(String name, double newX, double newY);

  /**
   * Resizes a shape identified by name.
   *
   * @param name      The name of the shape to resize.
   * @param newWidth  The new width for the shape.
   * @param newHeight The new height for the shape.
   */
  void resizeShape(String name, double newWidth, double newHeight);

  /**
   * Removes a shape from the album based on its name.
   *
   * @param name The name of the shape to remove.
   */
  void removeShape(String name);

  /**
   * Retrieves a shape by its name.
   *
   * @param name The name of the shape to retrieve.
   * @return The shape with the specified name, or null if it does not exist.
   */
  IShape getShapeByName(String name);

  /**
   * Retrieves a snapshot by its timestamp.
   *
   * @param timestamp The timestamp of the snapshot to retrieve.
   * @return The snapshot with the specified timestamp, or null if it does not exist.
   */
  Snapshot getSnapshotByID(LocalDateTime timestamp);

  /**
   * Captures the current state of all shapes in the photo album and stores it as a snapshot.
   *
   * @param description A description of the snapshot.
   */
  void takeSnapshot(String description);

  /**
   * Lists all snapshots taken.
   *
   * @return A list of all snapshots.
   */
  List<Snapshot> listSnapshots();

  /**
   * Lists all shapes currently in the photo album.
   *
   * @return A list of all shapes.
   */
  List<IShape> getListShapes();

  /**
   * Filters and returns a list of shapes that match the given predicate.
   *
   * @param pq The predicate to apply to each shape.
   * @return A list of shapes that match the predicate.
   */
  List<IShape> getFilteredShapesList(Predicate<IShape> pq);

  /**
   * Lists the timestamps of all snapshots taken.
   *
   * @return A list of timestamps for all snapshots.
   */
  List<LocalDateTime> listSnapshotsTimestamp();

  /**
   * Compiles and returns the details of all snapshots on the album.
   *
   * @return A string containing the details of all snapshots.
   */
  String printAllSnapshots();

  /**
   * Retrieves the snapshot immediately preceding the given snapshot.
   *
   * @param currentSnapshot The current snapshot as a reference point.
   * @return The previous snapshot, or null if the current snapshot is the first one.
   */
  Snapshot getPreviousSnapshot(Snapshot currentSnapshot);

  /**
   * Retrieves the snapshot immediately following the given snapshot.
   *
   * @param currentSnapshot The current snapshot as a reference point.
   * @return The next snapshot, or null if the current snapshot is the last one.
   */
  Snapshot getNextSnapshot(Snapshot currentSnapshot);
}
