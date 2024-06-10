package controller;

import model.IPhotoAlbumModel;
import utility.PhotoAlbumLoader;
import view.IView;

/**
 * The WebAlbumController class is responsible for controlling the interaction
 * between the model IPhotoAlbumModel and the web-based view.
 * It manages the loading of album data from a file and updates the web view accordingly.
 */
public class WebAlbumController implements IController {
  private IPhotoAlbumModel model;
  private IView view;
  private final String filePath;

  /**
   * Constructs a WebAlbumController with the specified model, view, and the file path
   * from which the album data will be loaded.
   *
   * @param model The photo album model to be used by the controller.
   * @param view The web view that will display the album data.
   * @param filePath The path to the file containing the album data.
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  public WebAlbumController(IPhotoAlbumModel model, IView view, String filePath)
          throws IllegalArgumentException {
    if (model == null || view == null || filePath == null || filePath.isEmpty()) {
    throw new IllegalArgumentException("Invalid inputs");
  }
    this.model = model;
    this.view = view;
    this.filePath = filePath;
  }

  /**
   * Loads the album data from the specified file into the model and then refreshes
   * the view to display the latest album data.
   */
  private void loadAlbumData() {
    PhotoAlbumLoader.loadFromFile(filePath, this.model);
  }

  /**
   * Starts the controller by loading the album data and updating the view.
   * It is the entry point for the controller's interaction with the web-based user interface.
   */
  @Override
  public void start() {
    loadAlbumData();
    view.refresh();
  }

  /**
   * Sets the view for this controller. This method links the controller to a specific
   * web-based view, enabling the controller to update the view based on changes to the model.
   *
   * @param view The web view to be managed by this controller.
   * @throws IllegalArgumentException if view is null.
   */
  @Override
  public void setView(IView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    this.view = view;
  }

  /**
   * Retrieves the current model associated with this controller.
   *
   * @return The IPhotoAlbumModel instance that this controller is managing.
   */
  @Override
  public IPhotoAlbumModel getModel() {
    return this.model;
  }

  /**
   * Sets the model for this controller. This method establishes a link between the controller
   * and a specific data model, allowing the controller to manipulate the model based on user
   * actions.
   *
   * @param model The photo album model to be managed by this controller.
   * @throws IllegalArgumentException if model is null.
   */
  @Override
  public void setModel(IPhotoAlbumModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }
}
