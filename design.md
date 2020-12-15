# Design Specification

Technical Design
Which technologies did you use?
Which technical design choices did you make?
Any lessons learned? E.g., what would you do differently next time?


For our project we decided to use a mix of different technologies and APIs. For login and authentication services, we decided to use Firebase AUTH; the reason we decided on this is because of our prior knowledge with the API, its vast documentation and online help, and it's simplistic desing. We decided to not use any premade UI like w had done before, but rather implement our own UI system that would handle all functionality regarding loging in, registering new users, and authentication. Our system also allowed for users to play as guests, allowing them to bypass the loging in process, but restrinsting some features such as highscores and rankings.

![firebase auth logo](https://2.bp.blogspot.com/-2L3KX_48TEQ/XJ0AIfGqG9I/AAAAAAAADew/tiTNwPkS84Y2lPmM8z6IQadB_TRxETHLwCLcBGAs/s1600/Firebase%2BAuthentication%2B%25282-%2BHorizontal%2BLockup%252C%2BLight%2529.png) 

For database management and data storage we used Firebase Database for handling our data; as it tied pretty well to our existing use of Firebase for Authentication, making sure both systems worked flawlesly together. Our DB structure is composed of two main databases: the user DB; which holds information about the total score for each user, and the category DB; which holds a list of all categories containing another list of users and their highscore in that category.

![firebase database logo](https://firebase.google.com/images/brand-guidelines/lockup_realtime-database.png)

We also made use of the OpenTriviaDB API. We use this API to query for our questions based on category, difficulty, and other paramenters. At the start of the game activity, we make a request to the API endpoint, to generate a session token; this session token is then used for every consecuent API call to ensure that no question(s) are repeated for the duration of token's validity. When we want to retrieve a question from the API, we make a request to the endpoint specifying all previously state parementers, and we specify a callback function that will handle the response of the API response.
We decided to use an external API for our question set; not for the simplicity (as coding our own system that would read questions from a resource file would not have been that complicated) but rather because of the wide range of questions and question sets that were offered on the OpenTriviaDB API. This choice also influenced many factors about the design of our application, as for examples; categories are restricted to those offered by the API.

![open trivia db logo](https://opentdb.com/images/logo.png)


