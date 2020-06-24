# Game_of_Life
The program allows you to observe the evolution of different configurations with different rules in the game of life.<br/>

The window has two components: the settings menu and, of course, the game board. on the game board you can create any configuration you need by clicking on the black cells, after which they become white, which means that they are "alive". Before starting, in the settings you need to enter the rules when the cells "come to life" and when they "die" (in the fields "Orzywa" and "Umiera" respectively) and then click "Wprowadz regoly" to confirm values. Fields for the rules accept an unlimited number of arguments in the formats: "x", "x,y", "x-y,z", "x-y", where x, y, z are positive integers, "," - separates single values , "-" - range from left value to right value. In the future, the rules can be changed during the simulation.<br/>

To clear the game board ("kill" all cells) click "Wyczysc" button.<br/>

To start or stop the simulation at the current position, click the "Start" and "Stop" buttons, respectively.<br/>

Opposite sides of the game board are considered interconnected. That is, for example, the neighbors of the cell [maxColumnsValue][6] are also cells at the addresses: [1][5], [1][6], [1][7]
