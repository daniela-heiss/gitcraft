package top.gitcraft.utils.configUtils;

public class ConfigContent {

    // This class is used to combine DatabaseConfig and GlobalConfig
    // This is mendatory because the create yaml function can only use one class
    private DatabaseConfig databaseConfig;
    private GlobalConfig globalConfig;

    public ConfigContent(DatabaseConfig databaseConfig, GlobalConfig globalConfig) {
        this.databaseConfig = databaseConfig;
        this.globalConfig = globalConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }
}
