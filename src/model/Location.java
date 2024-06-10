package model;

/**
 * Represents the possible locations used as reference points for drawing shapes.
 */
public enum Location {
  CORNER, //Indicates that the position of the shape is based on its corner.
  CENTER, //Indicates that the position of the shape is based on its center.
  MIN_CORNER, //Indicates that the position of the shape is based on its minimum corner.
  LEFT_CORNER; //Indicates that the position of the shape is based on its left corner

  /**
   * Provides a textual representation of the location.
   *
   * @return A string that describes the location used for a shape.
   */
  @Override
  public String toString() {
    switch (this) {
      case CORNER:
        return "Corner";
      case CENTER:
        return "Center";
      case MIN_CORNER:
        return "Min Corner";
      case LEFT_CORNER:
        return "Left Corner";
      default:
        throw new IllegalArgumentException("Unknown location to displace a shape.");
    }
  }
}
