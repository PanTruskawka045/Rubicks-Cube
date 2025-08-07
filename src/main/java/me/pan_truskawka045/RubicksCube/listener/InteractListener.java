package me.pan_truskawka045.RubicksCube.listener;

import me.pan_truskawka045.RubicksCube.RubicksCubePlugin;
import me.pan_truskawka045.RubicksCube.cube.Cube;
import me.pan_truskawka045.RubicksCube.cube.CubeAxis;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Interaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.util.Vector;

public class InteractListener implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Interaction interaction)) {
            return;
        }

        Cube cube = RubicksCubePlugin.getInstance().getCubeRegistry().getByEntityId(interaction.getEntityId());

        if (cube == null) {
            return;
        }


        Vector clickedPosition = event.getClickedPosition();

        float size = cube.getSize();
        float halfSize = size / 2;

        double clickX = clickedPosition.getX() + halfSize;
        double clickY = clickedPosition.getY();
        double clickZ = clickedPosition.getZ() + halfSize;

        int tileX = (int) (Math.min(clickX / cube.getSize() * cube.getTiles(), cube.getTiles() - 1));
        int tileY = (int) (Math.min(clickY / cube.getSize() * cube.getTiles(), cube.getTiles() - 1));
        int tileZ = (int) (Math.min(clickZ / cube.getSize() * cube.getTiles(), cube.getTiles() - 1));

        BlockFace face;

        Vector clickedPositionLocation = interaction.getLocation().toVector().add(clickedPosition);
        Vector eyeLoc = event.getPlayer().getEyeLocation().toVector();

        Vector diff = eyeLoc.clone().subtract(clickedPositionLocation);

        if (clickX == 0) {
            face = BlockFace.WEST;
        } else if (clickX == size) {
            face = BlockFace.EAST;
        } else if (clickY == 0) {
            face = BlockFace.DOWN;
        } else if (clickY == size) {
            face = BlockFace.UP;
        } else if (clickZ == 0) {
            face = BlockFace.NORTH;
        } else {
            face = BlockFace.SOUTH;
        }

        float angle = switch (face) {
            case WEST, EAST -> (float) Math.atan2(diff.getZ(), diff.getY());
            case DOWN, UP -> (float) Math.atan2(diff.getX(), diff.getZ());
            case NORTH, SOUTH -> (float) Math.atan2(diff.getX(), diff.getY());
            default -> 0;
        };

        int mode = getMode(angle);

        if (face == BlockFace.DOWN) {
            switch (mode) {
                case 0 -> cube.rotateBackward(CubeAxis.X, tileX);
                case 1 -> cube.rotateBackward(CubeAxis.Z, tileZ);
                case 2 -> cube.rotateForward(CubeAxis.X, tileX);
                case 3 -> cube.rotateForward(CubeAxis.Z, tileZ);
            }
        }
        if (face == BlockFace.UP) {
            switch (mode) {
                case 0 -> cube.rotateBackward(CubeAxis.X, tileX);
                case 1 -> cube.rotateForward(CubeAxis.Z, tileZ);
                case 2 -> cube.rotateForward(CubeAxis.X, tileX);
                case 3 -> cube.rotateBackward(CubeAxis.Z, tileZ);
            }
        }

        if (face == BlockFace.SOUTH) {
            switch (mode) {
                case 0 -> cube.rotateForward(CubeAxis.X, tileX);
                case 1 -> cube.rotateBackward(CubeAxis.Y, tileY);
                case 2 -> cube.rotateBackward(CubeAxis.X, tileX);
                case 3 -> cube.rotateForward(CubeAxis.Y, tileY);
            }
        }

        if (face == BlockFace.NORTH) {
            switch (mode) {
                case 0 -> cube.rotateBackward(CubeAxis.X, tileX);
                case 1 -> cube.rotateForward(CubeAxis.Y, tileY);
                case 2 -> cube.rotateForward(CubeAxis.X, tileX);
                case 3 -> cube.rotateBackward(CubeAxis.Y, tileY);
            }
        }

        if (face == BlockFace.EAST) {
            switch (mode) {
                case 0 -> cube.rotateBackward(CubeAxis.Z, tileZ);
                case 1 -> cube.rotateForward(CubeAxis.Y, tileY);
                case 2 -> cube.rotateForward(CubeAxis.Z, tileZ);
                case 3 -> cube.rotateBackward(CubeAxis.Y, tileY);
            }
        }

        if (face == BlockFace.WEST) {
            switch (mode) {
                case 0 -> cube.rotateForward(CubeAxis.Z, tileZ);
                case 1 -> cube.rotateBackward(CubeAxis.Y, tileY);
                case 2 -> cube.rotateBackward(CubeAxis.Z, tileZ);
                case 3 -> cube.rotateForward(CubeAxis.Y, tileY);
            }
        }

    }

    private int getMode(float angle) {
        if (angle < 0) {
            angle += (float) Math.TAU;
        }
        if (angle < Math.PI / 4) {
            return 0;
        }
        if (angle < 3 * Math.PI / 4) {
            return 1;
        }
        if (angle < 5 * Math.PI / 4) {
            return 2;
        }
        if (angle < 7 * Math.PI / 4) {
            return 3;
        }
        return 0;

    }
}