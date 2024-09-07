# CoreEngine Documentation
Overview
CoreEngine is a versatile and efficient game engine designed to streamline the development process for 3D applications. It provides essential components like a singleton-based GameEngine class for managing the engine's lifecycle, a Transform class for handling spatial transformations, and a Renderer class for managing rendering operations. This documentation provides a detailed overview of the key components within CoreEngine.

## GameEngine Class
The GameEngine class is the backbone of CoreEngine, serving as a singleton that orchestrates all engine components, ensuring they work seamlessly together. It is responsible for initializing the engine, managing the game loop, and coordinating the various subsystems such as rendering, physics, and input handling.

Key Features:
* Singleton Pattern: Ensures a single instance of the engine throughout the application's lifecycle, providing global access to core functionalities.
* Component Management: Manages and updates all engine components, ensuring they operate in harmony.
* Game Loop: Handles the main loop, controlling the flow of game execution, including updating game logic and rendering.
## Transform Class
The Transform class is a fundamental part of the CoreEngine, used to manage the position, rotation, and scale of a Node in 3D space. It plays a critical role in the engine's scene graph, where each Node represents an object in the scene. The Transform class uses the observer design pattern to propagate changes in its state to child nodes, ensuring that transformations are consistently applied throughout the scene hierarchy.

Key Features:
* Position, Rotation, Scale Management: Provides methods to get and set the position, rotation, and scale of a Node.
* Observer Pattern: Notifies parent and child nodes of any changes in transformation, ensuring consistency across the scene.
* Hierarchical Transformations: Automatically updates child nodes' transformations when a parent node's transform changes, maintaining the spatial relationship between objects.

## Renderer Class
The Renderer class is responsible for managing the rendering process, providing essential data and utilities required for rendering scenes in the engine. It stores information like the current window ID and the main camera, which are crucial for rendering operations.

Key Features:
* Rendering Context Management: Keeps track of the current rendering context, such as the active window and camera.
* Scene Rendering: Works with the Node and Transform classes to render objects within the scene accurately.
* Shader Management: Integrates with shader programs to facilitate advanced rendering techniques.