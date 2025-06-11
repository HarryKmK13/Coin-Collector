# Build Your Own World

### About the Project
Build Your Own World (BYOW) is a tile-based adventure game developed as part of UC Berkeley’s CS 61B Spring 2025 Project 3. The game procedurally generates a unique world of rooms and hallways using a user-input seed. Players can explore the world using keyboard controls, collect coins, and save/load their progress at any point. The game focuses on software design, algorithmic thinking, object-oriented programming, and creativity.

This project was developed in Java, leveraging a custom graphics engine (TileEngine) to render the world and support interactivity.


### Demo
<div style="text-align:center">
<img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcTAzejlrdjhieG16Z3p3YTdoeDQwNGhzdWowNHdjdDRmZDdpOGJjMCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/zqUuf4JUjvULprEvL5/giphy.gif" width="500"/>
</div>

<div style="text-align:center">
  <img src="image.png" alt="Default Demo" width="500" />
  <img src="image-1.png" alt="Star Demo" width="500" />
</div>
<p align="center">
    <strong> Default Theme[left] and Star Theme[right] </strong>
</p>


### Project Structure
<p align="center">
  <img src="UML copy.png" alt="UML" width="1000">
</p>
<p align="center">
    <strong> UML Diagram </strong>
</p>



### Algorithms
Creates hallways to connect all rooms on the game board. The algorithm connects rooms by finding the closest unconnected neighbor for each room. The algorithm uses `Stack` and `HashSet` to keep track of the connected rooms. It guarantees that all the rooms are at least **connected as a tree**. *It is possible that the rooms are connected as a graph*. 

A `PriorityQueue` was also used so that the algorithm runs from the leftmost room (i.e the room that has a center, which is the leftmost related to other rooms).


### Persistance
<sub><sup>(Coming Soon)</sub></sup>


### Contributors
**Kyaw Min Khant**

### Acknowledgement
- Documentation and README are written with the help of LLM.

#### Notes for Reference:
Currently using files

- [x] `Point.java`
- [x] `Room.java`
- [x] `Board.java`
- [x] `RoomGenerator.java`
- [x] `RoomPlot.java`
- [x] `Main.java`
- [x] `AutograderBuddy.java`
- [x] `World.java`
- [x] `Theme.java`
- [x] `TERenderer.java`
- [x] `TETile.java`
- [x] `Tileset.java`
