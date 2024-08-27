https://docs.google.com/document/d/1DwfnI6AzMFVhtee-DWFDQCVM1g0Ek8ns7yIK4_hRM4g/edit?usp=sharing

Orbital MileStone 3 README
CodeClown Creations 
Members: Wang Kang En Ethan, Goh Sze Kang
Proposed Level of Achievement: Apollo


Instructions to Run Darwin’s Duel

MUST READ
Since Darwin’s Duel is an online multiplayer game, it requires us to run a local server for the game to run.
(AKA the Darwin's Duel is currently unplayable.)
Thank you for your understanding and patience!
(server status: offline)

APK download: 
	https://drive.google.com/file/d/1IHzeqC28vdASCS83Ajp9lerSAbFvFcW-/view?usp=sharing

Video: 
	<Explanatory video>
https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*


Aim:

Darwin’s Duel is a multiplayer pet battling mobile game with 2D pixel art style. With an elemental counter system and unique skills that allows players to tailor the growth of their pets to suit their battle strategies, we hope to create a game that pushes the strategic skills of the players to their limits. 	

In Darwin’s Duel, every monster you encounter and capture can be trained and evolved through a branching evolutionary system, allowing you to tailor their growth to suit your battle strategies. Explore diverse environments, each teeming with unique creatures and challenges, making every gameplay session a new adventure.

The heart of Darwin’s Duel lies in its competitive battles. Players can engage in real-time duels with others. Victories yield rewards and prestige, helping you climb the global leaderboards and cement your status as the ultimate Duelist.

Motivation:

As avid gamers ourselves, we are weary of the stereotype that games are "useless" or merely a "waste of time." In response, we aspire to develop a game that fosters both social interaction and friendly competition.

Taking inspiration from popular AR mobile games like Pokémon Go and its competitors (e.g., Palworld), we aim to integrate real-time, location-based systems into our game, akin to those utilized in Pokémon Go and Ingress. This approach promises deeper immersion for players, allowing them to engage with the game world in their real-life surroundings.

Moreover, by offering diverse environments brimming with unique creatures and challenges, our goal is to craft a captivating pet battling mobile game that motivates players to venture beyond the confines of their homes. Through this, we hope to encourage players to explore, encounter, and capture new and distinctive creatures, thereby transforming their gaming experiences into meaningful adventures.


User Stories:

Priority
As a …
I want …
Must have
Pokemon Fan
to play a game that incorporates similar elements of virtual pet fighting; against both non-playable (i.e. pre-programmed) entities and other players
Must have
User
meet new people and make new friends by entering “combat” with other players using GPS location tracking system
Must have
Gamer
complex battle systems that require careful planning and adaptability, allowing me to refine my strategies and overcome challenging opponents
Must have
Gamer
a strategy game that will test my thinking so that I can challenge myself
Must have
Gamer
I want a game with a strong sense of progression and achievement to keep me engaged and motivated
Must have
Casual gamer
a short and simple game that I can pick up at any time, so that I can pass the time when I am bored
Must have
Casual gamer
intuitive controls and straightforward gameplay mechanics, allowing me to enjoy the game without a steep learning curve
Should have
Gamer
a rewarding leveling system that reflects my efforts and progress, granting new abilities, pets, and challenges as I advance
Should have
Casual gamer
quick battles and activities that fit into my busy schedule, enabling me to enjoy the game even during short breaks or commutes
Should have
Collector
a wide variety of pets or creatures to capture, train, and customize, allowing me to build a unique and personalized team
Good to have
Competitive gamer
robust multiplayer features with rankings and leaderboards, so I can gauge my skills against other players
Good to have
Strategist
deep gameplay mechanics such as skill combinations, buffs, and debuffs, allowing for intricate battle strategies and tactical decisions
Good to have
Strategist
challenging AI opponents and scenarios that test my strategic thinking, offering opportunities to apply advanced tactics and strategies.


Project Scope:

Darwin's Duel is an RPG mobile game, similar to Pokémon Go, where players traverse the in-game world using location-based systems that track their real-life whereabouts. Throughout their journey, players will encounter and capture a diverse array of unique creatures while exploring varied environments. These captured creatures can be trained and evolved through engaging in battles with other players, allowing them to acquire new skills and items to enhance their battle prowess.

With an array of strategic elements to consider, including an elemental counter system, mana/AP management, and unique status conditions, Darwin's Duel transcends simplistic gameplay where a single powerful pet reigns supreme.

By participating in real-time duels with fellow players, individuals have the opportunity to ascend the leaderboards and earn both prestige and exclusive rewards, further heightening the competitive aspect of the game.

Tech stack
LibGDX & Java
Game engine
Firebase (Authentication and Realtime Database)
For user login and retrieval/storage of player data, as well as location updates and online status
Kryonet
For PVP and PVE  battles. Client-server connection. 
Server hosted on our local server.
Google Maps API & GPS location tracking
For real time location display of self and enemies on the map
Android emulator (Android studio)
Required to run the program
Features: 

Features
Features
Description
Firebase
Utilizes Firebase for storing user information. 
Enables players to save their progress securely.
GPS Tracking System
Enables real-world battles based on geographical locations. 
Allows players to locate opponents and NPCs on the map, encouraging exploration and physical world interaction
Online Multiplayer Battling System
 Facilitates real-time player-versus-player combat.
NPC battling
Engages players in battles against NPCs.
Provides experience points (EXP) and unlocks new pets for players.
Elemental Counter System
Categorizes pets into elemental types: FIRE, WATER, EARTH.
FIRE > EARTH > WATER > FIRE
Pet Levelling System
Allows pets to gain EXP through battles and level up
Enhances stats like HP and skill damage upon leveling up.
Welcome screen
Features a message from developers to create excitement.
Pet Change Screen
Enables customization of battle teams with three pets by letting players swap pet positions and select battle order.
Attribute Screen
Provides detailed information about players' pets. 
Displays name, health points, level, elemental affinity, skills, and status effects.
Animations
Adds dynamic and idle animations for battle actions and pets. 



Firebase for User Login and Sign up



To enhance the user experience and ensure that progress is saved securely, we have integrated Firebase's authentication and database APIs for user login and sign-up. Players can now create accounts using their email and password, allowing them to save their progress and continue their journey anytime, anywhere. This feature provides peace of mind by safeguarding players’ progress and achievements.

New players start their adventure with three unique pets: MeowmadAli, CrocLesnar, and Froggy. As they progress through the game, they can gradually unlock more pets, each with its own unique abilities and characteristics. This system ensures a seamless and personalized gaming experience, making it easier for players to dive into the exciting world of Darwin’s Duel.

With these features in place, players can enjoy a more immersive and connected gameplay experience, knowing that their hard-earned progress is always saved and accessible.


GPS Tracking System 




One of the standout features of Darwin’s Duel is its innovative GPS tracking system. This technology allows players to engage in real-world battles based on their geographical locations. When players are in proximity, they can spot each other on the map and send battle requests. If the opponent accepts, the battle commences, blending the digital and physical worlds in an exciting and immersive way. This feature is not only a technical challenge but also a game-changer, encouraging players to venture outside and interact with the world around them.

The map also displays NPCs (grey) that players can choose to right to gain new pets or level up existing ones, as mentioned previously.

Players first will click on an enemy to select them as their target enemy. This can either be an NPC or another player.
If a player attempts to click the “Fight” button before selecting a valid enemy, the game will prompt them to do so again.

Battling requests

When one user has successfully targeted another online player and requests to fight them, that player will then receive a popup as shown below. His response will determine whether or not the battle between them starts.


Online multiplayer battles
We have implemented multiplayer combat functionality using Kryonet by setting up a Java-based server to manage connections and facilitate real-time interaction between players. Each player connects via a client application that communicates with the server using Kryonet's API. This setup enables players to engage in real-time combat by sending commands to the server, which processes these commands to determine outcomes such as damage calculation and status effects. This ensures fair gameplay mechanics and synchronized game states across all connected clients.
Our multiplayer combat system integrates turn-based mechanics within a real-time framework, allowing players to alternate actions while receiving immediate feedback from the server. This approach leverages Kryonet's efficient TCP protocols for low-latency communication, essential for maintaining a smooth and responsive gameplay experience. 
Sub-features:

Attacking opponent

Your Turn


Opponent’s Turn

During their turn, players can attack their opponent using one of their pet's skills. Each skill comes with an associated status effect, represented by a status effect icon, which provides information about the skill's impact. These icons indicate whether the skill inflicts poison, stun or absorb, allowing players to plan their strategy effectively.

Pet change during battle


Pet Change function

Gone are the days of waiting for pets to faint before switching! In Darwin’s Duel, players can now change their main pet during battles, unlocking a plethora of strategic possibilities. This feature allows players to adapt their tactics on the fly, introducing new ways to counter opponents' moves and turn the tide of battle in their favor. The ability to swap pets mid-fight adds a dynamic element to the gameplay, making every match more unpredictable and exciting. With this flexibility, players can experiment with different combinations and strategies, ensuring that each battle is a fresh and thrilling experience.

Skip turn

With the introduction of the Stun status effect in Darwin’s Duel, pets may now be unable to carry out an attack during their turn. This adds a new strategic layer to battles, as players must plan around the possibility of their pets being temporarily incapacitated.

To address this, we've added a Skip Turn button. This feature allows players to strategically skip their turn while waiting for the stun effect to wear off, providing an opportunity to regroup and plan their next move. The Skip Turn button ensures that players can adapt their tactics, making gameplay more dynamic and engaging.


NPC Battling System

In Darwin’s Duel, players have the option to engage in battles with NPCs, utilizing similar logic to that used in online multiplayer battles. These NPCs simulate real combat scenarios, providing players with a realistic and challenging experience.




Benefits of NPC Battles:

Gain EXP: NPC battles are an excellent way to earn experience points, allowing players to level up their pets and strengthen their skills. This is perfect for those who want to hone their skills and build stronger teams at their own pace.

Unlock New Pets: Players can acquire new pets by defeating them in battle. Successfully conquering specific NPCs grants players the chance to add these defeated pets to their collection, expanding their team’s capabilities.

Stress-free battling: Without the pressure of competing against other players, players are able to test out new strategies against NPCS, preparing for actual combat against other players. 

This feature adds depth to the gameplay, offering players various ways to grow their teams and refine their strategies. Whether preparing for player-versus-player combat or simply looking to enhance their roster, NPC battles provide valuable opportunities for progression and strategic development in Darwin’s Duel.



Won battle


Lost battle

Elemental Counter System

In Darwin’s Duel, we've introduced an Elemental Counter System to elevate gameplay strategy. Each pet is now categorized into one of three types: FIRE, WATER, or EARTH. These elements interact strategically—FIRE triumphs over EARTH, EARTH prevails against WATER, and WATER overcomes FIRE.

This system introduces a dynamic layer of strategy where elemental advantages yield a 1.35x damage bonus, while disadvantages result in reduced damage at 0.65x. This mechanic encourages players to carefully consider their team compositions, fostering deeper tactical planning. By enriching each battle with strategic decision-making, this addition enhances the overall intensity and complexity of encounters in Darwin’s Duel.


Pet Levelling System

In Darwin’s Duel, pets can level up through combat, gaining strength and new abilities. By participating in battles against other players or NPCs, pets earn EXP, with victories awarding more experience points. Once a pet accumulates enough EXP to reach its maximum, it levels up, enhancing its stats, including HP, MP, and skill damage. This progression allows players to see tangible improvements in their pets' capabilities.

To maintain the balance of Darwin’s Duel, pets are capped at level 30. This ensures a fair playing field and encourages players to strategize and make the most of their pets’ abilities at each level.

This leveling system introduces a sense of progression and achievement, encouraging players to invest time in training and strengthening their pets. The prospect of evolving stronger pets adds a compelling incentive for players to engage more deeply with the game, as they work towards building the ultimate team.



Welcome Screen



When new players create a Darwin’s Duel account, they are greeted by a personalized welcome screen. This screen features a special message from us, the developers, designed to make players feel appreciated and excited about their upcoming adventure. Our goal is to set the stage for an immersive and thrilling experience in the world of Darwin’s Duel, where players can explore, strategize, and conquer.


Pet Change Screen 

Players can select the three pets they wish to bring into battle from the Pet Change Screen. This enables players to add or remove pets from their battle team. Players can even choose the order in which they will enter the fight. This flexibility introduces new strategic layers, allowing players to craft customized teams and develop innovative combat strategies tailored to different opponents and scenarios.

Sub-features:
Clicking different battle pets consecutively swaps their positions in the battle team.
Clicking an empty slot followed by a reserve pet (or vice versa) adds the pet into the battle team.
Clicking a battle pet followed by a reserve pet (or vice versa) swaps their positions, swapping pets into the battle team.
Double-clicking the same battle pet consecutively removes it from the battle team.
An error message is displayed when a player tries to remove the last battle pet from the battle team.


Original team


Changing the order of battle team


Changing pets in battle team


Removing pets 
Error Message

Attribute Screen

The Attribute Screen is a vital tool for players, providing a comprehensive overview of the pets they own. This feature allows players to delve into the specifics of their pets' capabilities, helping them strategize and optimize their gameplay for upcoming battles.

Key Information Displayed:

Name: Each pet comes with a unique name, acting as its identifier. Each pet also has its own set of unique skills that distinguish it from others.

Health Points (HP): This indicates the pet's maximum health points and its capacity to withstand and survive damage. 

Level: The pet's level reflects its experience and power. Higher-level pets are generally stronger, capable of executing more powerful attacks and enduring more damage.

Element: Pets are associated with specific elemental affinities, such as fire, water, or earth. These elements significantly influence battle strategies, affecting both offensive and defensive capabilities. Understanding your pet’s element can help you make strategic decisions during combat.

Skills: A detailed list of the pet's skills is provided, including:

Skill Names: The unique identifiers for each skill.

Status Effects: Any additional effects that the skill may impart on the opponent or the pet itself, such as poison or stun or absorb.

Skill Description: A detailed explanation of the skill, including insights into its status effects and potential damage output.

By understanding and utilizing the information from the Attribute Screen, players can make informed decisions about their pets, tailoring their strategies to exploit their pets' strengths and mitigate their weaknesses. This level of insight empowers players to maximize their chances of victory in battles.



Pet Attribute Screen



Unique Pets and Skills









Elementary Starter Pets





MeowmadAli 
MouseHunter 
Froggy

















Advanced Pets





Dragon
Doge
Croc Lesnar



We are excited to introduce the six distinct pets in Darwin’s Duel, all drawn and designed by our talented artist and team member, Ethan. Each pet boasts unique skills and abilities, unlocking new strategies and playstyles for players to explore. This diversity enhances the game's variety and strategic depth, offering players fresh opportunities to experiment with different tactics and combinations.

Starter pets vs Advanced pets:

All new players receive the 3 starter pets upon entering the game: MeowmadAli, MouseHunter, and Froggy. 
As players progress, they unlock more formidable creatures known as advanced pets. These include: Dragon, Doge, and Croc Lesnar.

Starter Pets: Available to all new players, starter pets offer a balanced introduction to the game’s mechanics. They are essential for early-game strategy and allow players to learn the basics of elemental matchups and skill utilization.

Advanced Pets: As players advance in the game by defeating NPCs, they unlock advanced pets. These pets are stronger than starter pets at the same level and boast higher damage outputs. However, with strategic use of elemental advantages or higher levels, starter pets can still defeat advanced pets. This balancing encourages players to think strategically and utilize their entire team effectively.

Damage output of skills: 

The skills in Darwin’s Duel have varying levels of damage output, contributing to the strategic complexity of battles. Each skill falls into one of four damage categories, ranging from the least to the most powerful:

Status effects of skills: 

Skills in Darwin’s Duel come with unique status effects that add layers of strategy to battles. 

POISON: Poisons the opponent’s pet for 3 turns. Poisoned pets take damage equal to 30% of the skill’s damage every turn, gradually weakening them over time.

STUN: Has a 50% chance to stun the opponent’s pet for 3 turns. Stunned pets are unable to carry out attacks, leaving them vulnerable and unable to defend themselves. Players can still change to other pets or skip their turn.

ABSORB: Pets with the Absorb skill recover 30% of the damage dealt to the opponent, providing a valuable source of healing during combat.


Animations

We have incorporated dynamic animations into Darwin’s Duel to enhance the visual appeal and improve the overall player experience. When pets execute attacks during battles, players can enjoy engaging attack animations that bring each encounter to life. In addition, we have added idle animations for pets, providing a more lively and immersive environment throughout the game.



Idle Animation 	


Attack Animations


Code design
A large aspect of our program is the interaction between the Firebase API, Google maps API, and the game files. Below is a high-level overview of how our program works, including the extensive use of interfaces to connect the APIs, which are located in the “Android” module, to the game, located in the “Core” module.

Services UML



As is shown in the diagram above, multiple interfaces have been implemented to provide abstraction layers. These interfaces were used to link the “Android” module (where all the methods relating to Firebase and GoogleMaps API dependencies where located) and “Core” module (where the game classes were located). It was important to prevent cyclic dependency, ensuring that the “Android” module depended on the “Core” module and never the other way round. 

For reference, in the above diagram, only the DarwinsDuel (the main game class) and Myclient classes are located within the “Core” module.

All logic on the game-side would be written inside the “Core” module, and when it is necessary to access methods that require use of the Map or Firebase database/authentication, the methods declared by the interfaces would be called. For example, when the game is required to display the Map, from within the “Core” module, the game accesses the static instance of AndroidLauncher and calls the showMap() function. This is possible since the interface is located in the “Core” module. AndroidLauncher implements this interface and its methods inside “Android” module, adhering to the one-way dependency between the modules. Calling the method will then send an Intent to start the Map activity, displaying the map, which is caught by a broadcast receiver within “Android”. The use of receivers and intents is explained further below.


Aside from interfaces, we also used a combination of Receivers and Intent sending to communicate between the different modules.

This allowed us to communicate to the Map activity (which made use of the Google Maps and Firebase APIs to display users in real time) from the LibGDX game instance, which cannot be done directly since they are different Android activities.

Examples include starting the map activity from the game, and sending a request to battle another player or an NPC from the map. 




Server-client connection and battling logic

To facilitate real-time battles between users, we used Kryonet, a Java library that provides an API for efficient TCP and UDP client/server network communication using NIO.
In adherence with the Single-Responsibility principle, the MyClient class holds an instance of the kryonet.Client object and is responsible for interacting with the server.

When the program wants to send a request to the server, it will have to call a static method within MyClient to send a request to the server in the form of an Event. This will be handled in a separate thread from the main thread.

Different Events have been written which corresponds to different actions being taken by the server. Each event is a serialized instance of a particular object, for example PlayerJoinServerEvent. The server has a Listener which will identify the type of object received and handle the response accordingly.

The same logic is applicable to the client, which similarly holds a listener for objects received by the Server.


The server contains a table that maps each user’s unique ID (retrieved from Firebase) with their Connection, an object that allows communication between the server and each unique player.






The ServerPlayerHandler class above is responsible for keeping track of each player and the Connection object used to identify them.



The ServerBattleHandler.is responsible for keeping track of all the battles that are currently ongoing between pairs of battling players. Each battle is encapsulated in a BattleState (below), and the server has a table of BattleStates mapped to one of the player’s unique userID. 

The NPC class extends from Player class, as seen in the class diagram in the next page. To adhere to the Liskov Substitution Principle, BattleState works the same way with 2 players as it does with 1 player and an NPC, except for NPC attacking which has to be automated. This is done by checking a boolean againstNPC, which if true, will call the NPCAttack() method during the battle. This is all handled by ServerBattleHandler.



Software Engineering Principles and Design Patterns

Encapsulation and Open-Closed principle


Overview of class diagram (entities)


Our classes also make use of the open-closed principle. Adding more Creatures will not require any modification of the Creature class or other external classes/methods that make use of it, and simply requires extending the abstract Creature class and initialising the fields with the Creature’s stats and unique skills. We chose to make NPC extend from Player due to the similarities in functions and encapsulated information. Other classes that make use of Player class, such as BattleState/BattleHandler that call getBattlePets() will still be able to do so with NPC.



Singleton Design pattern

This design pattern is used throughout our code base. One example is given above, where there is a single static instance of our server used throughout the program. The same applies for the client instance. 
The constructor for ServerFoundation is private, preventing instantiation of ‘ServerFoundation’ from outside the class, ensuring that only one instance is created. The ‘private static ServerFoundation instance’ variable holds the single instance of the class, and is only initialised once when main is called.
Using the Singleton pattern in this context ensures that there is only one instance of the ServerFoundation class managing the server. This is crucial for maintaining a consistent state across the application and avoiding issues that may arise from having multiple server instances, such as conflicts in binding ports or managing connections and events. The Singleton pattern also makes it easier to manage shared resources and provides a single point of control for the server's lifecycle.
Single Responsibility Principle (SRP)
This principle states that a class should have only one reason to change, meaning it should only have one job or responsibility.


In the BattleState class, the primary responsibility is to manage the state and flow of a battle. This includes tracking whose turn it is, handling attacks, changing pets, scheduling NPC attacks, and checking the status of players and pets. The class does not attempt to manage the internal details of how players or pets function beyond what is necessary for the battle mechanics. By focusing solely on managing battle-related operations, the class adheres to the SRP.
Separation of concerns
Separation of Concerns involves dividing a program into distinct sections, each handling a separate aspect of the program’s functionality. In the MapActivity class, this principle is evident in the way the code is structured to handle different responsibilities:
Google Maps Initialization and Handling:
The code related to setting up and handling the Google Maps functionality is encapsulated in methods like onMapReady, displayAll, displayNPCs, and onMarkerClick.
onMapReady sets up the map, moves the camera to the user's location, and initializes marker click listeners.
displayAll manages the display of all online players, adding and updating markers as needed.
displayNPCs handles the display of non-player characters (NPCs) on the map.
onMarkerClick handles the logic when a marker on the map is clicked, distinguishing between player markers and NPC markers.
Firebase Integration:
The Firebase-related functionality is separated into distinct parts of the code. For instance, initializing Firebase references (database, databaseUsers, auth, userStatusDatabaseReference), and handling Firebase data updates (e.g., setUserOnlineStatus, sendLocationToFirebase).
setUserOnlineStatus listens for connectivity changes and updates the user's online status in Firebase.
sendLocationToFirebase updates the user's location in Firebase at regular intervals.
Location Services:
Location services are encapsulated in methods like startLocationUpdates and sendLocationToFirebase.
startLocationUpdates initializes the FusedLocationProviderClient and requests location updates, setting a callback to handle location results.
sendLocationToFirebase sends the location data to Firebase and updates local variables.
Broadcast Receivers and Intents:
The broadcast receiver for handling intents related to battles and map activity actions is registered and unregistered in the onCreate and onDestroy methods respectively.
Methods like sendBattleReqToEnemy, sendBattleReqToNPC, sendQuitToLibGDX, and sendAttributeToLibGDX handle specific intents and broadcast actions, ensuring that the intent-related logic is modular and separated from the rest of the code.

Testing
Unit Testing


We implemented unit testing using JUnit tests. The above LoginTest was used to check that the basic Firebase login and sign up logic worked, before continuing to implement it within the program. This was done using a mock authentication service that would simulate the signup/signin process and return the callback, after which the ensuing logic in the program would be executed in the actual code, in particular the retrieval of Player data.



This was another small unit test used to check the basic functionality of the main game class. It was used to ensure that the game started out displaying the splash screen and loaded the textures properly upon startup.


This test was used to check that we were able to access the game instance (gameCommunication) from within the Android module, ie. ensuring that dependencies between Android and Core modules were correctly set up. The methods (onQuitMapActivity(), onNPCReqReceived() etc.) were used within the Map activity to communicate with the game instance which would then execute things like sending requests to battle the enemy player.


Integration testing 

Considering the many different components to our program, integration testing was important to make sure that everything worked together well. Below are the test cases we used.

*Map = GoogleMaps API integration
Units/Modules checked
Test case
Initial Result
Improvements/changes made
Game, Firebase
Upon signup, new player created and data accurately uploaded to database
Pass


Game, Firebase
Upon login, data accurately pulled from database and de-serialised. All pets, skills and associated data accurate.
Fail
Problem with deserialisation. Fixed with changing return type of a function
Firebase, Map
Player online status accurately reflected in database
Pass


Android device, Map, Firebase
Player location accurately sent to and updated in firebase
Pass


Firebase, Map
Map activity listens out for changes in player info (sign in/sign out and location updates) and reflects this in the map 
Pass


Firebase, Map
Player markers show accurate and up to date location of players as well as player names
Pass


Game, Map
NPCs spawn upon starting game
Pass


Map
Player pressing fight button before selecting enemy shows popup that prompts him to choose one first
Pass


Map, Kryonet
Player pressing fight button after enemy selection sends request to that enemy to duel
Pass


Game
Damage taken by pet corresponds to damage of skill used to attack pet
Pass


Game
Element of pet corresponds to the elements of the skills owned
Pass


Game
Element advantage/disadvantage are properly reflected when calculating the damage dealt to a pet
Pass


Game
After winning NPC, if player owns that pet, increase its exp
Pass


Game
After winning NPC, if player doesn’t own that pet, obtains that pet
Pass


Map, Game
Player pressing fight button after NPC selection starts battle with NPC
Pass


Map, Game
Player pressing “attributes” goes to attribute screen
Pass


Map, Game
Player pressing “change pet” goes to change pet screen


Pass


Map. Game
Broadcast receiver can receive “send battle req” from Map and relay info to game instance
Fail
Previously failed to register broadcast receiver. Fixed by doing so
Map. Game
Broadcast receiver can receive “quit map activity” from Map and relay info to game instance
Pass


Map, Game
Broadcast receiver can receive “sending NPC req” from Map and relay info to game instance
Pass


Map, Game
Broadcast receiver can receive “attribute activity” from Map and relay info to game instance
Pass


Firebase, Map, Game
Enemy can receive duel request popup
Pass


Map, Game


Enemy can reject duel request, taking him back to map screen
Pass


Map, Game, Kryonet


Enemy can accept duel request, starting battle between players
Pass


Game, Kryonet
Player can attack, change pet, and skip turn. All actions result in change of player turn.
Pass


Game
Once health reaches zero or below, that pet dies
Pass


Game, kryonet
Once all pets die, player loses and game ends
Pass


Game, Kryonet
Every action taken is reflected in both player’s screens accurately
Pass


Game
Upon win/loss condition met, game is ended for both players and each is shown the corresponding victory/loss screen.
Pass


Game, Map
Upon confirmation, players are taken back to the map screen.
Pass


Game
After battle, pet exp is increased unless max level, in which case no increase 
Pass


Game, Firebase
After pet exp changes or changing primary pet, changes are updated in firebase 
Pass


Game
Players can remove pets by double clicking in the pet change screen
Pass


Game
Players can swap pets between teams by clicking on 1 pet from each team consecutively in the pet change screen 
Pass


Game
Players can add pets to battle team by clicking on an empty slot and a pet from the reserve team in the pet change screen
Pass


Game
Players cannot have an empty battle team.





Fail
Fixed by not allowing removal of a pet in pet change screen if it is the last remaining pet
Game
Changes made to battle team in the pet change screen are saved and updated in Firebase
Pass


Game
A player currently in a battle cannot be added to another battle. 
Fail
Made the server check whether a player is currently in a battle. If so, cannot start a concurrent battle.
Game
In the attribute screen, pet and skill descriptions are correctly shown.
Pass





User testing

Additionally, we made good use of user testing, by asking people to test Darwin’s Duel and gathering valuable feedback from them.

Comment
Feedback gained
Solution


Some users reported that they could enter the game without being connected to the server, leaving them unable to battle or interact with others. This was especially problematic as players were unaware of the connection issue until they attempted to initiate a battle.
We modified the login sequence so that the login screen only appears once a connection to the server has been successfully established. Otherwise, players will remain at the splash screen until connection to the server has been established. 

This ensures that players know they are online and ready to access all game features, preventing any confusion and improving the overall user experience.


“Game crashes after …”
Testers encountered bugs that caused the game to crash

There were synchronization issues where certain events would trigger prematurely. For example, attempting to retrieve a player’s username before it was initialized caused instability.

Additionally, our real-time location tracking showed offline users as online, leading to further confusion.

Moreover, spamming of in-game buttons would sometimes result in logic errors, causing our server to crash, or allowing player to carry out unallowed actions. 
We implemented callbacks to handle synchronization, ensuring that game events occur in the correct order. 

The in-game logic was updated to handle real-world user behaviors, such as button mashing or attempting unauthorized actions. For instance, players who attempted to remove all pets from their battle team would now receive an error message instead. Buttons are now disabled on click, preventing button mashing.

This proactive approach to user testing helped us identify and fix issues with the real-time location tracking, significantly reducing crashes and improving game stability.


Players felt there was a lack of progression and purpose, with battles not contributing to a sense of growth or achievement.
We implemented a leveling system where pets gain experience points (EXP) from battles, allowing them to level up and increase their stats, such as health and skill damage. This system gives players a tangible sense of progress, motivating them to continue battling and improving their pets, ultimately enhancing their connection to their in-game team.
“Game feels a bit too simplistic, no strategy at play”

“There’s no counterplay to someone with stronger pets so it just boils down to grinding to get max level”
Some players initially noted that battling in Darwin’s Duel felt too simplistic and lacked strategic depth. 

The original mechanics mainly revolved around skill damage, turning the game into a “numbers game” where players simply chose the highest damage skills. 
To address this feedback and enrich the gameplay experience, we introduced two key features: the Elemental Counter System and Status Effects.

Elemental Counter System: 
This system adds a strategic layer by requiring players to consider the elemental types of their pets and their opponents. With elements such as Fire, Water, and Earth, players must strategically plan their attacks, using elemental advantages to gain the upper hand. Even weaker pets can defeat stronger ones by exploiting elemental weaknesses.

Status Effects:  
We introduced effects like Poison, Stun, and Absorb to allow for more complex strategies. For instance, Poison can chip away at an opponent’s health over time, while Stun prevents an opponent’s pet from taking actions, providing a tactical advantage. These mechanics ensure each battle requires thoughtful decision-making and adds depth to the gameplay.


Project Management
In managing our project for Darwin’s Duel, we utilized GitHub’s issues and branches to streamline development and ensure organized project management. GitHub issues served as a centralized hub for tracking tasks, bugs, and feature requests. By systematically categorizing and prioritizing issues, we maintained clarity on project milestones and development goals. 



Concurrently, GitHub branches enabled structured feature development and bug fixing. Each branch corresponded to a specific issue or feature, allowing for isolated development and testing without disrupting the main codebase. This approach facilitated collaborative workflows, as team members could work independently on assigned tasks while maintaining version control integrity. 
Overall, GitHub’s robust issue tracking and branch management capabilities played a pivotal role in enhancing project efficiency, fostering clear communication, and ensuring the timely delivery of features in Darwin’s Duel.
Flowchart Diagram









Class Diagram:
Link to clearer version :Orbital Class Diagram

  Server side classes			              		   Global classes

		
Client side classes



