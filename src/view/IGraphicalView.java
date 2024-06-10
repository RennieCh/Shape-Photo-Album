package view;

import model.Snapshot;

/**
 * The IGraphicalView interface extends IView to define specific behaviors for
 * graphical views within the application's Model-View-Controller (MVC) architecture.
 * It introduces methods for displaying snapshots and showing error messages directly within
 * a graphical user interface, enhancing the user's interaction with the application.
 */
public interface IGraphicalView extends IView {

  /**
   * Displays a specific snapshot within the view. This method allows the controller to
   * command the view to render a particular state of the model, such as a snapshot from a
   * photo album.
   *
   * @param snapshot The snapshot to display.
   */
  void displaySnapshot(Snapshot snapshot);

  /**
   * Shows an error message to the user. This method can be used by the controller to inform the
   * user of errors or issues, such as trying to navigate beyond the available range of snapshots.
   *
   * @param message The error message to display.
   */
  void showErrorMessage(String message);

}
