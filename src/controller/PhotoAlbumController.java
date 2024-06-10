package controller;

import model.IPhotoAlbumModel;
import model.Snapshot;
import utility.PhotoAlbumLoader;
import view.IGraphicalView;
import view.IView;

/**
 * The PhotoAlbumController class implements IController and IGraphicalController,
 * serving as the intermediary between the IPhotoAlbumModel and the graphical view.
 * It manages the interaction logic, data loading, and updating the view based on user actions or
 * model changes.
 */
public class PhotoAlbumController implements IController, IGraphicalController {
  private IPhotoAlbumModel model;
  private IGraphicalView view;
  private final String filePath;
  private int currentSnapshotIndex = 0;

  /**
   * Constructs a PhotoAlbumController with the specified model, view, and file path.
   * Immediately upon creation, it loads album data from the specified file path into the model.
   *
   * @param model    The photo album model to be managed.
   * @param view     The graphical view where the model's data will be displayed.
   * @param filePath The path to the file containing the album data.
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  public PhotoAlbumController(IPhotoAlbumModel model, IView view, String filePath) throws
          IllegalArgumentException{
    if (model == null || view == null || filePath == null || filePath.isEmpty()) {
      throw new IllegalArgumentException("Invalid inputs");
    }
    this.model = model;
    this.view = (IGraphicalView) view;
    this.filePath = filePath;
    loadAlbumData();
  }

  /**
   * Loads the album data from the file into the model. This method is called during
   * the controller's initialization to populate the model with data from a persistent storage.
   */
  private void loadAlbumData() {
    PhotoAlbumLoader.loadFromFile(filePath, this.model);
    if (!model.listSnapshots().isEmpty()) {
      // Display the first snapshot on startup
      displaySnapshot(0);
    }
  }

  /**
   * Starts the controller by displaying the initial state in the view.
   * Typically, this will display the first snapshot if available.
   */
  @Override
  public void start() {
    if (!model.listSnapshots().isEmpty()) {
      displaySnapshot(0);
    }
  }

  /**
   * Handles various actions initiated by the user in the graphical interface.
   * This includes navigating to the next or previous snapshot or selecting a specific snapshot.
   *
   * @param action A string indicating the action to be handled.
   * @throws IllegalArgumentException if action is invalid.
   */
  @Override
  public void handleAction(String action) throws IllegalArgumentException {
    if (action == null || action.isEmpty()) {
      throw new IllegalArgumentException("Action cannot be null or empty.");
    }
    if (action.startsWith("NEXT_SNAPSHOT")) {
      showNextSnapshot();
    } else if (action.startsWith("PREVIOUS_SNAPSHOT")) {
      showPreviousSnapshot();
    } else if (action.startsWith("SELECT_SNAPSHOT")) {
      String timestamp = action.substring("SELECT_SNAPSHOT:".length());
      selectSnapshotByTimestamp(timestamp);
    } else {
      System.out.println("Unhandled action: " + action);
    }
  }

  /**
   * Sets the view for this controller. This method links the controller with a graphical interface,
   * enabling the controller to update the view based on model changes or user actions.
   *
   * @param view The graphical view to be used by this controller.
   * @throws IllegalArgumentException if view is null.
   */
  @Override
  public void setView(IView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    this.view = (IGraphicalView) view;
  }

  /**
   * Gets the current model associated with this controller.
   *
   * @return The current IPhotoAlbumModel instance managed by this controller.
   */
  @Override
  public IPhotoAlbumModel getModel() {
    return this.model;
  }

  /**
   * Sets the model for this controller. This method links the controller with the data model,
   * allowing the controller to manipulate the model data based on user actions.
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

  /**
   * Selects and displays a snapshot based on its timestamp.
   *
   * @param timestampStr The string representation of the snapshot's timestamp.
   */
  private void selectSnapshotByTimestamp(String timestampStr) {

    for (int i = 0; i < model.listSnapshots().size(); i++) {
      Snapshot snapshot = model.listSnapshots().get(i);
      if (snapshot.getTimestamp().toString().equals(timestampStr)) {
        currentSnapshotIndex = i;
        displaySnapshot(i);
        return;
      }
    }
    view.showErrorMessage("Snapshot with the given timestamp not found.");
  }

  /**
   * Displays the next snapshot in the shape photo album, if available.
   */
  private void showNextSnapshot() {
    if (currentSnapshotIndex < model.listSnapshots().size() - 1) {
      currentSnapshotIndex++;
      displaySnapshot(currentSnapshotIndex);
    } else {
      view.showErrorMessage("No next snapshot available.");
    }
  }

  /**
   * Displays the previous snapshot in the Photo album, if available.
   */
  private void showPreviousSnapshot() {
    if (currentSnapshotIndex > 0) {
      currentSnapshotIndex--;
      displaySnapshot(currentSnapshotIndex);
    } else {
      view.showErrorMessage("No previous snapshot available.");
    }
  }

  /**
   * Displays the snapshot at the specified index in the Photo album.
   *
   * @param index The index of the snapshot to display.
   */
  private void displaySnapshot(int index) {
    Snapshot snapshot = model.listSnapshots().get(index);
    view.displaySnapshot(snapshot);
  }
}
