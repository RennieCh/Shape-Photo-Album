package model;

import java.util.Objects;

/**
 * Represents a point in 2-dimensional space with x and y coordinates.
 */
public class Point2D {
  private double x;
  private double y;

  /**
   * Constructs a point at the specified (x, y) location.
   *
   * @param x the x-coordinate of this point.
   * @param y the y-coordinate of this point.
   */
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Default constructor constructs a point at the origin (0,0).
   */
  public Point2D() {
    this(0, 0);
  }

  /**
   * Copy constructor constructs a point with the same location as the given point.
   *
   * @param original the point to copy coordinates from.
   */
  public Point2D(Point2D original) {
    this(original.x, original.y);
  }

  /**
   * Gets the x-coordinate of this point.
   *
   * @return the x-coordinate.
   */
  public double getX() {
    return this.x;
  }

  /**
   * Gets the y-coordinate of this point.
   *
   * @return the y-coordinate.
   */
  public double getY() {
    return this.y;
  }

  /**
   * Moves the point to the specified (x, y) location.
   *
   * @param x the new x-coordinate of this point.
   * @param y the new y-coordinate of this point.
   */
  public void move(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns a string representation of the point in the format "(x,y)".
   *
   * @return a string representation of this point.
   */
  @Override
  public String toString() {
    return "(" + getX() + "," + getY() + ")";
  }

  /**
   * Compares this point2D to the specified object for equality.
   *
   * @param o The object to compare this point2D against.
   * @return true if the given object represents a point equivalent to this point, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point2D point2D = (Point2D) o;
    return Objects.equals(getX(), point2D.x) && Objects.equals(getY(), point2D.y);
  }

  /**
   * Returns a hash code value for the point2D, which is based on the x and y coordinates.
   *
   * @return a hash code value for this point2D.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

}
