# Mod Menu — Minecraft 1.21.11 Combat Mod

## Features
- **Aimbot**: Smoothly rotates view to nearest player target
- **ESP**: Entity detection with range render
- **Attack Swap**: Automatic hotbar slot switching for weapons/blocks

## Installation

### Requirements
- Java 17 or higher
- Gradle 8.x
- Fabric Loader 0.15.11+

### Build Instructions

1. Clone or extract this project
2. Open terminal in the project directory
3. Run the build:

```bash
./gradlew build
```

or on Windows:

```cmd
gradlew.bat build
```

4. The compiled JAR will be in `build/libs/modmenu-1.0.0.jar`

### Installing to Minecraft

1. Download and install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.11
2. Place the built JAR in your `.minecraft/mods` folder
3. Launch Minecraft with the Fabric profile

## Usage

- Press **M** to toggle the mod menu
- Click buttons to enable/disable features
- Close with ESC or the Close button

## Features Detail

**Aimbot**: Automatically tracks and aims at the nearest player within 50 blocks. Smoothing value 0.3 for human-like movement.

**ESP**: Shows bounding boxes around nearby players. Renders green boxes for all entities within 150 block range.

**Attack Swap**: Automatically switches to weapon slot (hotbar 1) on attack, and block slot (hotbar 9) on placement.

## Build Troubleshooting

If you encounter gradle plugin errors:
- Ensure you have internet connectivity (fabric plugins download from Maven)
- Try `./gradlew clean build` to reset the build cache
- Check that Java 17+ is set as default: `java -version`

## License

MIT
