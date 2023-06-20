# CO-TERMINUS-Java
CO-TERMINUS Game developed with Java using the gdx library(Libgdx), link to
the library here: https://libgdx.com/

## Overview 

CO-TERMINUS is a very small experimental prototype of a 2D action game.
Right now there's no game UI in the prototype, just game logic, 2D graphics and animations.
Because of that, when the player dies i directly close the application. 
In an ideal scenario on player death i would display a UI menu to the player to
ask him if he wants to quit or restart the game, but considering the complexity
of the library there wasn't time to implement it.

## Features

- 2D Sprites animation and rendering
- 2D movement
- Primitive AI test enemy
- Primitive ECS implementation
- A small map made using the Tiled editor which you can find here: https://www.mapeditor.org/.
- Collision detection framework for each Game Entity.

Project build and dependencies are handled with Maven, look at pom.xml for more
details.