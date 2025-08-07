package me.pan_truskawka045.RubicksCube.cube;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class CubePart {

    private final List<CubePartFace> faces = new ArrayList<>();
    private final Set<CubeColor> colors = new HashSet<>();
    private final BlockDisplay blackCube;
    private final Cube parent;

    private boolean rotatedBefore = false;

    public void rotate(CubeAxis axis, float angle) {
        Quaternionf leftRotation = this.blackCube.getTransformation().getLeftRotation();
        Quaternionf rightRotation = this.blackCube.getTransformation().getRightRotation();
        Vector3f translation = this.blackCube.getTransformation().getTranslation();

        float halfOffset = this.parent.getSize() / this.parent.getTiles() / 2;

        translation.add(halfOffset, halfOffset, halfOffset);

        switch (axis) {
            case X -> translation.rotateX(angle);
            case Y -> translation.rotateY(angle);
            case Z -> translation.rotateZ(angle);
        }

        translation.sub(halfOffset, halfOffset, halfOffset);

        Transformation transform = new Transformation(
                translation,
                leftRotation,
                this.blackCube.getTransformation().getScale(),
                rightRotation
        );

        this.blackCube.setInterpolationDelay(0);
        this.blackCube.setInterpolationDuration(20);
        this.blackCube.setTransformation(transform);

        this.rotatedBefore = true;

        for (CubePartFace cubePartFace : this.faces) {

            BlockDisplay face = cubePartFace.blockDisplay();
            Transformation faceTransform = face.getTransformation();
            float halfScale = faceTransform.getScale().x / 2;


            Vector3f faceTranslation = faceTransform.getTranslation();
            faceTranslation.add(halfScale, halfScale, halfScale);

            switch (axis) {
                case X -> faceTranslation.rotateX(angle);
                case Y -> faceTranslation.rotateY(angle);
                case Z -> faceTranslation.rotateZ(angle);
            }


            faceTranslation.sub(halfScale, halfScale, halfScale);

            Transformation newFaceTransform = new Transformation(
                    faceTranslation,
                    leftRotation,
                    faceTransform.getScale(),
                    rightRotation
            );

            face.setInterpolationDelay(0);
            face.setInterpolationDuration(20);
            face.setTransformation(newFaceTransform);

        }

    }

}
