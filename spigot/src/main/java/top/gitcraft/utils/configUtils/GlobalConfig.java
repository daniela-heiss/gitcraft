package top.gitcraft.utils.configUtils;

public class GlobalConfig {
    private boolean keepGamemodeOnWorldJoin = false;
    private boolean deleteSourceWorld =  true;
    private boolean cleanSchematicsInterval = false;
    private boolean allowPlayerToDeleteOtherWorld = false;
    private boolean cleanWorlds = false;
    private boolean skipVoidWorld = false;
    private boolean selectAreaByItem = true;
    private String particleType = "flames";
    private String defaultWorld = "world";

    // getter
    public boolean getKeepGamemodeOnWorldJoin() {
        return this.keepGamemodeOnWorldJoin;
    }
    public boolean getDeleteSourceWorld() {
        return this.deleteSourceWorld;
    }
    public boolean getCleanSchematicsInterval() {
        return this.cleanSchematicsInterval;
    }
    public boolean getAllowPlayerToDeleteOtherWorld() {
        return this.allowPlayerToDeleteOtherWorld;
    }
    public boolean getCleanWorlds() {
        return this.cleanWorlds;
    }
    public boolean getSkipVoidWorld() {
        return this.skipVoidWorld;
    }
    public boolean getSelectAreaByItem() {
        return this.selectAreaByItem;
    }
    public String getParticleType() {
        return this.particleType;
    }
    public String getDefaultWorld() {
        return this.defaultWorld;
    }

    // setter
    public void setKeepGamemodeOnWorldJoin(boolean keepGamemodeOnWorldJoin) {
        this.keepGamemodeOnWorldJoin = keepGamemodeOnWorldJoin;
    }
    public void setCleanWorlds(boolean cleanWorlds) {
        this.cleanWorlds = cleanWorlds;
    }

    public void setDeleteSourceWorld(boolean deleteSourceWorld) {
        this.deleteSourceWorld = deleteSourceWorld;
    }

    public void setCleanSchematicsInterval(boolean cleanSchematicsInterval) {
        this.cleanSchematicsInterval = cleanSchematicsInterval;
    }

    public void setAllowPlayerToDeleteOtherWorld(boolean allowPlayerToDeleteOtherWorld) {
        this.allowPlayerToDeleteOtherWorld = allowPlayerToDeleteOtherWorld;
    }

    public void setSkipVoidWorld(boolean skipVoidWorld) {
        this.skipVoidWorld = skipVoidWorld;
    }

    public void setSelectAreaByItem(boolean selectAreaByItem) {
        this.selectAreaByItem = selectAreaByItem;
    }

    public void setParticleType(String particleType) {
        this.particleType = particleType;
    }

    public void setDefaultWorld(String defaultWorld) {
        this.defaultWorld = defaultWorld;
    }
}
