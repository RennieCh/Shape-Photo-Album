package model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * PhotoAlbum model manages a collection of shapes and their snapshots.
 * It is a singleton class that provides functionality to add and transform shapes,
 * as well as to capture and list the states of shapes at different times.
 */
public class PhotoAlbumModel implements IPhotoAlbumModel {
  private static final PhotoAlbumModel INSTANCE = new PhotoAlbumModel();
  private static final Clock THIS_CLOCK = Clock.systemUTC();
  private final List<Snapshot> snapshots;
  private List<IShape> shapes;

  /**
   * Private constructor for the singleton pattern.
   */
  private PhotoAlbumModel() {
    this.snapshots = new ArrayList<>();
    this.shapes = new ArrayList<>();
  }

  /**
   * Provides access to the single instance of the PhotoAlbum.
   *
   * @return The singleton instance of the PhotoAlbum.
   */
  public static PhotoAlbumModel getInstance() {
    return INSTANCE;
  }

  /**
   * Resets the photo album, clearing all shapes and snapshots.
   */
  @Override
  public void reset() {
    snapshots.clear();
    shapes.clear();
  }

  /**
   * Creates a shape of the specified type and parameters.
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
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  @Override
  public IShape createShape(ShapeType type, String name, double red, double green, double blue,
                            Location location, double x, double y, double width, double height)
          throws IllegalArgumentException {
    if (type == null || name == null || name.isEmpty() || red < 0 || green < 0 || blue < 0
            || location == null || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid parameters.");
    }
    switch (type) {
      case OVAL:
        return new Oval(name, red, green, blue, location, x, y, width, height);
      case RECTANGLE:
        return new Rectangle(name, red, green, blue, location, x, y, width, height);
      default:
        throw new IllegalArgumentException("Unknown shape type.");
    }
  }

  /**
   * Adds a shape to the photo album if it is not already present.
   *
   * @param shape The shape to add to the album.
   */
  @Override
  public void addShape(IShape shape) {
    if (!shapes.contains(shape)) {
      shapes.add(shape);
    }
  }

  /**
   * Private method to apply transformations to shapes.
   *
   * @param name           The name of the shape.
   * @param transformation the transformation want to apply.
   */
  private void applyTransformation(String name, Consumer<IShape> transformation) {
    shapes.stream().filter(shape -> shape.getName().equals(name)).forEach(transformation);
  }

  /**
   * Changes the color of a shape identified by name.
   *
   * @param name     The name of the shape to change color.
   * @param red    The red color value of the shape.
   * @param green   The green color value of the shape.
   * @param blue    The blue color value of the shape.
   */
  @Override
  public void changeShapeColor(String name, double red, double green, double blue) {
    applyTransformation(name, shape -> shape.changeColor(red, green, blue));
  }

  /**
   * Moves a shape identified by name to new coordinates.
   *
   * @param name The name of the shape to move.
   * @param newX The new x-coordinate for the shape.
   * @param newY The new y-coordinate for the shape.
   */
  @Override
  public void moveShape(String name, double newX, double newY) {
    applyTransformation(name, shape -> shape.move(newX, newY));
  }

  /**
   * Removes a shape from the album based on its name.
   *
   * @param name The name of the shape to remove.
   */
  @Override
  public void removeShape(String name) {
    shapes = shapes.stream().filter(shape -> !shape.getName().equals(name))
            .collect(Collectors.toList());
  }

  /**
   * Resizes a shape identified by name.
   *
   * @param name      The name of the shape to resize.
   * @param newWidth  The new width for the shape.
   * @param newHeight The new height for the shape.
   */
  @Override
  public void resizeShape(String name, double newWidth, double newHeight) {
    applyTransformation(name, shape -> {
      shape.setWidth(newWidth);
      shape.setHeight(newHeight);
    });
  }

  /**
   * Retrieves a shape by its name.
   *
   * @param name The name of the shape to retrieve.
   * @return The shape with the specified name, or null if no such shape exists on the album.
   */
  @Override
  public IShape getShapeByName(String name) {
    for (IShape shape : shapes) {
      if (shape.getName().equals(name)) {
        return shape;
      }
    }
    return null; // Return null if no shape matches the given name.
  }

  /**
   * Captures a snapshot of the current state of all shapes in the Photo album.
   *
   * @param description A description of the snapshot.
   */
  @Override
  public void takeSnapshot(String description) {
    List<IShape> shapesCopy = this.shapes.stream().map(IShape::cloneShape)
            .collect(Collectors.toList());
    snapshots.add(new Snapshot(shapesCopy, description, THIS_CLOCK));
  }

  /**
   * Retrieves a snapshot from the photo album based on the specified timestamp.
   *
   * @param timestamp The LocalDateTime instance
   * @return A Snapshot object.
   */
  @Override
  public Snapshot getSnapshotByID(LocalDateTime timestamp) {
    for (Snapshot snapshot : snapshots) {
      if (snapshot.getTimestamp().equals(timestamp)) {
        return snapshot;
      }
    }
    return null;
  }

  /**
   * Returns an unmodifiable list of all snapshots taken.
   *
   * @return An unmodifiable list of snapshots.
   */
  @Override
  public List<Snapshot> listSnapshots() {
    return Collections.unmodifiableList(snapshots);
  }

  /**
   * Returns an unmodifiable list of all current shapes.
   *
   * @return An unmodifiable list of shapes.
   */
  @Override
  public List<IShape> getListShapes() {
    return Collections.unmodifiableList(shapes);
  }

  /**
   * Retrieves a list of shapes that satisfy a specified predicate condition.
   * This method allows for flexible filtering of the shapes stored in the photo album.
   *
   * @param pq predicate defining the condition that shapes must meet.
   * @return A List of IShape that meet the condition.
   * @throws IllegalArgumentException If the predicate is null. indicating that a valid
   *                                  filtering condition has not been provided.
   */
  public List<IShape> getFilteredShapesList(Predicate<IShape> pq) throws IllegalArgumentException {
    if (pq == null) {
      throw new IllegalArgumentException("Predicate cannot be null.");
    }
    return Collections.unmodifiableList(shapes.stream().filter(pq).collect(Collectors.toList()));
  }

  /**
   * Retrieves a list of timestamps for all snapshots currently stored in the photo album.
   * This method provides a read-only view of the snapshot timestamps.
   *
   * @return An unmodifiable list of LocalDateTime Instances.
   */
  @Override
  public List<LocalDateTime> listSnapshotsTimestamp() {
    return Collections.unmodifiableList(snapshots.stream().map(Snapshot::getTimestamp)
            .collect(Collectors.toList()));
  }

  /**
   * Compiles and returns the details of all snapshots in the album.
   *
   * @return A string containing the details of all snapshots.
   */
  @Override
  public String printAllSnapshots() {
    StringBuilder sb = new StringBuilder();
    sb.append("Printing Snapshots\n");
    for (Snapshot snapshot : snapshots) {
      sb.append(snapshot.toString()).append("\n");
    }
    return sb.toString();
  }

  /**
   * Returns a string representation of the photo album showing all snapshot timestamps.
   *
   * @return A string listing all snapshot timestamps.
   */
  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    String snapshotTimestamps = snapshots.stream().map(snapshot -> snapshot.getTimestamp()
            .format(formatter)).collect(Collectors.joining(", ", "[", "]"));

    return "List of snapshots taken before reset: " + snapshotTimestamps;
  }

  /**
   * Retrieves the snapshot immediately preceding the given snapshot.
   *
   * @param currentSnapshot The current snapshot as a reference point.
   * @return The previous snapshot, or null if the current snapshot is the first one.
   */
  @Override
  public Snapshot getPreviousSnapshot(Snapshot currentSnapshot) {
    return snapshots.stream()
            .filter(snapshot -> snapshot.getTimestamp().isBefore(currentSnapshot.getTimestamp()))
            .max(Comparator.comparing(Snapshot::getTimestamp))
            .orElse(null);
  }

  /**
   * Retrieves the snapshot immediately following the given snapshot.
   *
   * @param currentSnapshot The current snapshot as a reference point.
   * @return The next snapshot, or null if the current snapshot is the last one.
   */
  @Override
  public Snapshot getNextSnapshot(Snapshot currentSnapshot) {
    return snapshots.stream()
            .filter(snapshot -> snapshot.getTimestamp().isAfter(currentSnapshot.getTimestamp()))
            .min(Comparator.comparing(Snapshot::getTimestamp))
            .orElse(null);
  }
}
