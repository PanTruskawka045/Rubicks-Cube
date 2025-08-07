package me.pan_truskawka045.RubicksCube;

import lombok.Getter;
import me.pan_truskawka045.RubicksCube.command.SpawnCubeCommand;
import me.pan_truskawka045.RubicksCube.cube.CubeRegistry;
import me.pan_truskawka045.RubicksCube.listener.InteractListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RubicksCubePlugin extends JavaPlugin {

    @Getter
    private static RubicksCubePlugin instance;

    private final CubeRegistry cubeRegistry = new CubeRegistry();

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getCommandMap().register("rubickscube", new SpawnCubeCommand());
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
    }

}
