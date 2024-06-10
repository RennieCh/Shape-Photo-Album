package model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a snapshot of the state of a collection of shapes at a specific time.
 * Each snapshot includes a timestamp, a description, and an immutable list of shapes.
 */
public class Snapshot {
  private final LocalDateTime timestamp;
  private final String description;
  private final List<IShape> shapes;

  /**
   * Creates a snapshot with the specified shapes and description,
   * capturing the state at the time determined by the provided Clock.
   * The timestamp is generated based on the provided Clock instance,
   * ensuring flexibility in manipulating time during tests or in different time zones.
   *
   * @param shapes      The list of shapes to include in the snapshot.
   * @param description A text description of the snapshot.
   * @param clock       The clock to use for generating the timestamp of the snapshot.
   * @throws IllegalArgumentException if shapes is null, throw an exception.
   */
  public Snapshot(List<IShape> shapes, String description, Clock clock)
          throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("Invalid shapes input");
    }
    this.timestamp = LocalDateTime.now(clock);
    this.description = description;
    this.shapes = new ArrayList<>(shapes);
  }

  /**
   * Gets the timestamp when the snapshot was taken.
   *
   * @return The timestamp of the snapshot.
   */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Gets an unmodifiable list of the shapes in the snapshot.
   *
   * @return An unmodifiable list of shapes.
   */
  public List<IShape> getShapes() {
    return Collections.unmodifiableList(shapes);
  }

  /**
   * Gets the description of the snapshot.
   *
   * @return The description of the snapshot.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Provides a string representation of the snapshot, including the ID, timestamp,
   * description, and details of all shapes.
   *
   * @return A string representation of the snapshot.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Snapshot ID: ").append(timestamp.toString()).append("\n");
    sb.append("Timestamp: ").append(timestamp.format(DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm:ss"))).append("\n");
    sb.append("Description: ").append(description).append("\n");
    sb.append("Shape Information:\n");
    for (int i = 0; i < shapes.size(); i++) {
      sb.append(shapes.get(i).toString());
      if (i < shapes.size() - 1) {
        // Only add the double newline if it's not the last shape
        sb.append("\n\n");
      } else {
        // Just end with a single newline for the last shape
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Compares this snapshot to the specified object for equality.
   * Two snapshots are considered equal if they have the same timestamp and list of shapes.
   *
   * @param o The object to be compared for equality with this snapshot.
   * @return true if the specified object is equal to this snapshot.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Snapshot snapshot = (Snapshot) o;
    return this.timestamp == snapshot.timestamp && shapes.equals(snapshot.shapes);
  }

  /**
   * Returns a hash code for this snapshot.
   * The hash code is generated from the snapshot's timestamp and list of shapes.
   *
   * @return A hash code value for this snapshot.
   */
  @Override
  public int hashCode() {
    return Objects.hash(timestamp, shapes);
  }
}

