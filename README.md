# TheAlley

The Alley is a text-based fantasy RPG. It is currently under heavy development and is following a "lore-first" writing and development cycle. 

## Technologies

The Alley is designed to be as portable and lightweight as possible. As such, it does not use any heavy frameworks or engines. 
The Alley is built in Java and use Maven for dependency management.

## Developing The Alley

Getting The Alley set up on your machine is simple. Install Maven for your specific platform (available on Windows, Linux, and OSX) and then run the following commands:

`mvn compile`

`mvn exec:java`

At this point, The Alley should have launched in a console window.

## Limitations

The Alley relies on embedded color codes. These codes do not work on CMD in Windows, and as such, can't be run through it. A solution for launching The Alley through Powershell is
under development. Until then, you can run The Alley on Windows by using either Cygwin or WSL Ubuntu.
