package me.pan_truskawka045.RubicksCube.command;

import me.pan_truskawka045.RubicksCube.cube.Cube;
import me.pan_truskawka045.RubicksCube.cube.CubeAxis;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class SpawnCubeCommand extends Command {

    public SpawnCubeCommand() {
        super("spawncube", "Spawns rubicks cube", ChatColor.RED + "Usage: /spawncube <size> <tiles>", Collections.emptyList());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Please provide the size and number of tiles for the cube. Usage: /spawncube <size> <tiles> [<random_moves>]");
            return false;
        }

        float size;
        int tiles;

        try {
            size = Float.parseFloat(args[0]);
            tiles = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid number format. Please provide a valid size and number of tiles. Usage: /spawncube <size> <tiles> [<random_moves>]");
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player. Please run it in-game.");
            return false;
        }

        Cube cube = new Cube(size, tiles);
        cube.spawn(player.getLocation());

        if (args.length >= 3) {
            int randomMoves = 0;
            try {
                randomMoves = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number format for random moves. Using default value of 0.");
            }
            if (randomMoves <= 0) {
                sender.sendMessage(ChatColor.RED + "Random moves must be greater than 0. Using default value of 0.");
            }
            for (int i = 0; i < randomMoves; i++) {
                CubeAxis axis = CubeAxis.values()[(int) (Math.random() * CubeAxis.values().length)];
                boolean clockwise = Math.random() < 0.5;
                int layer = (int) (Math.random() * tiles);
                if (clockwise) {
                    cube.rotateForward(axis, layer);
                } else {
                    cube.rotateBackward(axis, layer);
                }
            }
        }

        return true;
    }
}
