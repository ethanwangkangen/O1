# Darwin's Duel

**Orbital Milestone 3 README**

| | |
|---|---|
| **Team** | CodeClown Creations |
| **Members** | Wang Kang En Ethan, Goh Sze Kang |
| **Proposed Level of Achievement** | Apollo |

**Links**

- README (Google Doc): https://docs.google.com/document/d/1DwfnI6AzMFVhtee-DWFDQCVM1g0Ek8ns7yIK4_hRM4g/edit?usp=sharing
- Demo video: https://drive.google.com/file/d/1PLEtG6E4CKsfKUjriL72LuvprP2Dt_45/view?usp=sharing
- APK download: https://drive.google.com/file/d/1IHzeqC28vdASCS83Ajp9lerSAbFvFcW-/view?usp=sharing

![Darwin's Duel](https://github.com/user-attachments/assets/48766cf5-2a9d-4645-9815-e493db12b343)

---

## Table of Contents

- [Instructions to Run](#instructions-to-run)
- [Aim](#aim)
- [Motivation](#motivation)
- [User Stories](#user-stories)
- [Project Scope](#project-scope)
- [Tech Stack](#tech-stack)
- [Features](#features)
  - [Firebase Login and Sign Up](#firebase-login-and-sign-up)
  - [GPS Tracking System](#gps-tracking-system)
  - [Online Multiplayer Battles](#online-multiplayer-battles)
  - [NPC Battling System](#npc-battling-system)
  - [Elemental Counter System](#elemental-counter-system)
  - [Pet Levelling System](#pet-levelling-system)
  - [Welcome Screen](#welcome-screen)
  - [Pet Change Screen](#pet-change-screen)
  - [Attribute Screen](#attribute-screen)
  - [Unique Pets and Skills](#unique-pets-and-skills)
  - [Animations](#animations)
- [Code Design](#code-design)
  - [Module Structure and Interfaces](#module-structure-and-interfaces)
  - [Receivers and Intents](#receivers-and-intents)
  - [Server-Client Connection and Battling Logic](#server-client-connection-and-battling-logic)
- [Software Engineering Principles and Design Patterns](#software-engineering-principles-and-design-patterns)
- [Testing](#testing)
  - [Unit Testing](#unit-testing)
  - [Integration Testing](#integration-testing)
  - [User Testing](#user-testing)
- [Project Management](#project-management)
- [Diagrams](#diagrams)

---

## Instructions to Run

> **Must read**
>
> Darwin's Duel is an online multiplayer game and requires our local server to be running for the game to work. While the server is offline, the game is unplayable.
>
> **Server status: offline**
>
> Thank you for your understanding and patience.

1. Download the APK from the link above.
2. Install it on an Android device or an Android emulator (Android Studio).
3. Launch the game. The login screen will only appear once a connection to the server has been established.

---

## Aim

Darwin's Duel is a multiplayer pet battling mobile game in a 2D pixel art style. With an elemental counter system and unique skills that let players tailor the growth of their pets to suit their battle strategies, we hope to create a game that pushes the strategic skills of players to their limits.

In Darwin's Duel, every monster you encounter and capture can be trained and evolved through a branching evolutionary system, allowing you to tailor their growth to suit your battle strategies. Explore diverse environments, each teeming with unique creatures and challenges, making every gameplay session a new adventure.

The heart of Darwin's Duel lies in its competitive battles. Players can engage in real-time duels with others. Victories yield rewards and prestige, helping you climb the global leaderboards and cement your status as the ultimate Duelist.

---

## Motivation

As avid gamers ourselves, we are weary of the stereotype that games are "useless" or merely a "waste of time." In response, we aspire to develop a game that fosters both social interaction and friendly competition.

Taking inspiration from popular AR mobile games like Pokémon Go and its competitors (e.g. Palworld), we aim to integrate real-time, location-based systems into our game, akin to those used in Pokémon Go and Ingress. This approach promises deeper immersion for players, allowing them to engage with the game world in their real-life surroundings.

By offering diverse environments brimming with unique creatures and challenges, our goal is to craft a captivating pet battling mobile game that motivates players to venture beyond their homes. Through this, we hope to encourage players to explore, encounter, and capture new and distinctive creatures, transforming their gaming experiences into meaningful adventures.

---

## User Stories

| Priority | As a... | I want... |
|---|---|---|
| Must have | Pokémon fan | to play a game that incorporates similar elements of virtual pet fighting, against both non-playable (pre-programmed) entities and other players |
| Must have | User | to meet new people and make new friends by entering combat with other players using a GPS location tracking system |
| Must have | Gamer | complex battle systems that require careful planning and adaptability, allowing me to refine my strategies and overcome challenging opponents |
| Must have | Gamer | a strategy game that will test my thinking so that I can challenge myself |
| Must have | Gamer | a game with a strong sense of progression and achievement to keep me engaged and motivated |
| Must have | Casual gamer | a short and simple game that I can pick up at any time, so that I can pass the time when I am bored |
| Must have | Casual gamer | intuitive controls and straightforward gameplay mechanics, allowing me to enjoy the game without a steep learning curve |
| Should have | Gamer | a rewarding levelling system that reflects my efforts and progress, granting new abilities, pets, and challenges as I advance |
| Should have | Casual gamer | quick battles and activities that fit into my busy schedule, enabling me to enjoy the game even during short breaks or commutes |
| Should have | Collector | a wide variety of pets or creatures to capture, train, and customise, allowing me to build a unique and personalised team |
| Good to have | Competitive gamer | robust multiplayer features with rankings and leaderboards, so I can gauge my skills against other players |
| Good to have | Strategist | deep gameplay mechanics such as skill combinations, buffs, and debuffs, allowing for intricate battle strategies and tactical decisions |
| Good to have | Strategist | challenging AI opponents and scenarios that test my strategic thinking, offering opportunities to apply advanced tactics and strategies |

---

## Project Scope

Darwin's Duel is an RPG mobile game, similar to Pokémon Go, where players traverse the in-game world using location-based systems that track their real-life whereabouts. Throughout their journey, players encounter and capture a diverse array of unique creatures while exploring varied environments. These captured creatures can be trained and evolved through battles with other players, acquiring new skills and items to enhance their battle prowess.

With an array of strategic elements to consider, including an elemental counter system, mana/AP management, and unique status conditions, Darwin's Duel goes beyond simplistic gameplay where a single powerful pet reigns supreme.

By participating in real-time duels with fellow players, individuals have the opportunity to ascend the leaderboards and earn both prestige and exclusive rewards, further heightening the competitive aspect of the game.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| **LibGDX & Java** | Game engine |
| **Firebase** (Authentication and Realtime Database) | User login, retrieval and storage of player data, location updates, and online status |
| **Kryonet** | Client-server connection for PVP and PVE battles. Server hosted on our local server |
| **Google Maps API & GPS location tracking** | Real-time location display of self and enemies on the map |
| **Android emulator** (Android Studio) | Required to run the program |

---

## Features

### Overview

| Feature | Description |
|---|---|
| **Firebase** | Stores user information and lets players save their progress securely |
| **GPS Tracking System** | Enables real-world battles based on geographical location. Players can locate opponents and NPCs on the map, encouraging exploration and physical world interaction |
| **Online Multiplayer Battling System** | Facilitates real-time player-versus-player combat |
| **NPC Battling** | Lets players battle NPCs to earn EXP and unlock new pets |
| **Elemental Counter System** | Categorises pets into FIRE, WATER, and EARTH. FIRE > EARTH > WATER > FIRE |
| **Pet Levelling System** | Pets gain EXP through battles and level up, enhancing stats like HP and skill damage |
| **Welcome Screen** | Features a message from the developers to create excitement |
| **Pet Change Screen** | Customise a battle team of three pets by swapping pet positions and selecting battle order |
| **Attribute Screen** | Displays detailed pet information: name, HP, level, element, skills, and status effects |
| **Animations** | Dynamic attack animations and idle animations for pets |

---

### Firebase Login and Sign Up

<!-- Screenshot: login / sign up screen -->

To enhance the user experience and ensure that progress is saved securely, we integrated Firebase's authentication and database APIs for user login and sign up. Players create accounts using their email and password, allowing them to save their progress and continue their journey anytime, anywhere.

New players start their adventure with three unique starter pets: **MeowmadAli**, **MouseHunter**, and **Froggy**. As they progress, they can gradually unlock more pets, each with its own unique abilities and characteristics.

With these features in place, players can enjoy a more immersive and connected gameplay experience, knowing that their hard-earned progress is always saved and accessible.

---

### GPS Tracking System

<!-- Screenshot: map view with player and NPC markers -->

One of the standout features of Darwin's Duel is its GPS tracking system. When players are in proximity, they can spot each other on the map and send battle requests. If the opponent accepts, the battle commences, blending the digital and physical worlds. This feature is not only a technical challenge but also a game-changer, encouraging players to venture outside and interact with the world around them.

The map also displays NPCs (grey markers) that players can choose to fight to gain new pets or level up existing ones.

**How targeting works:**

1. Click on an enemy (an NPC or another player) to select them as the target.
2. Press **Fight**.
3. If **Fight** is pressed before a valid enemy is selected, the game prompts the player to choose one first.

**Battle requests**

When a player successfully targets another online player and requests to fight them, the targeted player receives a popup. Their response determines whether the battle starts.

<!-- Screenshot: battle request popup -->

---

### Online Multiplayer Battles

We implemented multiplayer combat using Kryonet by setting up a Java-based server to manage connections and facilitate real-time interaction between players. Each player connects via a client application that communicates with the server using Kryonet's API. Players send commands to the server, which processes them to determine outcomes such as damage calculation and status effects. This ensures fair gameplay mechanics and synchronised game states across all connected clients.

Our multiplayer combat system integrates turn-based mechanics within a real-time framework, allowing players to alternate actions while receiving immediate feedback from the server. This leverages Kryonet's efficient TCP protocols for low-latency communication, essential for a smooth and responsive gameplay experience.

#### Attacking the opponent

<!-- Screenshots: "Your Turn" and "Opponent's Turn" -->

During their turn, players attack using one of their pet's skills. Each skill comes with an associated status effect, represented by a status effect icon. These icons indicate whether the skill inflicts **Poison**, **Stun**, or **Absorb**, allowing players to plan their strategy effectively.

#### Pet change during battle

<!-- Screenshot: pet change function in battle -->

Gone are the days of waiting for pets to faint before switching. Players can change their main pet during battles, unlocking a range of strategic possibilities. This lets players adapt their tactics on the fly, introducing new ways to counter opponents' moves and turn the tide of battle in their favour.

#### Skip turn

With the introduction of the **Stun** status effect, pets may be unable to attack during their turn. To handle this, we added a **Skip Turn** button. This allows players to strategically skip their turn while waiting for the stun effect to wear off, providing an opportunity to regroup and plan their next move.

---

### NPC Battling System

<!-- Screenshots: NPC battle, won battle, lost battle -->

Players can engage in battles with NPCs using similar logic to online multiplayer battles. These NPCs simulate real combat scenarios, providing a realistic and challenging experience.

**Benefits of NPC battles:**

- **Gain EXP:** NPC battles are an excellent way to earn experience points, allowing players to level up their pets and strengthen their skills at their own pace.
- **Unlock new pets:** Successfully defeating specific NPCs grants players the chance to add the defeated pet to their collection.
- **Stress-free battling:** Without the pressure of competing against other players, players can test out new strategies against NPCs in preparation for PVP combat.

---

### Elemental Counter System

Each pet is categorised into one of three types: **FIRE**, **WATER**, or **EARTH**.

```
FIRE  >  EARTH  >  WATER  >  FIRE
```

| Matchup | Damage multiplier |
|---|---|
| Elemental advantage | **1.35x** |
| Elemental disadvantage | **0.65x** |

This mechanic encourages players to carefully consider their team compositions, fostering deeper tactical planning and enriching each battle with strategic decision-making.

---

### Pet Levelling System

Pets level up through combat, gaining strength and new abilities. By participating in battles against other players or NPCs, pets earn EXP, with victories awarding more experience points. Once a pet accumulates enough EXP, it levels up, enhancing its stats including HP, MP, and skill damage.

To maintain balance, pets are capped at **level 30**. This ensures a fair playing field and encourages players to strategise and make the most of their pets' abilities at each level.

---

### Welcome Screen

<!-- Screenshot: welcome screen -->

When new players create a Darwin's Duel account, they are greeted by a personalised welcome screen featuring a special message from the developers, designed to make players feel appreciated and excited about their upcoming adventure.

---

### Pet Change Screen

Players select the three pets they wish to bring into battle from the Pet Change Screen. This lets players add or remove pets from their battle team and choose the order in which they enter the fight.

**Sub-features:**

- Clicking different battle pets consecutively swaps their positions in the battle team.
- Clicking an empty slot followed by a reserve pet (or vice versa) adds the pet to the battle team.
- Clicking a battle pet followed by a reserve pet (or vice versa) swaps their positions, moving the reserve pet into the battle team.
- Double-clicking the same battle pet consecutively removes it from the battle team.
- An error message is displayed when a player tries to remove the last pet from the battle team.

<!-- Screenshots: original team, changing order, changing pets, removing pets, error message -->

---

### Attribute Screen

<!-- Screenshot: pet attribute screen -->

The Attribute Screen provides a comprehensive overview of the pets a player owns, helping them strategise and optimise their gameplay for upcoming battles.

| Field | Description |
|---|---|
| **Name** | Each pet has a unique name and its own set of unique skills |
| **Health Points (HP)** | The pet's maximum health and its capacity to withstand damage |
| **Level** | Reflects the pet's experience and power. Higher-level pets are stronger and more durable |
| **Element** | The pet's elemental affinity (Fire, Water, or Earth), which influences both offensive and defensive capabilities |
| **Skills** | A detailed list of the pet's skills, including skill names, status effects (Poison, Stun, Absorb), and descriptions covering effects and potential damage output |

---

### Unique Pets and Skills

All six pets in Darwin's Duel were drawn and designed by our team member and artist, Ethan. Each pet has unique skills and abilities, unlocking new strategies and playstyles.

| Starter Pets | Advanced Pets |
|---|---|
| MeowmadAli | Dragon |
| MouseHunter | Doge |
| Froggy | Croc Lesnar |

<!-- Pet sprite images -->

**Starter pets** are given to all new players and offer a balanced introduction to the game's mechanics. They are essential for early-game strategy and let players learn the basics of elemental matchups and skill use.

**Advanced pets** are unlocked by defeating NPCs. They are stronger than starter pets at the same level and have higher damage output. However, with strategic use of elemental advantages or higher levels, starter pets can still defeat advanced pets. This balance encourages players to think strategically and use their entire team effectively.

#### Damage output of skills

Each skill falls into one of four damage categories, ranging from least to most powerful.

<!-- Image: skill damage categories -->

#### Status effects of skills

| Status effect | Description |
|---|---|
| **POISON** | Poisons the opponent's pet for 3 turns. Poisoned pets take damage equal to 30% of the skill's damage every turn |
| **STUN** | 50% chance to stun the opponent's pet for 3 turns. Stunned pets cannot attack, but players can still change pets or skip their turn |
| **ABSORB** | The attacking pet recovers 30% of the damage dealt to the opponent, providing a source of healing during combat |

---

### Animations

<!-- Images: idle animation, attack animations -->

We incorporated dynamic animations to enhance visual appeal and the overall player experience. When pets execute attacks during battles, players see engaging attack animations that bring each encounter to life. Idle animations for pets provide a more lively and immersive environment throughout the game.

---

## Code Design

A large aspect of our program is the interaction between the Firebase API, Google Maps API, and the game files. Below is a high-level overview of how the program works, including the extensive use of interfaces to connect the APIs (located in the **Android** module) to the game (located in the **Core** module).

### Module Structure and Interfaces

<!-- Image: Services UML -->

Multiple interfaces provide abstraction layers between the two modules:

- **Android module:** all methods relating to Firebase and Google Maps API dependencies.
- **Core module:** all game classes.

It was important to prevent cyclic dependency, ensuring that the Android module depends on the Core module and never the other way round. In the Services UML, only `DarwinsDuel` (the main game class) and `MyClient` are located within the Core module.

All game-side logic is written inside the Core module. When it is necessary to access methods that require the Map or Firebase database/authentication, the methods declared by the interfaces are called. For example, when the game needs to display the map, from within the Core module it accesses the static instance of `AndroidLauncher` and calls `showMap()`. This works because the interface is located in the Core module: `AndroidLauncher` implements this interface and its methods inside the Android module, adhering to the one-way dependency between modules. Calling the method sends an Intent to start the Map activity, which is caught by a broadcast receiver within the Android module.

### Receivers and Intents

Aside from interfaces, we used a combination of Broadcast Receivers and Intents to communicate between modules.

This allowed us to communicate with the Map activity (which uses the Google Maps and Firebase APIs to display users in real time) from the LibGDX game instance. This cannot be done directly since they are different Android activities.

Examples include starting the Map activity from the game, and sending a request to battle another player or an NPC from the map.

### Server-Client Connection and Battling Logic

To facilitate real-time battles between users, we used **Kryonet**, a Java library that provides an API for efficient TCP and UDP client/server network communication using NIO.

**Client side**

In adherence with the Single Responsibility Principle, the `MyClient` class holds an instance of the `kryonet.Client` object and is responsible for interacting with the server.

When the program wants to send a request to the server, it calls a static method within `MyClient` to send the request in the form of an **Event**. This is handled in a separate thread from the main thread.

Different Events correspond to different actions taken by the server. Each Event is a serialised instance of a particular object, for example `PlayerJoinServerEvent`. The server has a Listener which identifies the type of object received and handles the response accordingly. The same logic applies to the client, which holds a listener for objects received from the server.

**Server side**

- The server contains a table mapping each user's unique ID (retrieved from Firebase) to their `Connection`, an object that allows communication between the server and each player.
- `ServerPlayerHandler` keeps track of each player and the `Connection` object used to identify them.
- `ServerBattleHandler` keeps track of all ongoing battles between pairs of players. Each battle is encapsulated in a `BattleState`, and the server holds a table of `BattleState`s mapped to one of the players' unique user IDs.

The `NPC` class extends `Player`. To adhere to the Liskov Substitution Principle, `BattleState` works the same way with two players as it does with one player and an NPC, except for NPC attacking, which has to be automated. This is done by checking a boolean `againstNPC`, which, if true, calls `NPCAttack()` during the battle. This is handled by `ServerBattleHandler`.

---

## Software Engineering Principles and Design Patterns

### Encapsulation and the Open-Closed Principle

<!-- Image: class diagram overview (entities) -->

Adding more Creatures does not require modification of the `Creature` class or other external classes/methods that use it. It simply requires extending the abstract `Creature` class and initialising the fields with the Creature's stats and unique skills.

We chose to make `NPC` extend `Player` due to the similarities in functions and encapsulated information. Other classes that use `Player`, such as `BattleState` and `BattleHandler` that call `getBattlePets()`, continue to work with `NPC`.

### Singleton Design Pattern

This pattern is used throughout the codebase. One example is the single static instance of the server used throughout the program; the same applies to the client instance.

The constructor for `ServerFoundation` is private, preventing instantiation from outside the class and ensuring that only one instance is created. The `private static ServerFoundation instance` variable holds the single instance and is initialised only once when `main` is called.

Using the Singleton pattern here ensures there is only one instance of `ServerFoundation` managing the server. This is crucial for maintaining a consistent state across the application and avoiding issues such as conflicts in binding ports or managing connections and events. It also makes shared resources easier to manage and provides a single point of control for the server's lifecycle.

### Single Responsibility Principle (SRP)

A class should have only one reason to change, meaning it should have only one job or responsibility.

The primary responsibility of `BattleState` is to manage the state and flow of a battle: tracking whose turn it is, handling attacks, changing pets, scheduling NPC attacks, and checking the status of players and pets. It does not manage the internal details of how players or pets function beyond what is necessary for battle mechanics.

### Separation of Concerns

The `MapActivity` class is structured so that each distinct aspect of its functionality is handled separately:

**Google Maps initialisation and handling**

- `onMapReady` sets up the map, moves the camera to the user's location, and initialises marker click listeners.
- `displayAll` manages the display of all online players, adding and updating markers as needed.
- `displayNPCs` handles the display of NPCs on the map.
- `onMarkerClick` handles marker clicks, distinguishing between player markers and NPC markers.

**Firebase integration**

- Firebase references (`database`, `databaseUsers`, `auth`, `userStatusDatabaseReference`) are initialised separately.
- `setUserOnlineStatus` listens for connectivity changes and updates the user's online status in Firebase.
- `sendLocationToFirebase` updates the user's location in Firebase at regular intervals.

**Location services**

- `startLocationUpdates` initialises the `FusedLocationProviderClient` and requests location updates, setting a callback to handle location results.
- `sendLocationToFirebase` sends the location data to Firebase and updates local variables.

**Broadcast Receivers and Intents**

- The broadcast receiver for battle and map activity intents is registered in `onCreate` and unregistered in `onDestroy`.
- `sendBattleReqToEnemy`, `sendBattleReqToNPC`, `sendQuitToLibGDX`, and `sendAttributeToLibGDX` handle specific intents and broadcast actions, keeping intent-related logic modular and separate from the rest of the code.

---

## Testing

### Unit Testing

We implemented unit testing using JUnit.

- **LoginTest** checked that the basic Firebase login and sign up logic worked before integrating it into the program. This used a mock authentication service that simulates the sign up / sign in process and returns the callback, after which the subsequent logic (in particular the retrieval of Player data) is executed in the actual code.
- A small unit test checked the basic functionality of the main game class, ensuring that the game starts by displaying the splash screen and loads textures properly on startup.
- A test checked that the game instance (`gameCommunication`) could be accessed from within the Android module, verifying that dependencies between the Android and Core modules were set up correctly. The methods tested (`onQuitMapActivity()`, `onNPCReqReceived()`, etc.) are used within the Map activity to communicate with the game instance, which then executes actions such as sending battle requests to enemy players.

<!-- Images: unit test code screenshots -->

### Integration Testing

Given the many components in the program, integration testing was important to make sure everything worked together. Below are the test cases used.

*Map = Google Maps API integration*

| Units / Modules | Test case | Initial result | Improvements / changes made |
|---|---|---|---|
| Game, Firebase | Upon sign up, new player created and data accurately uploaded to database | Pass | |
| Game, Firebase | Upon login, data accurately pulled from database and deserialised. All pets, skills, and associated data accurate | Fail | Problem with deserialisation. Fixed by changing the return type of a function |
| Firebase, Map | Player online status accurately reflected in database | Pass | |
| Android device, Map, Firebase | Player location accurately sent to and updated in Firebase | Pass | |
| Firebase, Map | Map activity listens for changes in player info (sign in/out and location updates) and reflects this on the map | Pass | |
| Firebase, Map | Player markers show accurate and up-to-date player locations and names | Pass | |
| Game, Map | NPCs spawn upon starting the game | Pass | |
| Map | Pressing Fight before selecting an enemy shows a popup prompting the player to choose one first | Pass | |
| Map, Kryonet | Pressing Fight after enemy selection sends a duel request to that enemy | Pass | |
| Game | Damage taken by a pet corresponds to the damage of the skill used | Pass | |
| Game | Element of a pet corresponds to the elements of the skills it owns | Pass | |
| Game | Element advantage/disadvantage is properly reflected when calculating damage | Pass | |
| Game | After defeating an NPC, if the player owns that pet, its EXP increases | Pass | |
| Game | After defeating an NPC, if the player does not own that pet, they obtain it | Pass | |
| Map, Game | Pressing Fight after NPC selection starts a battle with the NPC | Pass | |
| Map, Game | Pressing Attributes goes to the attribute screen | Pass | |
| Map, Game | Pressing Change Pet goes to the pet change screen | Pass | |
| Map, Game | Broadcast receiver can receive "send battle req" from Map and relay it to the game instance | Fail | Previously failed to register the broadcast receiver. Fixed by registering it |
| Map, Game | Broadcast receiver can receive "quit map activity" from Map and relay it to the game instance | Pass | |
| Map, Game | Broadcast receiver can receive "sending NPC req" from Map and relay it to the game instance | Pass | |
| Map, Game | Broadcast receiver can receive "attribute activity" from Map and relay it to the game instance | Pass | |
| Firebase, Map, Game | Enemy receives a duel request popup | Pass | |
| Map, Game | Enemy can reject a duel request, returning them to the map screen | Pass | |
| Map, Game, Kryonet | Enemy can accept a duel request, starting a battle between players | Pass | |
| Game, Kryonet | Player can attack, change pet, and skip turn. All actions result in a change of player turn | Pass | |
| Game | Once health reaches zero or below, that pet dies | Pass | |
| Game, Kryonet | Once all pets die, the player loses and the game ends | Pass | |
| Game, Kryonet | Every action taken is reflected accurately on both players' screens | Pass | |
| Game | Upon win/loss, the game ends for both players and each is shown the corresponding victory/loss screen | Pass | |
| Game, Map | Upon confirmation, players are taken back to the map screen | Pass | |
| Game | After battle, pet EXP increases unless at max level, in which case there is no increase | Pass | |
| Game, Firebase | After pet EXP changes or the primary pet changes, changes are updated in Firebase | Pass | |
| Game | Players can remove pets by double-clicking in the pet change screen | Pass | |
| Game | Players can swap pets between teams by clicking one pet from each team consecutively in the pet change screen | Pass | |
| Game | Players can add pets to the battle team by clicking an empty slot and a pet from the reserve team | Pass | |
| Game | Players cannot have an empty battle team | Fail | Fixed by disallowing removal of a pet in the pet change screen if it is the last remaining pet |
| Game | Changes made to the battle team in the pet change screen are saved and updated in Firebase | Pass | |
| Game | A player currently in a battle cannot be added to another battle | Fail | Made the server check whether a player is currently in a battle. If so, a concurrent battle cannot start |
| Game | In the attribute screen, pet and skill descriptions are shown correctly | Pass | |

### User Testing

We asked people to test Darwin's Duel and gathered feedback from them.

| Comment | Feedback gained | Solution |
|---|---|---|
| Players entering the game while disconnected | Some users could enter the game without being connected to the server, leaving them unable to battle or interact with others. Players were unaware of the connection issue until they attempted to initiate a battle | The login screen now only appears once a connection to the server has been established. Otherwise, players remain at the splash screen. This ensures players know they are online and ready to access all game features |
| "Game crashes after..." | Testers encountered crashes. There were synchronisation issues where certain events triggered prematurely, e.g. retrieving a player's username before it was initialised. Real-time location tracking sometimes showed offline users as online. Spamming in-game buttons sometimes caused logic errors, crashing the server or allowing disallowed actions | Implemented callbacks to handle synchronisation, ensuring events occur in the correct order. Updated in-game logic to handle real-world behaviours such as button mashing and unauthorised actions (e.g. attempting to remove all pets from the battle team now shows an error message). Buttons are disabled on click to prevent mashing. Fixed issues with real-time location tracking, significantly reducing crashes |
| Lack of progression | Players felt there was a lack of progression and purpose, with battles not contributing to a sense of growth or achievement | Implemented a levelling system where pets gain EXP from battles, level up, and increase stats such as health and skill damage. This gives players a tangible sense of progress and motivates continued play |
| "Game feels a bit too simplistic, no strategy at play" / "There's no counterplay to someone with stronger pets so it just boils down to grinding to get max level" | Battling felt too simplistic and lacked strategic depth. The original mechanics revolved mainly around skill damage, turning the game into a numbers game where players simply chose the highest damage skills | Introduced two key features. **Elemental Counter System:** players must consider the elemental types of their pets and their opponents' pets; even weaker pets can defeat stronger ones by exploiting elemental weaknesses. **Status Effects:** Poison, Stun, and Absorb allow for more complex strategies, ensuring each battle requires thoughtful decision-making |

---

## Project Management

We used GitHub Issues and branches to streamline development and keep the project organised.

**GitHub Issues** served as a centralised hub for tracking tasks, bugs, and feature requests. By systematically categorising and prioritising issues, we maintained clarity on project milestones and development goals.

**GitHub branches** enabled structured feature development and bug fixing. Each branch corresponded to a specific issue or feature, allowing for isolated development and testing without disrupting the main codebase. This facilitated collaborative workflows, as team members could work independently on assigned tasks while maintaining version control integrity.

<!-- Images: GitHub issues and branches screenshots -->

---

## Diagrams

### Flowchart Diagram

<!-- Image: flowchart diagram -->

### Class Diagram

Link to clearer version: Orbital Class Diagram

<!-- Images: server side classes, global classes, client side classes -->
