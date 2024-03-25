package top.gitcraft.utils.configUtils;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.File;
import java.io.*;


public class ConfigUtils {

    private static String currentDirectory = System.getProperty("user.dir");
    private static String folderName = "/plugins/GitCraft/";
    private static String fileName = "config";
    private static String fileEnding = ".yaml";


    private static boolean createGitCraftFolder (String currentDirectory, String folderName) {
        File directory = new File(currentDirectory + folderName);
        boolean success;
        if (!directory.exists()) {
            success = directory.mkdir();
        } else {
            System.out.println("Directory " + folderName + " already exists");
            return true;
        }
        if (success) {
            System.out.println("Directory " + folderName + " created successfully");
            return true;
        } else {
            System.out.println("Error creating " + folderName + " directory");
            return false;
        }
    }

    public static void createNewConfigFile () throws FileNotFoundException {

        boolean folderCreated = createGitCraftFolder(currentDirectory, folderName);
        if (folderCreated) {
            File configFile= new File(currentDirectory + "/plugins/GitCraft/" + fileName + fileEnding);
            if (!configFile.exists()) {
                // Make it look like a "classic" yaml file
                DumperOptions options = new DumperOptions();
                options.setIndent(2);
                options.setPrettyFlow(true);
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                Representer skipEmptyAndNullRepresenter = new Representer(options);
                skipEmptyAndNullRepresenter.addClassTag(ConfigContainer.class, Tag.MAP);

                ConfigContainer ConfigContainer = new ConfigContainer();

                PrintWriter writer = new PrintWriter(configFile);
                Yaml config = new Yaml(skipEmptyAndNullRepresenter, options);
                config.dump(ConfigContainer, writer);
                System.out.println("config.yml created successfully");
            } else {
                System.out.println("config.yml already exists");
            }
        } else {
            System.out.println("Unable to create config.yml, Directory couldn't be created");
        }
    }

    public static ConfigContainer getEntireConfig() {

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(currentDirectory + "/plugins/GitCraft/" + fileName + fileEnding);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Yaml config = new Yaml(new Constructor(ConfigContainer.class, new LoaderOptions()));

        return config.load(inputStream);
    }

}

