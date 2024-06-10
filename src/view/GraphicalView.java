package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import controller.IController;
import controller.IGraphicalController;
import model.Snapshot;
import utility.DrawPanel;

/**
 * The GraphicalView class is an implementation of IView and IGraphicalView interfaces,
 * providing a graphical user interface (GUI) for displaying snapshots and allowing user interaction
 * within the photo album application. It extends JFrame to utilize Swing components
 * for rendering the UI elements.
 */
public class GraphicalView extends JFrame implements IView, IGraphicalView {
  private IGraphicalController controller;
  private DrawPanel drawPanel;
  private JScrollPane scrollPane;
  private JButton previous;
  private JButton next;
  private JButton select;
  private JButton quit;
  private JLabel snapshotID;

  /**
   * Constructs a GraphicalView with specified dimensions for the display area.
   *
   * @param width  the width of the display area.
   * @param height the height of the display area.
   */
  public GraphicalView(int width, int height) {
    initializeUI(width, height);
  }

  /**
   * Initializes the user interface including setting up the frame, labels, drawing panel,
   * scroll pane, and buttons.
   *
   * @param width  the width of the display area.
   * @param height the height of the display area.
   */
  private void initializeUI(int width, int height) {
    setupFrame();
    setupSnapshotIDLabel();
    setupDrawPanel();
    setupScrollPane(width, height);
    setupButtons();
    finalizeSetup();
  }

  /**
   * Sets up the main JFrame properties for the graphical view.
   */
  private void setupFrame() {
    setTitle("CS5004 Shape Photo Album Graphical View");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
  }

  /**
   * Initializes and adds the snapshot ID label to the frame.
   */
  private void setupSnapshotIDLabel() {
    snapshotID = new JLabel("", SwingConstants.LEFT);
    snapshotID.setBackground(new Color(244, 194, 194));
    snapshotID.setOpaque(true);
    getContentPane().add(snapshotID, BorderLayout.PAGE_START);
  }

  /**
   * Initializes the drawing panel where shapes will be rendered.
   */
  private void setupDrawPanel() {
    drawPanel = new DrawPanel();
    drawPanel.setBackground(new Color(137, 207, 240));
  }

  /**
   * Sets up the scroll pane that will contain the drawing panel.
   *
   * @param width  The preferred width of the scroll pane.
   * @param height The preferred height of the scroll pane.
   */
  private void setupScrollPane(int width, int height) {
    scrollPane = new JScrollPane(drawPanel);
    scrollPane.setPreferredSize(new Dimension(width, height));
    getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * Finalizes the UI setup by packing the JFrame and making it visible.
   */
  private void finalizeSetup() {
    pack();
    setVisible(true);
  }

  /**
   * Initializes the buttons used for navigation and interaction within the UI.
   */
  private void setupButtons() {
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(Color.ORANGE);

    previous = createButton(" << Prev << ", e -> controller.handleAction("PREVIOUS_SNAPSHOT"));
    next = createButton(" >> Next >>", e -> controller.handleAction("NEXT_SNAPSHOT"));
    select = createButton(" ^^ Select ^^ ", e -> selectSnapshot());
    quit = createButton(" xx Quit xx ", e -> System.exit(0));

    addButtonsToPanel(buttonPanel, previous, next, select, quit);
    getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
  }

  /**
   * Creates a JButton with the specified text and action listener.
   *
   * @param text           The text to display on the button.
   * @param actionListener The action listener to attach to the button.
   * @return The created JButton.
   */
  private JButton createButton(String text, ActionListener actionListener) {
    JButton button = new JButton(text);
    button.addActionListener(actionListener);
    return button;
  }

  /**
   * Adds the given buttons to the specified panel.
   *
   * @param panel   The panel to which buttons will be added.
   * @param buttons The buttons to add to the panel.
   */
  private void addButtonsToPanel(JPanel panel, JButton... buttons) {
    for (JButton button : buttons) {
      panel.add(button);
    }
  }

  /**
   * Sets the controller for this view. This controller manages the interactions
   * and updates between the model and this graphical view.
   *
   * @param controller the controller that manages this view.
   */
  @Override
  public void setController(IController controller) {
    this.controller = (IGraphicalController) controller;
  }

  /**
   * Getter function to retrieve the controller.
   */
  @Override
  public IController getController() {
    return this.controller;
  }

  /**
   * Displays a specific snapshot on the graphical view. Updates the drawing panel
   * with the shapes from the snapshot and updates the snapshot information label.
   *
   * @param snapshot the snapshot to display.
   */
  @Override
  public void displaySnapshot(Snapshot snapshot) {
    // Use HTML tags to support multiline text in JLabel
    snapshotID.setText("<html>" + snapshot.getTimestamp().toString()
            + "<br/>" + snapshot.getDescription() + "</html>");

    drawPanel.setShapes(snapshot.getShapes());
    drawPanel.revalidate();
    drawPanel.repaint();
  }

  /**
   * Shows an error message dialog in the graphical view. Used for displaying
   * error or informational messages to the user.
   *
   * @param message the error message to display.
   */
  @Override
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Triggers the snapshot selection process by displaying a combo box with available snapshots.
   */
  private void selectSnapshot() {
    JComboBox<Snapshot> snapshotComboBox = createSnapshotComboBox();
    int result = showSnapshotSelectionDialog(snapshotComboBox);

    if (result == JOptionPane.OK_OPTION) {
      Snapshot selectedSnapshot = (Snapshot) snapshotComboBox.getSelectedItem();
      if (selectedSnapshot != null) {
        controller.handleAction("SELECT_SNAPSHOT:" + selectedSnapshot.getTimestamp().toString());
      }
    }
  }

  /**
   * Creates a combo box filled with snapshots available in the model.
   *
   * @return The created JComboBox containing Snapshot objects.
   */
  private JComboBox<Snapshot> createSnapshotComboBox() {
    // Create a combo box and add snapshot items to it
    JComboBox<Snapshot> snapshotComboBox = new JComboBox<>();
    List<Snapshot> snapshots = controller.getModel().listSnapshots();
    for (Snapshot snapshot : snapshots) {
      snapshotComboBox.addItem(snapshot);
    }

    // Set a custom renderer to display the timestamp as the item text
    snapshotComboBox.setRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
                                                    int index, boolean isSelected,
                                                    boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Snapshot snapshot) {
          setText(snapshot.getTimestamp().toString());
        }
        return this;
      }
    });
    return snapshotComboBox;
  }

  /**
   * Shows a dialog box for selecting a snapshot from a JComboBox of snapshots.
   *
   * @param snapshotComboBox The JComboBox containing the snapshots to select from.
   * @return The JOptionPane selection result.
   */
  private int showSnapshotSelectionDialog(JComboBox<Snapshot> snapshotComboBox) {
    return JOptionPane.showConfirmDialog(null, snapshotComboBox,
            "Select Snapshot", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
  }

  /**
   * Method to refresh the view, can be used to repaint the frame and its components.
   */
  @Override
  public void refresh() {
    repaint();
  }
}
