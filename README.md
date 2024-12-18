## Milestone 1 - GardenGuruApp (Unit 7)
## Table of Contents
- Overview
- Product Spec
- Wireframes
## Overview
### Description
Garden Guru is a helpful plant care app designed for gardeners and plant enthusiasts of all skill levels. Users can ask the AI questions about plant health and care, saving answers to create a personalized plant guide. The app sends reminders for tasks like watering and fertilizing, helping users stay on track even when offline. Whether you’re just starting out or have years of experience, Garden Guru makes it easy to keep your plants happy and healthy.

### App Evaluation

* **Category**: 
    * Productivity / Informative
* **Mobile**: 
    * Reminders for plant care tasks like watering, fertilizing, or pruning make it an indispensable mobile tool. Users can ask the garden guru questions about their plant health. Some stretch features such as using pictures to identify plants could be added as well. Saved guides and plant profiles can be accessed offline, offering on-the-go utility beyond a standard website.
* **Story**:
    * The app solves a common pain point for plant lovers: understanding their plants’ needs. Whether you're a beginner or an expert, personalized advice and reminders make plant care simple. Caring for plants can feel daunting, and the app positions itself as a helpful companion, giving users confidence and fostering success in their plant-care journey.
* **Market**:
    * The app appeals to plant parents, home gardeners, and even educators teaching plant care. People who want an easy way to track and care for their plants will find this app useful.
* **Habit**:
    * While reminders encourage passive use, users actively create content by logging plant details, asking the AI questions, and saving guides. Users will engage with the app daily or weekly for:
        * Checking or updating plant profiles.
        * Logging growth and health status.
Receiving and following care reminders.
* **Scope**:
    * Core features like AI prompts (via GPT API), local data storage, reminders, for plant profiles can be implemented within a month. Stratch features such as Camera-based plant identification or community sharing features can be added if there is additional time left.

## Product Spec
### 1. User Features (Required and Optional)
**Required Features**
* [x] Users can ask promps to the AI
* [x] Users can save records for their plant information.
* [x] Users can see their records even when app is offline.
* [x] Users can make and recieve push notifications.
* [ ] Users can click on their records for a detailed view.


**Optional Features**
* [ ] Users can store responses in a digital book.
* [ ] Users can take pictures of their plant to identify it with the AI.
* [ ] Users can share their plant information with others via a share button.
* [ ] Garden Guru has a customized animation for responses

### 2. Screen Archetypes
Messaging
* Users can ask promps to the AI

Creation
* Users can save records for their plant information.
* Users can make and recieve push notifications. 

Stream
* Users can see their records even when app is offline.
* Users can store responses in a digital book.

Detail
* Users can click on their records for a detailed view.

### 3. Navigation
Tab Navigation (Tab to Screen)
* Guru (Home / Chat Conversation)
* Your Plants
* Plant Reminders 
* Garden Book

Flow Navigation (Screen to Screen)
* Messaging:
    * Guru => Your Plants, when tab is pressed
    * Guru => Plant Reminders, when tab is pressed
    * Guru => Garden Book, when tab is pressed

* Creation: 
    * Your Plants Creation Screen => Your Plants Screen (after record is made)
    * Plant Reminders Creation Screen => Plant Reminders Screen (after reminder is made)

* Stream:
    * Your Plants => Plant Reminders, when tab is pressed
    * Your Plants => Detail Screen for plant, when that record is pressed
    * Plant Reminders => Your Plants, when tab is pressed
    
* Detail:
    * Your Plants Detail Screen => Plant Reminders, when tab is pressed
    * Your Plants Detail Screen => Your Plants, when tab is pressed or esc
    
    

## Wireframes
![GardenGuruWireFrame](https://github.com/user-attachments/assets/babe56c1-2120-4880-925a-b02fe926a541)

[BONUS] Digital Wireframes & Mockups
[BONUS] Interactive Prototype

### Milestone 2 - Build Sprint 1 (Unit 8)
GitHub Project board
<img width="1111" alt="image" src="https://github.com/user-attachments/assets/db72cc99-292a-4177-b31b-9fdc9b3a0e63">

Issue cards

[Project Board with the issues that you've been working on for this unit's milestone] 
<img width="1120" alt="image" src="https://github.com/user-attachments/assets/7a92a319-8a50-4852-9ff7-ec85f568a4e3">

[Project Board with the issues that you're working on in the NEXT sprint. It should include issues for next unit with assigned owners.]
<img width="1117" alt="image" src="https://github.com/user-attachments/assets/9dd27e1d-86b3-4f4d-9134-705fa5a89b34">

Issues worked on this sprint:

- Create UI Chatbot UI Screen


### Milestone 3 - Build Sprint 2 (Unit 9)
GitHub Project board
[Project Board with the updated status of issues for Milestone 3]
![Completed Issues - Milestone 3](https://github.com/user-attachments/assets/60df89e7-efab-4777-8c2f-e389ca258566)

Completed user stories:
* Users can ask promps to the AI
* Users can save records for their plant information.
* Users can see their records even when app is offline.
* Users can make and recieve push notifications. 

List any pending user stories / any user stories you decided to cut from the original requirements
* Users can store responses in a digital book.
* Users can click on their records for a detailed view.
* Users can take pictures of their plant to identify it with the AI.
* Users can share their plant information with others via a share button.
* Garden Guru has a customized animation for responses

Reasoning: 

- Due to a heavy time constraint, the optional extended features for the app were not integrated.
- The detail view was not integrated in the app for similar reasons. 

![ezgif2](https://github.com/user-attachments/assets/447749cb-d75b-474c-8489-f87b18cf27b0)


App Demo Video
https://youtu.be/1wECc9lq8LY
