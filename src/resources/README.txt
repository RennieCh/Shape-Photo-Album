Photo Album Application

This README outlines the design and the changes in the Photo Album application. This Photo Album
application enables users to view, navigate, and interact with snapshots collections of various
shapes representing different states, and the recent updates is aid to enhance user interaction and
model manipulation.

Design Overview

In the Photo Album Application, interfaces are used extensively to define the roles and
capabilities of various components. It employs a Model-View-Controller (MVC) architecture to
separate concerns, improve code maintainability, and facilitate user interaction.

Key components include:

- Models(PhotoAlbumModel): Manages the data, logic, and rules of the application. It stores
shapes and snapshots, allowing for operations like add, remove, and transform shapes, as well as
capturing and navigating through snapshots.

- Views(WebView, GraphicalView): Responsible for presenting the data from the model to the user in a
graphical (Swing-based) or web-based format. Views listen for changes in the model and update the
display accordingly.

- Controllers(PhotoAlbumController, WebAlbumController): Acts as an intermediary between the model
and view, handling user input and updating the model or view as necessary.

Recent Design Changes

- Enhanced Shape Rendering: An addition is the draw() method in the IShape interface. This method
enables shapes to be rendered directly onto a graphical context.

- Snapshot Navigation: The getPreviousSnapshot() and getNextSnapshot() methods were added to
the IPhotoAlbumModel interface. These methods enhance the user's ability to navigate between
snapshots.

Conclusion

By leveraging the MVC architecture, interfaces, mock classes for testing, and utility classes for
common functionalities, the Photo Album application promotes modularity, flexibility, and
maintainability. Recent enhancements, such as the draw() method for shapes and improved snapshot
navigation, significantly also enrich user interaction.
