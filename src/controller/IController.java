package controller;

import model.IPhotoAlbumModel;
import view.IView;

/**
 * The IController interface defines the contract for controllers in the application's
 * Model-View-Controller (MVC) architecture. It outlines methods for starting the application,
 * setting and retrieving the model, and establishing a connection with the view.
 */
public interface IController {

  /**
   * Starts the application.
   * It serves as the entry point for the controller's interaction with the user interface.
   */
  void start();

  /**
   * Sets the controller's reference to the view. This allows the controller to communicate
   * with the view.
   *
   * @param view The view instance that this controller will manage.
   */
  void setView(IView view);

  /**
   * Retrieves the IPhotoAlbumModel Instance. This allows external entities
   * to access the current state or properties of the model managed by this controller.
   *
   * @return The model instance associated with this controller.
   */
  IPhotoAlbumModel getModel();

  /**
   * Sets the controller's reference to the model. This method establishes the link between
   * the controller and the model, allowing the controller to request updates or changes to
   * the data based on user actions.
   *
   * @param model The model instance that this controller will interact with.
   */
  void setModel(IPhotoAlbumModel model);
}
