package controller;

/**
 * The IGraphicalController interface extends IController to define additional
 * behaviors specific to controllers that manage graphical views within the application's
 * Model-View-Controller (MVC) architecture.
 * It introduces a method for handling user actions, which may trigger updates to the model
 * or view based on the nature of the action.
 */
public interface IGraphicalController extends IController {

  /**
   * Handles a request for updating the view based on an action. The specifics of the action
   * are determined by the controller implementation, allowing flexibility for different types
   * of controllers within the application.
   *
   * @param action A String representing the action to be handled, which could be user input
   *               or a request to update the view with new data from the model.
   */
  void handleAction(String action);
}
