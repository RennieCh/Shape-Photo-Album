package model;

import java.awt.*;
import java.util.Locale;

/**
 * Represents a rectangle shape with specific name, color, location, x-coordinate,
 * y-coordinate, and size parameters. It extends from the AbstractShape class.
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructs a rectangle with the given parameters.
   *
   * @param name     The name of the oval, used as an identifier.
   * @param red    The red color value of the shape.
   * @param green   The green color value of the shape.
   * @param blue    The blue color value of the shape.
   * @param location The location type of the oval (e.g., CENTER, CORNER).
   * @param x        The x-coordinate of the oval's position.
   * @param y        The y-coordinate of the oval's position.
   * @param width    The width of the oval (often representing the horizontal radius).
   * @param height   The height of the oval (often representing the vertical radius).
   * @throws IllegalArgumentException if any parameter constraints are violated.
   */
  public Rectangle(String name, double red, double green, double blue, Location location, double x,
                   double y, double width, double height) throws IllegalArgumentException {
    super(name, red, green, blue, location, x, y, width, height);
  }

  /**
   * Copy constructor.
   * @param rectangle A rectangle object to copy from.
   */
  public Rectangle(Rectangle rectangle) {
    super(rectangle);
  }

  /**
   * Clone this rectangle.
   *
   * @return A Rectangle(IShape) instance.
   */
  @Override
  public IShape cloneShape() {
    return new Rectangle(this); // Use the copy constructor
  }

  /**
   * Returns a string representation of the rectangle with its name, type, position, size,
   * and color.
   *
   * @return A string containing the rectangle's details.
   */
  @Override
  public String toString() {
    return "Name: " + getName() + "\n" + "Type: " + getClass().getSimpleName()
            .toLowerCase(Locale.ROOT) + "\n" + getLocation() + ": " + getPoints()
            + ", Width: " + getWidth() + ", Height: " + getHeight()
            + ",\n" + "Color: " + getColor();
  }

  /**
   * Draws this Rectangle onto a graphical context. This method is responsible for rendering
   * the shape using the provided Graphics object. The actual appearance of the shape
   * (e.g., color, size, position) is determined by its current properties.
   *
   * @param g The Graphics context on which the shape will be drawn.
   */
  @Override
  public void draw(Graphics g) {
    // Convert model.Color to java.awt.Color
    model.Color modelColor = this.getColor();
    java.awt.Color awtColor = new java.awt.Color((int) modelColor.getR(),
            (int) modelColor.getG(), (int) modelColor.getB());

    g.setColor(awtColor); // using java.awt.Color
    g.fillRect((int) this.getPoints().getX(), (int) this.getPoints().getY(),
            (int) this.getWidth(), (int) this.getHeight());
  }
}
