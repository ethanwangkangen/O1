Download link:
https://github.com/ethanwangkangen/O1/archive/refs/heads/master.zip

Team Name:
CodeClown Creations

Proposed Level of Achievement:
Apollo

MileStone 1:
Poster:
Video:
Project Log: https://docs.google.com/document/d/1weF3uqogUadpHA4-t_1HjwFV7HoL4UolzKsHFlo2ckI/edit?usp=sharing

Motivation:

	 As avid gamers ourselves, we are weary of the stereotype that games are "useless" or merely a "waste of time." In response, we aspire to develop a game that fosters both social interaction and friendly competition.

	Taking inspiration from popular AR mobile games like Pokémon Go and its competitors (e.g., Palworld), we aim to integrate real-time, location-based systems into our game, akin to those utilized in Pokémon Go and Ingress. This approach promises deeper immersion for players, allowing them to engage with the game world in their real-life surroundings.

	Moreover, by offering diverse environments brimming with unique creatures and challenges, our goal is to craft a captivating pet battling mobile game that motivates players to venture beyond the confines of their homes. Through this, we hope to encourage players to explore, encounter, and capture new and distinctive creatures, thereby transforming their gaming experiences into meaningful adventures.

Aim:

	Darwin’s Duel is a multiplayer pet battling mobile game with 2D pixel art style. With a branching evolutionary system and unique skills that allows players to tailor the growth of their pets to suit their battle strategies, we hope to create a game that pushes the strategic skills of the players to their limits. 	

	In Darwin’s Duel, every monster you encounter and capture can be trained and evolved through a branching evolutionary system, allowing you to tailor their growth to suit your battle strategies. Explore diverse environments, each teeming with unique creatures and challenges, making every gameplay session a new adventure.

	The heart of Darwin’s Duel lies in its competitive battles. Players can engage in real-time duels with others. Victories yield rewards and prestige, helping you climb the global leaderboards and cement your status as the ultimate Duelist.

User Stories:

	As a personal fan of Pokemon since young, I want a game that incorporates similar elements of virtual pet fighting; against both non-playable (i.e. pre-programmed) entities and other players.

	As a student, who wants to meet new people and make new friends, I want to be able to enter “combat” with other players (students from NUS) and interact with them. I also want to be incentivised to explore the outdoors more.

Project Scope:

	Darwin's Duel is an RPG mobile game, similar to Pokémon Go, where players traverse the in-game world using location-based systems that track their real-life whereabouts. Throughout their journey, players will encounter and capture a diverse array of unique creatures while exploring varied environments. These captured creatures can be trained and evolved through engaging in battles with other players, allowing them to acquire new skills and items to enhance their battle prowess.

	With an array of strategic elements to consider, including an elemental counter system, mana/AP management, and unique status conditions, Darwin's Duel transcends simplistic gameplay where a single powerful pet reigns supreme.

	By participating in real-time duels with fellow players, individuals have the opportunity to ascend the leaderboards and earn both prestige and exclusive rewards, further heightening the competitive aspect of the game.

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

	One crucial feature we plan to incorporate into Darwin's Duel is the utilization of players' real-life locations to enhance the exploration of the in-game world, as well as facilitate battling and interaction between players, akin to the mechanics of Pokémon Go.

	By integrating real-life locations, players are incentivized to explore diverse areas, challenge formidable opponents (both players and NPCs), and acquire unique, location-specific items and pets. This dynamic approach adds depth and excitement to the game, ensuring that victory cannot be achieved solely by remaining indoors and grinding for hours on end.

	To realize this vision, we intend to leverage the Google Maps API to track players' real-time locations and integrate this data into our in-game features. We hope that this implementation will create an immersive gaming experience that encourages outdoor exploration and fosters social interaction among players.

Advance Combat System (by end July) 

	For a pet-battling game, the simplest battle strategy would typically involve using pets with high stats and skills that deal significant damage. However, in Darwin's Duel, we aim to introduce complexity and depth to the battling system to prevent situations where one pet dominates all others.

	Our plans include implementing an elemental counter system, incorporating unique skills with various effects, and introducing status conditions (both positive and negative). These additions will necessitate strategic thinking and lead to unpredictable outcomes in battles. Furthermore, we will assign action points to each skill to prevent overpowered skills from being used excessively.

	The environment where battles take place, determined by the real-life location of the players, will also influence the strength of different pets. This feature will result in buffs for some pets while weakening others, adding another layer of strategy to the game.

	Overall, our goal is to make Darwin's Duel a game that demands careful strategizing and adaptability from players.

Proof-of-Concept: https://github.com/ethanwangkangen/O1/tree/updateOne

Flowchart Diagram:

Class Diagram:

Link to high resolution version: https://drive.google.com/file/d/12Qsbgy2pwt6NFLwXsRApP1EVpbVfqUeH/view?usp=sharing

![image](https://github.com/ethanwangkangen/O1/assets/118478459/f7e80ccc-8bf4-4977-bf3d-d0d1755a8b23)




