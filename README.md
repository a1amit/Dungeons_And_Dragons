# Dungeons and Dragons Board Game

This is a single-player multi-level version of a Dungeons and Dragons board game implemented in Java. In this game, you are trapped in a dungeon and surrounded by enemies. Your goal is to fight your way through them and reach the next level of the dungeon. The game consists of multiple levels, and you win the game by completing all the levels.

## Game Description

The game is played on a board represented by a 2-dimensional array of characters. The board consists of different tiles, including walls, empty areas, the player, and enemies. The player character is represented by the symbol '@', and enemies are represented by different symbols such as 'B', 's', 'k', and 'M'. Dots represent free areas, and '#' symbols represent walls.

## Game Flow

1. The user chooses a player character from a list of pre-defined characters.
2. The game starts with the first level. Each level consists of several rounds.
3. In each round, the player performs a single action, followed by each enemy performing a single action.
4. The level ends when all the enemies are defeated, and the next level is loaded.
5. The game ends when the player completes all levels or if the player dies.

## Player and Enemy Units

### Player

The player character has the following properties:
- Name
- Health (health pool and current health)
- Attack points
- Defense points
- Experience
- Level
- Special ability (unique to each player type)

The player character can level up, gain experience by killing enemies, and cast a special ability to improve their situation.

There are three player types:
1. Warrior: Special ability - Avenger's Shield
2. Mage: Special ability - Blizzard
3. Rogue: Special ability - Fan of Knives

### Enemy

There are two types of enemies:
1. Monster: Moves around the board and can chase the player if within its vision range.
2. Trap: Remains stationary but can change visibility and attack the player if within range.

Enemies have experience values, and the player gains experience by defeating them.

## Combat System

Combat occurs when the player and an enemy are in the same location. The attacker rolls an attack value, the defender rolls a defense value, and damage is calculated based on the difference between the rolls. The defender may die if their health reaches or falls below 0.

## Command Line Interface (CLI)

The game is interacted with through a command line interface. The user can select actions such as moving, casting special abilities, or doing nothing. The CLI displays the game state after each round, including the board, player stats, combat information, and level up notifications.

## Forms of Input

The game takes a command-line argument, which is the path to a directory containing files representing the game levels. Each level is represented by a text file named "level<i>.txt", where <i> is the level number.

## Notes
  For More information, read the file in the instructions folder
