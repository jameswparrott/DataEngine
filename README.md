# DataEngine

DataEngine is a game engine written in Java using LWJGL.

The project is primarily an exploration of game engine architecture, real-time rendering and data-oriented design, with the goal of building a complete engine from the ground up.

## Planned Features

* 3D scene and entity management
* Asset loading via Assimp
* Forward rendering
* Deferred rendering
* OpenGL-based graphics pipeline
* Audio via OpenAL
* Custom physics engine
* Scene editing
* Custom math library
* Support for modern Java language features, including Records

## Dependencies

DataEngine uses [LWJGL](https://www.lwjgl.org) for native bindings to:

* Assimp
* GLFW
* OpenAL
* OpenGL
* stb

## Requirements

- Java 17+
- LWJGL 3.4.1

DataEngine intentionally has minimal external dependencies. LWJGL provides
the native bindings required for graphics, audio, asset loading and window
management.

### macOS

When running the engine on macOS, the JVM must be started with:

```text
-XstartOnFirstThread
```
