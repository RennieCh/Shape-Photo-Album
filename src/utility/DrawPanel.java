package utility;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import model.IShape;

/**
 * DrawPanel is a custom JPanel designed to display shapes stored in a list.
 * It extends JPanel to override the paintComponent method, allowing for custom drawing of shapes.
 * This panel dynamically calculates its preferred size based on the dimensions of the shapes it
 * contains, ensuring that all shapes are visible within the panel.
 */
public class DrawPanel extends JPanel {
  private List<IShape> shapes;

  /**
   * Sets the list of shapes to be drawn on this panel.
   * Upon setting, it automatically updates the panel's preferred size to ensure all
   * shapes are visible.
   *
   * @param shapes A list of shapes implementing the IShape interface.
   */
  public void setShapes(List<IShape> shapes) {
    this.shapes = shapes;
    updatePreferredSize();
  }

  /**
   * Calculates and updates the preferred size of the panel based on the dimensions and positions
   * of the shapes it contains. This ensures that the panel is large enough to display all shapes.
   */
  private void updatePreferredSize() {
    int maxWidth = 0;
    int maxHeight = 0;
    for (IShape shape : shapes) {
      maxWidth = Math.max(maxWidth, (int) shape.getPoints().getX() + (int) shape.getWidth());
      maxHeight = Math.max(maxHeight, (int) shape.getPoints().getY() + (int) shape.getHeight());
    }
    Dimension preferredSize = new Dimension(maxWidth, maxHeight);
    setPreferredSize(preferredSize);
  }

  /**
   * Overrides the JPanel's paintComponent method to custom draw the list of shapes on the panel.
   * Each shape is drawn using its own draw method, allowing for polymorphic rendering of shapes.
   *
   * @param g The Graphics object to protect.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (shapes != null) {
      for (IShape shape : shapes) {
        shape.draw(g); // Use the adapter method for drawing each shape
      }
    }
  }
}
