import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIFileFinder {

    public static void findRandomFiles(String searchLocation, String fileType) throws IOException {
        findRandomFiles(searchLocation, 3, fileType);
    }

    public static void findRandomFiles(String searchLocation, int numOfFiles, String fileType) throws IOException {
        File folder = new File(searchLocation);
        List<File> fileList = new ArrayList<>();

        /*/ This was used to save the directory for later ease of access
        PrintWriter folderWriter = new PrintWriter("lastLocation.txt");
        folderWriter.write(searchLocation);
        folderWriter.close();*/

        File[] allFiles = folder.listFiles();
        assert allFiles != null && allFiles.length > numOfFiles;

        for (int i = 0; i < numOfFiles; i++) {
            File randomItem = allFiles[(int) ((Math.random()) * allFiles.length - 1)];
            String extension = getFileExtension(randomItem);
            if (extension.equals(".jpg")) extension = ".jpeg";

            if (fileList.contains(randomItem)) {
                i--;
                continue;
            }
            if (!fileType.equals("Any") && !extension.equals(fileType)) {
                i--;
                continue;
            }

            fileList.add(randomItem);
        }
        //Set output folder "RandomFiles/"
        File outputFolder;
        if (!(folder.getPath().toLowerCase().contains("homework\\games") && folder.getPath().toLowerCase().contains("osama")))
            outputFolder = new File(folder.getPath() + "/RandomFiles/");
        else outputFolder = new File(folder.getParent() + "/RandomFiles/");

        outputFolder.mkdir();
        for (File fileItem : fileList) {
            Runtime.getRuntime().exec("cmd /c copy \"" + fileItem.getPath() + "\" \"" + outputFolder.getPath()
                + "\\\"" + fileItem.getName() + "\"");
        }

    }


    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    public static void main(String[] args) throws IOException {
        findRandomFiles("C:\\Users\\Osama\\Downloads\\Homework\\Games", 5, "Any");
    }
}
