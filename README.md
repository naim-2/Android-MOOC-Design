# My-Project
The application allows users to get details about countries in the world as well as getting cat photos which are randomly generated from external APIs.

### 1. How it works: 
The user will first see the home page. 
#
Then when they select “See Countries”, they will be taken to the next layout. This next layout uses a content provider and runs background tasks.
#
He can then type in the country name.
#
He will then get information about the country after clicking the “Get Details” button.
#
He can go back by to the home page by clicking the home page icon at the top left.
#
He can then click on “See Cats” to see a random cat image. This layout uses a content provider, runs background tasks and also handles asynchronous events using a broadcast receiver.


### 2. The URLs I'll be connecting to are: 
i. For country search: https://restcountries.com/v3.1/name/ 
Example: https://restcountries.com/v3.1/name/united%20states%20of%20america 
...will return a few details about United States of America.
ii. For cat image generator: https://api.thecatapi.com/v1/images/search 
This should get a random JSON which then extracts the URL to display the image using technologies like Picasso.


### 3. I have done unit and integration testing on CountrySearch file; and interface testing on homePage file.
 
### 4. YouTube URL demonstrating how the Android app works: 
https://www.youtube.com/watch?v=s59kIcauJoU