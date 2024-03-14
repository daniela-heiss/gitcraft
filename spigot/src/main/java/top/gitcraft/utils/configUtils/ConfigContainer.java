package top.gitcraft.utils.configUtils;

public class ConfigContainer {

    // This class is used to combine DatabaseConfig and GlobalConfig
    // This is needed because the create yaml function can only use one class

    /*
    Example:
    // load config.yaml
    ConfigContainer configContainer = getEntireConfig();

    // user getter to load the respective class you want to use
    GlobalConfig globalConfig = configContainer.getGlobalConfig();

    // Use in Class getter so get desired values
    System.out.println(globalConfig.getDefaultWorld());
    System.out.println(globalConfig.getParticleType());
    Particle particle = Particle.valueOf(globalConfig.getParticleType());
    System.out.println(globalConfig.getDeleteSourceWorld());

    // user getter to load the respective class you want to use
    DatabaseConfig databaseConfig = configContainer.getDatabaseConfig();
    
    // Use in Class getter so get desired values
    System.out.println(databaseConfig.getDATABASE_URL());
    System.out.println(databaseConfig.getDATABASE_USERNAME());
    System.out.println(databaseConfig.getDATABASE_PASSWORD());
    */

    private GlobalConfig globalConfig = new GlobalConfig();
    private DatabaseConfig databaseConfig = new DatabaseConfig();

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }
}

