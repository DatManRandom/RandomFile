import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GUI {
    private JPanel mainPanel;
    private JPanel submitButtonPanel;
    private JButton pickLocationButton;
    private JButton submitButton;
    private JTextField locationText;
    private JTextField fileNumberText;
    private JLabel numLabel;
    private JComboBox<String> fileTypes;
    private JLabel TypeLabel;

    private static String lastPath = System.getProperty("user.home") + "/Desktop";

    private static final String[] fileFormats = {"Any", ".gif", ".jpeg", ".mp4", ".png", ".webm", ".swf"};

    public GUI() {
        pickLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Folder");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                fileChooser.setCurrentDirectory(new File(lastPath));

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    lastPath = fileChooser.getSelectedFile().toString();
                    System.out.println(lastPath);
                    locationText.setText(lastPath);
                }
            }
        });
        submitButton.addActionListener(e -> {
            try {
                if (fileNumberText.getText().length() < 1)
                    GUIFileFinder.findRandomFiles(lastPath, fileTypes.getSelectedItem().toString());
                else
                    GUIFileFinder.findRandomFiles(lastPath, Integer.parseInt(fileNumberText.getText()),
                        fileTypes.getSelectedItem().toString());
                JOptionPane.showMessageDialog(null,
                    "The files have been successfully randomized!");
            } catch (AssertionError | IOException error) {
                JOptionPane.showMessageDialog(null,
                    "The folder you provided is empty or has been deleted");
                error.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Random File Picker");
        GUI gui = new GUI();
        jFrame.setContentPane(gui.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        File lastLocation = new File("lastLocation.txt");
        try {
            Scanner fileSc = new Scanner(lastLocation);
            lastPath = fileSc.nextLine();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        gui.locationText.setText(lastPath);
        gui.fileTypes.setPrototypeDisplayValue(".webm");
        for (String item : fileFormats)
            gui.fileTypes.addItem(item);

    }

}
