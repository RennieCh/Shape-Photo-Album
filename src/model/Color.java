package model;

import java.util.Objects;

/**
 * Represents an RGB color with red, green, and blue components.
 */
public class Color {
  private final double red;
  private final double green;
  private final double blue;
  private static final double MIN_RANGE = 0.0;
  private static final double MAX_RANGE = 255.0;


  /**
   * Creates a color with specified red, green, and blue components.
   *
   * @param red   the red component, clamped between 0.0 and 255.0.
   * @param green the green component, clamped between 0.0 and 255.0.
   * @param blue  the blue component, clamped between 0.0 and 255.0.
   */
  public Color(double red, double green, double blue) {
    this.red = Math.max(MIN_RANGE, Math.min(MAX_RANGE, red)); // Clamp values between 0.0 and 255.0
    this.green = Math.max(MIN_RANGE, Math.min(MAX_RANGE, green));
    this.blue = Math.max(MIN_RANGE, Math.min(MAX_RANGE, blue));
  }

  /**
   * Default constructor that creates the color black.
   */
  public Color() {
    this.red = 0.0;
    this.green = 0.0;
    this.blue = 0.0;
  }

  /**
   * Copy constructor that creates a new Color instance with the same RGB components
   * as the provided color.
   *
   * @param other the Color instance to copy.
   */
  public Color(Color other) {
    this.red = other.red;
    this.green = other.green;
    this.blue = other.blue;
  }

  /**
   * Gets the red component of the color.
   *
   * @return the red component.
   */
  public double getR() {
    return red;
  }

  /**
   * Gets the green component of the color.
   *
   * @return the green component.
   */
  public double getG() {
    return green;
  }

  /**
   * Gets the blue component of the color.
   *
   * @return the blue component.
   */
  public double getB() {
    return blue;
  }

  /**
   * Returns a string representation of the color in the format (red,green,blue).
   *
   * @return a string representation of the color.
   */
  @Override
  public String toString() {
    return String.format("(%.1f,%.1f,%.1f)", red, green, blue);
  }

  /**
   * Compares this color to the specified object for equality.
   *
   * @param o The object to compare this color against.
   * @return true if the given object represents a color equivalent to this color, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Color color = (Color) o;
    return Objects.equals(getR(), color.red)
            && Objects.equals(getG(), color.green)
            && Objects.equals(getB(), color.blue);
  }

  /**
   * Returns a hash code value for the color, which is based on the RGB components.
   *
   * @return a hash code value for this color.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

}
