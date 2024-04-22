<img width="547" alt="Screenshot 2024-04-22 at 18 27 13" src="https://github.com/jxcg/horse-simulator/assets/154761377/0128251c-6d2a-4d24-a6da-d7b4e06e4201"># horse-simulator

**Author:** Joshua Cameron Ng

This is a University project as part of coursework for Object Oriented Programming.

This project is divided into two parts, part1 and part2, with part1 referring to the command-line version of the game, whereas part2 refers to the graphical interface version of the game.



Notable Logs:

**Command Line Project**

# *Version 1.1: Modifications made*
- Added message to denote winning horse.
- Added a Unicode 'Cross' Emoji to denote fallen horse
- Added Current confidence via .getConfidence() within the current race next to each horse's name
- Decrement a Horse's current confidence if they fall
- Increment a Horse's current confidence if they win
- Horse name added to the side to allow for identification of each horse.
- Only have one table printed out, rather than multiple tables each time a horse moves. (Cleaner console).
- If all horses fall, winner horse is determined by furthest horse

**Prerequisites**
- In order to run the game, within src/ the three main classes have been provided.
- Please run the game by cloning the repo (or downloading the files manually - whatever works best for you, and run 'java Main.java' within a Command Line Interface / Terminal (macOS or Unix))
- This is **NOT** necessary, but it ensures that when the game is run, the game will not print out multiple lines of the same race track, as IntelliJ doesn't like to properly print out formfeed characters (despite the /u000c method and the clearScreen() method added).
- Running the game in a developer environment console will work fine, however for the intended experience, running the game in the console is intended.
- For markers, all files required to run will be provided directly via the .zip file

- How to run the game:
- Clone repository, if there are no classes available, please use 'javac File.java' to compile the available java files provided within the src/ folder
- Run "java Main.java" to produce expected results
- Example of a game:

<img width="547" alt="Screenshot 2024-04-22 at 18 27 13" src="https://github.com/jxcg/horse-simulator/assets/154761377/46e9cdd2-6c4c-4cc0-abbf-03294dbb1365">



**Dependencies**
- This project requires Java Development Kit (JDK) to compile and run.
- This program was made under JDK 21.0.2 "java version "21.0.2" within the IntelliJ IDEA Development Environment


# *Version 2.0: GUI Version*

**Dependencies**
- This project requires Java Development Kit (JDK) to compile and run.
- This program was made under JDK 21.0.2 "java version "21.0.2" within the IntelliJ IDEA Development Environment
- Java Swing

**Imports** 
-  javax.imageio.ImageIO;
-  javax.swing.*;
-  java.awt.*;
-  java.awt.event.ActionEvent;
-  java.awt.event.ActionListener;
-  java.awt.image.BufferedImage;
-  java.io.BufferedReader;
-  java.io.FileReader;
-  java.io.IOException;
-  java.util.Objects;
-  java.lang.Math;

**Steps on running:**
**Class files already provided! If required to be generated again, please follow steps below:**
- *compile.sh file provided - just run . ./compile.sh within part2/src/*
- **If this does not work, manually compile all the java provided, (Main.java, Race.java, WindowFrame.java, Horse.java, VirtualCurrency.java)**
- javac Main.java
- javac Race.java
- javac WindowFrame.java
- javac Horse.java
- javac VirtualCurrency.java

- Run java Main.java afterwards, it should run!

- Note:
- There is a duplicate set of horseData.txt and results.txt, as when running in src/ (terminal) - the game generates the files relative to the path the program is running within, within IntelliJ the files happen to be generated outside of src/, but within the terminal, the files are generated within src/, so a duplicate set is provided. If this does not load for whatever reason, appropriate error handling is still provided, and the files will automatically be created during normal usage of the game.
- *results.txt* is a File that lets people see the last results of the horse game.
- *horseData.txt* is a File that lets people load in the last results of the horse game into their current session, so they can continue where they left off.



**Features**

## 1. Display Performance Metrics
After each race, the game displays performance metrics such as confidence rating and finishing time for each horse on the user interface.

## 2. Load Previous Horse Setup Profile
Users have the ability to load previous horse setup profiles from a file, allowing them to easily reuse their favorite horse configurations.

## 3. View Horse Stats
A dedicated UI component allows users to view detailed statistics and attributes of each horse's most recent race. This includes information such as horse name, confidence rating, horse coat, and their boots.

## 4. Interactive Track Design
Players can customise the length of the race track and the number of lanes, providing an interactive and dynamic racing experience.

## 5. Custom Horses
The game allows users to create and customise their own horses, choosing attributes such as their symbol, name, confidence rating, coat, and boots.

## 6. Betting System
A comprehensive betting system is integrated into the game, allowing players to place bets on their favorite horses and compete for coins (virtual currency).

## 7. Interactive UI
An intuitive, responsive UI makes the horse-simulator even more fun!
<img width="1293" alt="Screenshot 2024-04-21 at 13 06 44" src="https://github.com/jxcg/horse-simulator/assets/154761377/1e2cc55c-0570-43c1-86dd-b6ecee3a53a0">


