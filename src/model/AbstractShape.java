package model;

import java.awt.*;
import java.util.Objects;

/**
 * AbstractShape provides a skeletal implementation of the IShape interface.
 */
public abstract class AbstractShape implements IShape {
  private final Point2D point2D;
  private final String name; // as the primary key for a shape equality
  private Color color;
  private Location location;
  private double width;
  private double height;

  /**
   * Constructs an abstract shape with the specified name, color, location, position, width,
   * and height.
   *
   * @param name     The name of the shape, used as an identifier.
   * @param red    The red color value of the shape.
   * @param green   The green color value of the shape.
   * @param blue    The blue color value of the shape.
   * @param location The location type of the shape.
   * @param x        The x-coordinate of the shape's position.
   * @param y        The y-coordinate of the shape's position.
   * @param width    The width of the shape.
   * @param height   The height of the shape.
   * @throws IllegalArgumentException if any parameter constraints are violated.
   */
  public AbstractShape(String name, double red, double green, double blue, Location location, double x,
                       double y, double width, double height) throws IllegalArgumentException {
    if (name == null || name.isEmpty() || red < 0 || green < 0 || blue < 0
            || location == null || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid parameters.");
    }
    this.name = name;
    this.color = new Color(red, green, blue);
    this.location = location;
    this.point2D = new Point2D(x, y);
    this.width = width;
    this.height = height;
  }

  /**
   * Copy constructor, creates a new instance of an AbstractShape based on another shape instance.
   *
   * @param shape The AbstractShape instance from which to copy properties.
   */
  public AbstractShape(AbstractShape shape) {
    this.name = shape.name;
    this.color = new Color(shape.color); //deep copy
    this.location = shape.location;
    this.point2D = new Point2D(shape.point2D.getX(), shape.point2D.getY()); // deep copy
    this.width = shape.width;
    this.height = shape.height;
  }

  @Override
  public void move(double x, double y) {
    point2D.move(x, y);
  }

  @Override
  public void changeColor(double red, double green, double blue) {
    this.color = new Color(red, green, blue);
  }

  @Override
  public Point2D getPoints() {
    return this.point2D;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public void setWidth(double width) {
    this.width = width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public void setHeight(double height) {
    this.height = height;
  }

  @Override
  public Location getLocation() {
    return this.location;
  }

  @Override
  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public abstract IShape cloneShape();

  @Override
  public abstract void draw(Graphics g);

  /**
   * Compares this shape to the specified object for equality.
   * The equality is based on the name of the shape.
   *
   * @param o The object to compare this shape against.
   * @return true if the given object represents a shape equivalent to this shape, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractShape shape = (AbstractShape) o;
    return Objects.equals(name, shape.name);
  }

  /**
   * Returns a hash code value for the shape, which is based on the shape's name.
   *
   * @return a hash code value for this shape.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

}
