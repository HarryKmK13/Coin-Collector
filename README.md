# Build Your Own World

### About the Project
[Needs description of the project here]


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
  <img src="UML.png" alt="UML" width="1000">
</p>
<p align="center">
    <strong> UML Diagram </strong>
</p>

---

<p align="center">
  <img src="ActivityDiagram.png" alt="Activity Diagram" title="Activity Diagram" width="300">
</p>
<p align="center">
    <strong> Activity Diagram </strong>
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
- [ ] `ThemeSet.java`  <sub><sup>(Temporarily removed due to AutoGrader tests)</sub></sup>
- [x] `TERenderer.java`
- [x] `TETile.java`
- [x] `Tileset.java`
