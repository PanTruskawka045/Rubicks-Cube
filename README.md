# 🎲 Rubik's Cube Minecraft Plugin

This plugin was developed, because I was bored and wanted to learn how to use block display's 4x4 rotation

## 🎮 How to Use

1.  **Spawning a Cube**:
    Use the command `/spawncube <size> <tiles> [<random_moves>]` to spawn a new Rubik's Cube at your location.
    *   `<size>`: The total size of the cube in blocks.
    *   `<tiles>`: The number of smaller cubes (tiles) per row/column (e.g., 3 for a standard 3x3x3 cube).
    *   `[<random_moves>]` (Optional): The number of random moves to apply to scramble the cube.

2.  **Interacting with the Cube**:
    To rotate a face, simply right-click on one of the tiles. The face will rotate based on where you click on the tile.

## 🎥 Video Showcase

[![Rubik's Cube Plugin Showcase](https://i.imgur.com/1Tju6bU.png)](https://youtu.be/rNcARcLnlac "Rubik's Cube Plugin Showcase - Click to Watch!")

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## 🛠️ Building

To build the plugin, run:

```bash
gradle reobfJar
```