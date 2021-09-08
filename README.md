# TXEngine

TXEngine is a text-based game engine written in Java. The goal of this project is to create a rich toolset of game designers to build flexible and unique worlds without writing a single line of code. Instead, designers create games entirely by manipulating a set of JSON files. Further, TXEngine has a rich content designer that can assist users of the engine with writing, tracking, and checking their game components (coming soon).

## Technologies

TXEngine is designed to be as portable and lightweight as possible. As such, it does not use any heavy frameworks or engines. 
TXEngine is built in Java and use Maven for dependency management.


## Developing TXEngine

Getting TXEngine set up on your machine is simple. Install Maven (3.8+) and JDK 16 for your specific platform (available on Windows, Linux, and OSX) and then run the following commands from the root folder of TXEngine:

`mvn compile`

`mvn exec:java`

At this point, TXEngine's default game should have launched in a console window.

## Limitations

TXEngine relies on embedded color codes. These codes do not work on CMD in Windows, and as such, can't be run through it (with colors). A solution for launching TXEngine through Powershell is under development. Until then, you can run TXEngine on Windows by using either Cygwin or WSL Ubuntu, or any native Linux distribution.
