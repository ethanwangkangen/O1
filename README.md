Team Name:
CodeClown Creations

Proposed Level of Achievement:
Apollo

MileStone 1:
Poster:
Video:
Project Log: https://docs.google.com/document/d/1weF3uqogUadpHA4-t_1HjwFV7HoL4UolzKsHFlo2ckI/edit?usp=sharing

Motivation:

	As long-time gamers, we are tired of games being seen as “useless” and “ a waste of time”. As such, we hope to create a game that encourages both social interaction and friendly competition. Inspired by popular AR mobile game Pokemon Go and its rivals (eg. Palworld), we intend to incorporate real-time, location-based systems (similar to those used in games like Pokemon Go and Ingress) for deeper immersion. Coupled with diverse environments, each teeming with unique creatures and new challenges to overcome, we want to create a captivating pet battling mobile game that will encourage people to leave the comforts of their homes to encounter and capture new and unique creatures.

Aim:

Darwin’s Duel is a multiplayer pet battling mobile game with 2D pixel art style. 

	With a branching evolutionary system and unique skills that allows players to tailor the growth of their pets to suit their battle strategies, we hope to create a game that pushes the strategic skills of the players to their limits. 	

	In Darwin’s Duel, every monster you encounter and capture can be trained and evolved through a branching evolutionary system, allowing you to tailor their growth to suit your battle strategies. Explore diverse environments, each teeming with unique creatures and challenges, making every gameplay session a new adventure.

	The heart of Darwin’s Duel lies in its competitive battles. Players can engage in real-time duels with others. Victories yield rewards and prestige, helping you climb the global leaderboards and cement your status as the ultimate Duelist.

User Stories:

	As a personal fan of Pokemon since young, I want a game that incorporates similar elements of virtual pet fighting; against both non-playable (i.e. pre-programmed) entities and other players.

	As a student, who wants to meet new people and make new friends, I want to be able to enter “combat” with other players (students from NUS) and interact with them. I also want to be incentivised to explore the outdoors more.

Project Scope:

	Darwin's Duel is a RPG mobile game, similar to that of Pokemon Go.
	Players travel the in-game world through location-based systems that track their real-life locations. They can encounter, capture a wide variety of unique creatures while exploring diverse environments. They can then train and evolve these pets by engaging in battles with others while obtaining new skills and items that will enhance their battle capabilities. With various battle elements that players have to consider (eg. element counter system, mana/AP management, unique status conditions), Darwin's Duel is more than a simple game where a strong pet beats all.
 	By engaging in real-time duels with other players, they can climb leaderboards and gain both prestige and unique rewards. 

Features implemented in MileStone 1:

Basic networking for online 2-player combat

	Kyronet is a Java Library that provides a API for TCP and UDP client/server network communication.	
 	Using Kyronet, we have allowed for multiplayer (2-player) combat across our server. Players will be able to engage in real-time combat and take turns attacking each other by sending attack commands to our server.  

Turn-based combat system

	We have implemented a turn-based combat system, where players can take turns attacking each other. Their health points (HP) will be indicated with both a label ("remaining HP / max HP") and a animated HP bar. When the HP of the pet of a player reaches 0, he/she will lose the battle, rendering a "you have lost" popup, while a "you have won" popup will appear for the opposing player.
 
Basic visuals

 	Basic visuals have been implemented, in both the gamescreen and battlescreen, and some pets and player sprites have been created. 
 
User Login Screen

	The login screen has been created. By clicking on the "login" button, players will be able to create a player that has the username typed into the "username" text field. Currently, players created will only one pet called MeowmadAli.


Features to be implemented in future milestones:

	At this point, we have completed the battle feature, as well as the multiplayer aspect for a basic proof of concept. Moving forward, we aim to add more features and gameplay mechanics, as well as improving the design of the game.

Features to be implemented by mid June
	
 	1. Database to store data of players, allowing players to save their progress
	2. Additional features such as menu, setting, etc.
	4. Create a large RPG world that players can move around and explore
 
Features to be implemented by end June
	
 	1. location-based GPS system
  	2. Improve battle system by adding elements such as items
   	3. Multithreading to allow for larger player base and simultaneous battles

Features to be implemented by mid July

	1. Create a wider variety of pets
 	2. Improve visual quality and include animations
  	3. Include NPCs that players can engage in battles with
   	4. Game progression features such as leveling, currency, upgrades, evolution

Features to be implemented by end July

	1. Theme music and SFX
	2. Balancing of pets and fine-tuning stats and skills
 	3. Improving efficiency of game
 	4. Leaderboard feauture

Future Major Plans:

Location-based GPS system (by end June)

 	One important key feature we plan to have for Darwin's Duel is to use the real-life locations of the players to facilitate the exploration of the in-game world, as well as the battling and interaction between players, similar to that of Pokemon Go. 
	Through this, players are encourage to travel around in-real-life in order to explore new and diverse areas, challenge new and powerful opponenets (both players and NPCs), and obtain unique, location-specific items and pets. This makes the game more interesting and fun, one that cannot be won by just staying indoors and grinding hours on end. 
 	To achieve this, we plan to use Google Maps API to track the real-time location of players and utitlize this data in our in-game features. 

Advance Combat System (by end July) 

	For a pet-battling game, the simplest battle strategy would be to use pets with high stats as well as skills that deal the most amount of damage. 

Proof-of-Concept

Class Diagram:

Link to high resolution version: https://drive.google.com/file/d/12Qsbgy2pwt6NFLwXsRApP1EVpbVfqUeH/view?usp=sharing

![image](https://github.com/ethanwangkangen/O1/assets/118478459/f7e80ccc-8bf4-4977-bf3d-d0d1755a8b23)




