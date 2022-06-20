Project 1-2 Phase 1:
----------------

Project Description:
--------------
Developing a game engine (physics engine and GUI included) for putting.

Authors:
--------
Group 01: Marco Cattaneo Vittone, Marian Chen, Zhefan Cheng, Evgueny Nazarenko, Egor Sementul, Jason Somoglou, Drago Stoyanov

Purpose of Project:
-------------------
The objective of this project was to develop a GUI that accurately displayed the workings of a physics engine estimating the trajectory of a golf ball on a field with certain given inputs (i.e., height function, friction constants, etc.).

The main focus of the research question from this project is to determine the most efficient (yet still accurate) interval for calculating the trajectory of the golf ball over a certain time interval.

Version / Required Softwares:
----------------
Updated to work with Java 17
Gradle version 7.3.1
LibGDX version 1.10.0
IntelliJ (not compatible with VSCode)

User Instructions:
------------------

LibGDX
------

Steps
-----
- Step 1: Installing and setting up LibGDX online (https://libgdx.com/wiki/start/setup)
- Step 2: Change the file paths accordingly on the following files: HeightFunction.java, Reader, and velocityReader.java.
- Step 3 (Optional): (Un)comment accordingly to the instructions on Lines 284 and 538 in Game.java to choose velocity input
- Step 4: To run the game, go to Field.java and uncomment Line 7 (comment Line 6). Otherwise, to run Experiment.java, uncomment Line 6 (comment Line 7).
- Step 5: To play the game, launch the application by running Game() on DesktopLauncher.java. To see the bot play, launch the application by running Game2() on DesktopLauncher.java.
- Note: This application might run into errors on Macs and/or on VSCode. Windows and IntelliJ are therefore recommended for running this software.

To Play the Game:
-----------------------------------
By selecting to play the game, the application shows a green golf field with lighter greens indicating higher altitudes and darker greens indicating depressions. Elevations with a negative height indicate the presence of water, and are therefore colored blue. The left/right keys can be used to change the angle in which the ball is launched, the strength with which that is done depends on how long the space bar was held before it was released (to shoot). Alternatively, the ball's movements can also be determined separately from input text file VelocityTestTXT.txt. The game is completed once the ball has been shot into the hole (and accordingly disappears). On the other hand, if the ball was shot into the water, the ball respawns in its previous position.
Alternatively, by choosing a bot to play the game, the application first calculates the ideal xy-velocities to shoot the ball, after which has been calculated the ball will appear on the screen. To shoot the ball for a hole-in-one, place space bar. 

In order to start the game, where the user can play him/herself he/she has to use Game and not Game2 class in DesktopLauncher, also the coordinates of hole and ball have to be scaled in Game as it is in Game2 class.
In order to launch the main method in GraphGolf user has to change example_input with the data mentioned in test.txt and remove the scaling of coordinates in Game class.
In order to run the bot (modified hill climbing/rule based) user has to use Game2 class in DesktopLauncher and the select in Game2 which bot he/she wants to use 