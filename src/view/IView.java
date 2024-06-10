package view;

import controller.IController;

/**
 * The IView interface defines the methods that any view in the MVC pattern must implement.
 * This interface ensures that the view can communicate with the controller and update its
 * display based on changes in the model or user interactions.
 */
public interface IView {

  /**
   * Refreshes the view to reflect any changes in the model's state or due to user interaction.
   * This method is typically called after the model has been updated.
   */
  void refresh();

  /**
   * Sets the controller for this view. This method allows the view to communicate with the
   * controller, which handles user actions and updates the model accordingly.
   *
   * @param controller The controller instance that this view will interact with.
   */
  void setController(IController controller);

  /**
   * Getter function to retrieve the controller.
   */
  IController getController();

}


