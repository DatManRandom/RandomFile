import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FileFinder {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner loc = new Scanner(new File("Location.txt"));
        String folderLocation = loc.nextLine();
        File folder = new File(folderLocation);
        Scanner sc = new Scanner(System.in);

        List<File> fileList = new ArrayList<>();

        System.out.println("Please type the address of the targeted folder, or leave this field empty to use the last location");
        String locationInput = sc.nextLine();
        System.out.println("Please type the number of wanted random files");
        String sizeInput = sc.nextLine();
        int size = 0;
        if (sizeInput.length() < 1) {
            size = 3;
        } else {
            size = Integer.parseInt(sizeInput);
        }
        System.out.println("Please type the type of extension you want the random files to be, or leave this field empty to return all files");
        String ext = sc.nextLine();

        if (locationInput.length() > 1) {
            folder = new File(locationInput);
            if (!folder.exists()) {
                System.out.println("File doesn't exist");
                throw new NullPointerException("File doesn't exist");
            }
            PrintWriter folderWriter = new PrintWriter("Location.txt");
            folderWriter.write(locationInput);
            folderWriter.close();
        }


        try {
            File[] allFiles = folder.listFiles();
            int num = allFiles.length;
            if (size > num) {
                System.out.println("The requested number of files is more than the number of files in the specified folder");
                throw new NullPointerException("The requested number of files is more than the number of files in the specified folder");
            }

            for (int i = 0; i < size; i++) {
                File randomItem = allFiles[(int) ((Math.random()) * num - 1)];

                if (!randomItem.getName().contains("." + ext)) {
                    i--;
                    continue;
                }

                if (fileList.contains(randomItem)) {
                    i--;
                    continue;
                }

                //TimeUnit.SECONDS.sleep(1);
                //Runtime.getRuntime().exec("cmd /c \"" + randomItem.getPath() + "\"");
                System.out.println(randomItem.getName());
                fileList.add(randomItem);
            }

            File outputFolder = new File("C:/Users/Osama/Desktop/RandomFiles/");
            outputFolder.mkdir();
            for (File fileItem : fileList) {
                Runtime.getRuntime().exec("cmd /c copy \"" + fileItem.getPath() + "\" \"" + outputFolder.getPath() + "\\\"" + fileItem.getName() + "\"");
            }

        } catch (NullPointerException e) {
            System.out.println("The folder you provided is empty or has been deleted");
            throw new NullPointerException("The folder you provided is empty or has been deleted");
        }
    }
}
