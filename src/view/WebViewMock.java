package view;

import java.io.StringWriter;
import java.io.Writer;

/**
 * Extends the WebView class to create a mock version for testing purposes. This mock class
 * overrides methods related to file writing to capture output in a string format rather than
 * writing to the filesystem. This allows tests to inspect the generated HTML content directly.
 */
public class WebViewMock extends WebView {
  private StringWriter stringWriter;

  /**
   * Constructs a new instance of WebViewMock with a specified output path.
   * The outputPath is provided to the superclass constructor but is not used for
   * file operations in this mock.
   *
   * @param outputPath The path where the HTML file would nominally be saved.
   */
  public WebViewMock(String outputPath) {
    super(outputPath);
  }

  /**
   * Overrides the generateHTMLFile method to capture generated HTML content in a
   * StringWriter instead of writing to a physical file.
   *
   * @param outputPath The path where the HTML file is saved (unused in this mock).
   */
  @Override
  protected void generateHTMLFile(String outputPath) {
    // Instead of writing to a file, write to a StringWriter to capture output.
    stringWriter = new StringWriter();
    super.generateHTMLFile(outputPath);
  }

  /**
   * Overrides the method to obtain a Writer instance, returning a StringWriter instead
   * of a FileWriter. This change ensures that HTML content is captured in memory.
   *
   * @param outputPath The path where the HTML file would be saved (unused in this mock).
   * @return A StringWriter to capture HTML content.
   */
  @Override
  protected Writer getWriter(String outputPath) {
    // Ensure stringWriter is initialized
    if (stringWriter == null) {
      stringWriter = new StringWriter();
    }
    return stringWriter;
  }

  /**
   * Retrieves the HTML content generated by this view.
   *
   * @return The generated HTML content.
   */
  public String getTestOutput() {
    return stringWriter.toString();
  }
}
