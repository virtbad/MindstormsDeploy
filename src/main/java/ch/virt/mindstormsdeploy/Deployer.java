package ch.virt.mindstormsdeploy;

import org.checkerframework.checker.units.qual.C;

import javax.security.auth.login.FailedLoginException;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Deployer {
    Config config;

    public Deployer(Config config) {
        this.config = config;
    }

    public boolean deploy(){
        File drive = selectDrive();
        if (drive == null) return false;

        File[] files = new File(config.getBuildFolder()).listFiles();

        if (files == null || files.length == 0) return false;

        System.out.println("    Copying File " + files[0] + " to Drive " + drive);
        try {
            Files.copy(files[0].toPath(), new File(drive, "bulit.uf2").toPath());
        } catch (IOException e) {
            System.err.println("    Failed to copy File !");
            return false;
        }

        System.out.println("    Deleting Files");
        for (File file : files) {
            if (!file.delete()) {
                System.err.println("    Failed to Delete File: " + file);
                return false;
            }
        }

        return true;
    }

    public File selectDrive(){
        System.out.println("    Selecting Deploy Drive");
        File[] paths = File.listRoots();
        FileSystemView fsv = FileSystemView.getFileSystemView();

        ArrayList<File> candidates = new ArrayList<>();

        for(File path:paths) {
            if (fsv.getSystemTypeDescription(path).contains("USB")) candidates.add(path);
        }

        if (candidates.size() > 1){
            StringBuilder sb = new StringBuilder();

            for (File candidate : candidates) {
                sb.append(candidate);
                sb.append(" , ");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Select drive to use: " + sb.substring(0, sb.length() - 2));

            String drive = scanner.next();

            return new File(drive + ":/");
        }

        if (candidates.size() == 1) return candidates.get(0);

        return null;
    }
}
