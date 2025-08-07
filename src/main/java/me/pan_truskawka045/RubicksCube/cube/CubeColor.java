package me.pan_truskawka045.RubicksCube.cube;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@RequiredArgsConstructor
@Getter
public enum CubeColor {

    RED(Material.RED_CONCRETE),
    ORANGE(Material.ORANGE_CONCRETE),
    YELLOW(Material.YELLOW_CONCRETE),
    GREEN(Material.LIME_CONCRETE),
    BLUE(Material.LIGHT_BLUE_CONCRETE),
    PURPLE(Material.MAGENTA_CONCRETE);

    private final Material material;

}
