package net.main;

import net.main.model.User;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.File;
import org.hibernate.cfg.Configuration;
import java.util.Objects;
import java.sql.*;

public final class SpigotExample extends JavaPlugin {

    private static final String HIBERNATE_CONFIG_FILE_NAME = "hibernate.cfg.xml";

    //private SessionFactory sessionFactory;

    @Override
    public void onEnable() {

        //this.saveResource(HIBERNATE_CONFIG_FILE_NAME, false);
        //this.sessionFactory = new Configuration()
        //        .configure(new File(this.getDataFolder().getAbsolutePath() + "/" + HIBERNATE_CONFIG_FILE_NAME))
        //        .addAnnotatedClass(User.class)
        //        .buildSessionFactory();

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(User.class);

        try ( SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            User user = new User();
            System.out.println(user.getUser());
        }



        // Plugin startup logic
        getLogger().info("Hello World example loaded");
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new CommandKit());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye World example unloaded");
    }
}
