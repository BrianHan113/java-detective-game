# SOFTENG 206 - Software Engineering Design 1
Detective game to train aspiring private investigators using JavaFX and AI technology. Built for our clients, PI Masters, working in a group of 3 using GitHub Flow.

## Gameplay
![Video](/assets/game.mp4)

## Features/Project Requirements
- Realistic and immersive storyline
- Interactive items
- Suspect interrogations and unique personalities
- Concurrency to avoid GUI freezing
- Use of text-to-speech
- Immediate feedback
- Replayability

## To setup OpenAI API 

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside the credentials:

  ```
  email: "UPI@aucklanduni.ac.nz"
  apiKey: "YOUR_KEY"
  ```

## To run the game

`./mvnw clean javafx:run`