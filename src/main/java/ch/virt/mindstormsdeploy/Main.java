package ch.virt.mindstormsdeploy;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.FileSystemAlreadyExistsException;
import java.sql.Struct;
import java.util.Scanner;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Main {
    public static final String VERSION = "1.2.0";

    public static void main(String[] args) {
        new Main();
    }

    private Config config;
    private Collector collector;

    public Main(){
        System.out.println("Running MindstormsDeploy on Version " + VERSION);
        config = Config.load();
        collector = new Collector(config);

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Press enter for build, type exit to quit");

            String answer = scanner.nextLine();
            if (answer.equals("exit")) quit();

            System.out.println("Collecting...");
            String code = collector.collect();

            System.out.println("Adding Code to Clipboard");
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(code), null);

            if (!config.isInfinityMode()) quit();
        }
    }

    public void quit(){
        System.exit(0);
    }


}
