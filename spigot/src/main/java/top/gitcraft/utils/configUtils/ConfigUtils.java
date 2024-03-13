package top.gitcraft.utils.configUtils;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.*;


public class ConfigUtils {

    private static String currentDirectory = System.getProperty("user.dir");
    private static String folderName = "/plugins/GitCraft/";
    private static String fileName = "config";
    private static String fileEnding = ".yml";


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

                // Create new config.yaml
                ConfigContent configContent = new ConfigContent(new DatabaseConfig(), new GlobalConfig());

                PrintWriter writer = new PrintWriter(configFile);
                Yaml config = new Yaml(options);
                config.dump(configContent, writer);
                System.out.println("config.yml created successfully");
            } else {
                System.out.println("config.yml already exists");
            }
        } else {
            System.out.println("Unable to create config.yml, Directory couldn't be created");
        }
    }

    public static DatabaseConfig getDatabaseConfig() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(currentDirectory + "/plugins/GitCraft/" + fileName + fileEnding));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Yaml config = new Yaml(new Constructor(DatabaseConfig.class, new LoaderOptions()));
        return config.load(inputStream);
    }

    public static DatabaseConfig getGlobalConfig() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(currentDirectory + "/plugins/GitCraft/" + fileName + fileEnding));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Yaml config = new Yaml(new Constructor(GlobalConfig.class, new LoaderOptions()));
        return config.load(inputStream);
    }

}

