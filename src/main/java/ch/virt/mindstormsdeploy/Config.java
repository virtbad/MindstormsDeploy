package ch.virt.mindstormsdeploy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Config {
    public static final String NAME = "deployconfig.json";


    private String buildWebsite = "makecode.mindstorms.com";
    private String projectName = "Enter Project Name";
    private String buildFolder = "built";
    private boolean infinityMode = false;

    private CodeConfig code = new CodeConfig();

    private BrowserConfig browser = new BrowserConfig();

    public CodeConfig getCode() {
        return code;
    }

    public String getBuildFolder() {
        return buildFolder;
    }

    public boolean isInfinityMode() {
        return infinityMode;
    }

    public BrowserConfig getBrowser() {
        return browser;
    }

    public String getBuildWebsite() {
        return buildWebsite;
    }

    public String getProjectName() {
        return projectName;
    }

    public static Config load(){
        System.out.println("Loading Configuration");
        try {
            return new Gson().fromJson(Files.readString(new File(NAME).toPath()), Config.class);
        } catch (IOException e) {
            System.err.println("Failed to load deployconfig.json");
            try {
                Files.writeString(new File(NAME).toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(new Config()));
            } catch (IOException ioException) {
                System.err.println("Failed to create default config, you'll have to create it yourself!");
            }
            System.exit(0);
        }
        return null;
    }

}