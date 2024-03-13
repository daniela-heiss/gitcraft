package top.gitcraft.utils.configUtils;

public class DatabaseConfig {
    private String DATABASE_URL = "<Your Database URL>";
    private String DATABASE_USERNAME = "<Your Database Username>";
    private String DATABASE_PASSWORD = "<Your Database Password>";

    // getter
    public String getDATABASE_URL() {
        return DATABASE_URL;
    }

    public void setDATABASE_URL(String DATABASE_URL) {
        this.DATABASE_URL = DATABASE_URL;
    }

    public String getDATABASE_USERNAME() {
        return DATABASE_USERNAME;
    }

    // setter
    public void setDATABASE_USERNAME(String DATABASE_USERNAME) {
        this.DATABASE_USERNAME = DATABASE_USERNAME;
    }

    public String getDATABASE_PASSWORD() {
        return DATABASE_PASSWORD;
    }

    public void setDATABASE_PASSWORD(String DATABASE_PASSWORD) {
        this.DATABASE_PASSWORD = DATABASE_PASSWORD;
    }
}
