package blue.tmad40.stShards;

import blue.tmad40.stShards.files.Config;
import blue.tmad40.stShards.listeners.*;
import blue.tmad40.stShards.metrics.MetricsLite;
import blue.tmad40.stShards.commands.MainCommand;
import blue.tmad40.stShards.listeners.*;
import org.bukkit.entity.Arrow;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;


public class Main extends JavaPlugin {

    private static Main instance;
    public Map<Arrow, String> shotArrows = new WeakHashMap<>();

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        Config.getInstance().loadFiles();

        this.getCommand("sts").setExecutor(new MainCommand());

        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new Break(), this);
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new ShootBow(), this);

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {

        }
    }


}