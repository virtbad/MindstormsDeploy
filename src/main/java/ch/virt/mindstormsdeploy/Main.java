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
    public static final String VERSION = "2.0.1-SNAPSHOT";

    public static void main(String[] args) {
        new Main();
    }

    private Config config;
    private Collector collector;
    private Builder builder;
    private Deployer deployer;

    public Main(){
        System.out.println("Running MindstormsDeploy on Version " + VERSION);
        config = Config.load();
        collector = new Collector(config);
        builder = new Builder(config);
        deployer = new Deployer(config);

        if(!builder.start()) quit();

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Press enter for build, type exit for quit");

            String answer = scanner.nextLine();
            if (answer.equals("exit")) quit();

            System.out.println("Starting deploy...");
            System.out.println("Collecting");
            String code = collector.collect();

            System.out.println("Building");
            if(!builder.build(code)){
                System.err.println("Failed to build");
                continue;
            }
            System.out.println("Deploying");
            if(!deployer.deploy()){
                System.err.println("Failed to deploy");
                continue;
            }
            System.out.println("Job Finished!");

            if (!config.isInfinityMode()) quit();
        }
    }

    public void quit(){
        builder.quit();
        System.exit(0);
    }

}
