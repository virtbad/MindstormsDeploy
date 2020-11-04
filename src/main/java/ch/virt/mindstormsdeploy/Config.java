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

    private boolean infinityMode = false;

    private CodeConfig code = new CodeConfig();

    public CodeConfig getCode() {
        return code;
    }

    public boolean isInfinityMode() {
        return infinityMode;
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