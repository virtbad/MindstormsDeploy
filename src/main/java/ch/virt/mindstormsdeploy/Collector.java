package ch.virt.mindstormsdeploy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Collector {
    private Config config;

    private List<String> files;

    public Collector(Config config) {
        this.config = config;
    }

    public String collect(){
        files = new ArrayList<>();
        System.out.println("Collecting code from directory " + config.getCode().getSourceFolder());

        File src = new File(config.getCode().getSourceFolder());
        if (!src.exists() || !src.isDirectory()) {
            System.err.println("Your source directory must be a directory");
            return null;
        }

        searchThrough(src);

        System.out.println("Sorting for Priorities");
        files.sort((o1, o2) -> {
            int firstPriority = getPriority(o1), secondPriority = getPriority(o2);

            return -1 * Integer.compare(firstPriority, secondPriority);
        });

        StringBuilder sb = new StringBuilder();

        for (String file : files) {
            sb.append(file);
            sb.append("\n\n");
        }

        System.out.println("Finished Collection");

        return sb.toString();
    }

    public int getPriority(String file){
        String[] lines = file.split("\n");
        String first = lines[0];

        first = first.toLowerCase();
        first = first.replace(" ", "");

        if (first.startsWith(config.getCode().getPriorityPrefix())){
            return Integer.parseInt(first.substring(config.getCode().getPriorityPrefix().length()).strip());
        }

        return 0;
    }

    public void searchThrough(File dir){
        System.out.println("    Looking through directory: " + dir);
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) searchThrough(file);
            else {
                for (String codeFileEnding : config.getCode().getCodeFileEndings()) {
                    if (file.getName().endsWith(codeFileEnding)){
                        readFile(file);
                        break;
                    }
                }
            }
        }
    }

    public void readFile(File file){
        System.out.println("        Reading file: " + file);
        try {
            files.add(Files.readString(file.toPath()));
        } catch (IOException e) {
            System.err.println("        Failed to read that File!");
        }
    }
}
