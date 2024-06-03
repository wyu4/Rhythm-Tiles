package Content.RTComponents;

import javax.swing.JFileChooser;
import java.io.File;

public class RTFileChooser extends JFileChooser {
    public RTFileChooser(String title, String message) {
        setName(title);
        setDialogTitle(message);
        setCurrentDirectory(new File("Resources")); // Set the current directory to the project directory
        setToolTipText("Select a file");
        setApproveButtonText("Choose");
        setApproveButtonToolTipText(message);
        setAcceptAllFileFilterUsed(false);
    }

    /**
     * Get the relative path of the selected file
     */
    public String getSelectedRelativeFile() {
        File selectedFile = super.getSelectedFile();
        String absolutePath = selectedFile.getAbsolutePath();
        String base = System.getProperty("user.dir");
        return new File(base).toURI().relativize(new File(absolutePath).toURI()).getPath();
    }
}
