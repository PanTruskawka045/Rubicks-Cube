package me.pan_truskawka045.RubicksCube.cube;

import java.util.HashMap;
import java.util.Map;

public class CubeRegistry {

    private final Map<Integer, Cube> cubes = new HashMap<>();

    public Cube getByEntityId(int entityId) {
        return cubes.get(entityId);
    }

    public void registerCube(int entityId, Cube cube) {
        cubes.put(entityId, cube);
    }

}
