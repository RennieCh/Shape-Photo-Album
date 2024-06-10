package model;

import java.awt.*;

/**
 * The IShape interface represents a generic shape with basic properties and behaviors
 * that all concrete shapes must implement.
 */
public interface IShape {

  /**
   * Moves the shape to a new location.
   *
   * @param x The x-coordinate of the new location.
   * @param y The y-coordinate of the new location.
   */
  void move(double x, double y);

  /**
   * Changes the color of the shape.
   *
   * @param red    The red color value of the shape.
   * @param green   The green color value of the shape.
   * @param blue    The blue color value of the shape.
   */
  void changeColor(double red, double green, double blue);

  /**
   * Gets the current position of the shape as a Point2D object.
   *
   * @return The current position of the shape.
   */
  Point2D getPoints();

  /**
   * Gets the width of the shape.
   *
   * @return The width of the shape.
   */
  double getWidth();

  /**
   * Sets the width of the shape.
   *
   * @param width The new width to set for the shape.
   */
  void setWidth(double width);

  /**
   * Gets the height of the shape.
   *
   * @return The height of the shape.
   */
  double getHeight();

  /**
   * Sets the height of the shape.
   *
   * @param height The new height to set for the shape.
   */
  void setHeight(double height);

  /**
   * Gets the location type of the shape.
   * Location is where the shape's position is defined/drawn.
   *
   * @return The location type of the shape.
   */
  Location getLocation();

  /**
   * Sets the location of the shape.
   *
   * @param location The new location to set for the shape.
   */
  void setLocation(Location location);

  /**
   * Gets the color of the shape.
   *
   * @return The current color of the shape.
   */
  Color getColor();

  /**
   * Gets the name of the shape.
   *
   * @return The name of the shape.
   */
  String getName();

  /**
   * Clone a given shape
   *
   * @return An IShape instance.
   */
  IShape cloneShape();

  /**
   * Draws the shape onto a graphical context. This method is responsible for rendering
   * the shape using the provided Graphics object. The actual appearance of the shape
   * (e.g., color, size, position) is determined by its current properties.
   *
   * @param g The Graphics context on which the shape will be drawn.
   */
  void draw(Graphics g);
}
