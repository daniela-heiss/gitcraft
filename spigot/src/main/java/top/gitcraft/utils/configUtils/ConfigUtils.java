package top.gitcraft.utils.configUtils;

import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.*;

import java.io.File;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static top.gitcraft.utils.configUtils.YamlMap.getStringObjectMap;

public class ConfigUtils {

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
        Map<String, Object> dataMap = getStringObjectMap();

        String currentDirectory = System.getProperty("user.dir");
        String folderName = "/plugins/GitCraft/";

        boolean folderCreated = createGitCraftFolder(currentDirectory, folderName);
        if (folderCreated) {
            String fileName = "config";
            String fileEnding = ".yml";
            File configFile= new File(currentDirectory + "/plugins/GitCraft/" + fileName + fileEnding);
            if (!configFile.exists()) {
                // Make it look like a "classic" yaml file
                DumperOptions options = new DumperOptions();
                options.setIndent(2);
                options.setPrettyFlow(true);
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

                // Create new config.yaml
                PrintWriter writer = new PrintWriter(configFile);
                Yaml config = new Yaml(options);
                config.dump(dataMap, writer);
                System.out.println("config.yml created successfully");
            } else {
                System.out.println("config.yml already exists");
            }
        } else {
            System.out.println("Unable to create config.yml, Directory couldn't be created");
        }
    }


}

