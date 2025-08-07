package me.pan_truskawka045.RubicksCube.cube;

import lombok.Getter;
import me.pan_truskawka045.RubicksCube.RubicksCubePlugin;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Interaction;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Cube {

    private final AxisAngle4f ZERO = new AxisAngle4f(0, 0, 0, 1);

    private final float spaceBetween = 0.1f;
    private final float offset = 0.1f;
    private final float size;
    private final int tiles;
    private final CubePart[/*X*/][/*Y*/][/*Z*/] parts;
    private Interaction interaction;


    public Cube(float size, int tiles) {
        this.size = size;
        this.tiles = tiles;
        this.parts = new CubePart[tiles][tiles][tiles];
    }

    public void spawn(Location location) {
        location.setPitch(0);
        location.setYaw(0);

        float halfSize = size / 2;
        float tileSize = size / tiles;

        for (int x = 0; x < tiles; x++) {
            for (int z = 0; z < tiles; z++) {
                float xTransform = -halfSize + tileSize * x;
                float zTransform = -halfSize + tileSize * z;
                float y1Transform = -halfSize;
                float y2Transform = -halfSize + tileSize * (tiles - 1);

                CubePart cubePartBottom = parts[x][0][z] == null ? (parts[x][0][z] = new CubePart(createDarkBlockDisplay(location, xTransform, y1Transform, zTransform), this)) : parts[x][0][z];

                location.getWorld().spawn(location, BlockDisplay.class, bottomFace -> {
                    CubeColor cubeColor = CubeColor.values()[0];
                    bottomFace.setBlock(cubeColor.getMaterial().createBlockData());
                    bottomFace.setTransformation(createColorBlockTransformation(xTransform, y1Transform - offset * 2, zTransform));
                    cubePartBottom.getFaces().add(new CubePartFace(bottomFace, BlockFace.DOWN));
                    cubePartBottom.getColors().add(cubeColor);
                });

                CubePart cubePartTop = parts[x][tiles - 1][z] == null ? (parts[x][tiles - 1][z] = new CubePart(createDarkBlockDisplay(location, xTransform, y2Transform, zTransform), this)) : parts[x][tiles - 1][z];

                location.getWorld().spawn(location, BlockDisplay.class, topFace -> {
                    CubeColor cubeColor = CubeColor.values()[1];
                    topFace.setBlock(cubeColor.getMaterial().createBlockData());
                    topFace.setTransformation(createColorBlockTransformation(xTransform, y2Transform + offset * 2, zTransform));
                    cubePartTop.getFaces().add(new CubePartFace(topFace, BlockFace.UP));
                    cubePartTop.getColors().add(cubeColor);
                });
            }
        }

        for (int y = 0; y < tiles; y++) {
            for (int z = 0; z < tiles; z++) {
                float yTransform = -halfSize + tileSize * y;
                float zTransform = -halfSize + tileSize * z;
                float x1Transform = -halfSize;
                float x2Transform = -halfSize + tileSize * (tiles - 1);

                CubePart cubePartLeft = parts[0][y][z] == null ? (parts[0][y][z] = new CubePart(createDarkBlockDisplay(location, x1Transform, yTransform, zTransform), this)) : parts[0][y][z];

                location.getWorld().spawn(location, BlockDisplay.class, leftFace -> {
                    CubeColor cubeColor = CubeColor.values()[2];
                    leftFace.setBlock(cubeColor.getMaterial().createBlockData());
                    leftFace.setTransformation(createColorBlockTransformation(x1Transform - offset * 2, yTransform, zTransform));
                    cubePartLeft.getFaces().add(new CubePartFace(leftFace, BlockFace.WEST));
                    cubePartLeft.getColors().add(cubeColor);
                });

                CubePart cubePartRight = parts[tiles - 1][y][z] == null ? (parts[tiles - 1][y][z] = new CubePart(createDarkBlockDisplay(location, x2Transform, yTransform, zTransform), this)) : parts[tiles - 1][y][z];

                location.getWorld().spawn(location, BlockDisplay.class, rightFace -> {
                    CubeColor cubeColor = CubeColor.values()[3];
                    rightFace.setBlock(cubeColor.getMaterial().createBlockData());
                    rightFace.setTransformation(createColorBlockTransformation(x2Transform + offset * 2, yTransform, zTransform));
                    cubePartRight.getFaces().add(new CubePartFace(rightFace, BlockFace.EAST));
                    cubePartRight.getColors().add(cubeColor);
                });
            }
        }

        for (int x = 0; x < tiles; x++) {
            for (int y = 0; y < tiles; y++) {
                float xTransform = -halfSize + tileSize * x;
                float yTransform = -halfSize + tileSize * y;
                float z1Transform = -halfSize;
                float z2Transform = -halfSize + tileSize * (tiles - 1);

                CubePart cubePartFront = parts[x][y][0] == null ? (parts[x][y][0] = new CubePart(createDarkBlockDisplay(location, xTransform, yTransform, z1Transform), this)) : parts[x][y][0];

                location.getWorld().spawn(location, BlockDisplay.class, frontFace -> {
                    CubeColor cubeColor = CubeColor.values()[4];
                    frontFace.setBlock(cubeColor.getMaterial().createBlockData());
                    frontFace.setTransformation(createColorBlockTransformation(xTransform, yTransform, z1Transform - offset * 2));
                    cubePartFront.getFaces().add(new CubePartFace(frontFace, BlockFace.NORTH));
                    cubePartFront.getColors().add(cubeColor);
                });

                CubePart cubePartBack = parts[x][y][tiles - 1] == null ? (parts[x][y][tiles - 1] = new CubePart(createDarkBlockDisplay(location, xTransform, yTransform, z2Transform), this)) : parts[x][y][tiles - 1];

                location.getWorld().spawn(location, BlockDisplay.class, backFace -> {
                    CubeColor cubeColor = CubeColor.values()[5];
                    backFace.setBlock(cubeColor.getMaterial().createBlockData());
                    backFace.setTransformation(createColorBlockTransformation(xTransform, yTransform, z2Transform + offset * 2));
                    cubePartBack.getFaces().add(new CubePartFace(backFace, BlockFace.SOUTH));
                    cubePartBack.getColors().add(cubeColor);
                });
            }
        }

        this.interaction = location.getWorld().spawn(location.clone().add(0, -size / 2f, 0), Interaction.class, entity -> {
            entity.setInteractionHeight(size);
            entity.setInteractionWidth(size);
            entity.setResponsive(true);
        });

        RubicksCubePlugin.getInstance().getCubeRegistry().registerCube(interaction.getEntityId(), this);

        setGlow(true);
    }

    public boolean isSolved() {
        int requiredColors = tiles * tiles;

        for (CubeAxis axis : CubeAxis.values()) {
            layerLoop:
            for (int layer : new int[]{0, this.tiles - 1}) {
                Map<CubeColor, Integer> countedColors = countColors(axis, layer);
                for (Map.Entry<CubeColor, Integer> entry : countedColors.entrySet()) {
                    if (entry.getValue() == requiredColors) {
                        continue layerLoop;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private Map<CubeColor, Integer> countColors(CubeAxis axis, int layer) {
        Map<CubeColor, Integer> colors = new HashMap<>();
        for (int i = 0; i < tiles; i++) {
            for (int j = 0; j < tiles; j++) {
                CubePart cubePart;
                switch (axis) {
                    case X -> cubePart = this.parts[layer][i][j];
                    case Y -> cubePart = this.parts[j][layer][i];
                    case Z -> cubePart = this.parts[i][j][layer];
                    default -> throw new IllegalArgumentException("Invalid axis: " + axis);
                }
                if (cubePart != null) {
                    for (CubeColor color : cubePart.getColors()) {
                        colors.put(color, colors.getOrDefault(color, 0) + 1);
                    }
                }
            }
        }
        return colors;
    }

    private BlockDisplay createDarkBlockDisplay(Location location, float transformX, float transformY, float transformZ) {
        BlockDisplay display = location.getWorld().spawn(location, BlockDisplay.class);
        display.setBlock(Material.BLACK_CONCRETE.createBlockData());
        display.setTransformation(createDarkBlockTransformation(transformX, transformY, transformZ));
        return display;
    }

    private Transformation createDarkBlockTransformation(float x, float y, float z) {

        float scale = size / tiles;

        return new Transformation(
                new Vector3f(x, y, z),
                ZERO,
                new Vector3f(scale, scale, scale),
                ZERO
        );
    }

    private Transformation createColorBlockTransformation(float x, float y, float z) {
        float scale = size / tiles;
        float halfOffset = offset / 2;

        return new Transformation(
                new Vector3f(x + halfOffset, y + halfOffset, z + halfOffset),
                ZERO,
                new Vector3f(scale - offset, scale - offset, scale - offset),
                ZERO
        );
    }

    public void rotateForward(CubeAxis axis, int layer) {
        rotateCube(axis, layer, Math.PI / 2);
    }

    public void rotateBackward(CubeAxis axis, int layer) {
        rotateCube(axis, layer, -Math.PI / 2);
    }

    private void rotateCube(CubeAxis axis, int layer, double angle) {

        CubePart[][] temp = new CubePart[tiles][tiles];

        for (int i = 0; i < tiles; i++) {
            for (int j = 0; j < tiles; j++) {
                switch (axis) {
                    case X -> temp[i][j] = this.parts[layer][i][j];
                    case Y -> temp[i][j] = this.parts[j][layer][i];
                    case Z -> temp[i][j] = this.parts[i][j][layer];
                }
            }
        }

        if (angle > 0) {
            for (int i = 0; i < temp.length / 2; i++) {
                int bottom = temp.length - 1 - i;
                for (int j = i; j < bottom; j++) {
                    CubePart tempCube = temp[i][j];
                    temp[i][j] = temp[j][bottom];
                    temp[j][bottom] = temp[bottom][bottom - (j - i)];
                    temp[bottom][bottom - (j - i)] = temp[bottom - (j - i)][i];
                    temp[bottom - (j - i)][i] = tempCube;
                }
            }
        } else {
            for (int i = 0; i < temp.length / 2; i++) {
                int bottom = temp.length - 1 - i;
                for (int j = i; j < bottom; j++) {
                    CubePart tempCube = temp[i][j];
                    temp[i][j] = temp[bottom - (j - i)][i];
                    temp[bottom - (j - i)][i] = temp[bottom][bottom - (j - i)];
                    temp[bottom][bottom - (j - i)] = temp[j][bottom];
                    temp[j][bottom] = tempCube;
                }
            }

        }


        for (int i = 0; i < tiles; i++) {
            for (int j = 0; j < tiles; j++) {
                final CubePart cubePart = temp[i][j];
                if (cubePart != null) {
                    cubePart.rotate(axis, (float) angle);
                }
                switch (axis) {
                    case X -> this.parts[layer][i][j] = cubePart;
                    case Y -> this.parts[j][layer][i] = cubePart;
                    case Z -> this.parts[i][j][layer] = cubePart;
                }
            }
        }

        setGlow(isSolved());
    }

    private void setGlow(boolean glow) {
        for (int x = 0; x < tiles; x++) {
            for (int y = 0; y < tiles; y++) {
                for (int z = 0; z < tiles; z++) {
                    CubePart cubePart = this.parts[x][y][z];
                    if (cubePart != null) {
                        if (glow) {
                            cubePart.getBlackCube().setGlowing(true);
                            cubePart.getBlackCube().setGlowColorOverride(Color.GREEN);
                        } else {
                            cubePart.getBlackCube().setGlowing(false);
                        }
                    }
                }
            }
        }
    }

}
