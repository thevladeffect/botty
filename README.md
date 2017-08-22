# botty
Simple chat bot REST app.

# endpoint
URL:  
`/chat`  
Method:  
`GET`  
Parameters:  
`message` (optional)  
`uid` (optional)  

Example messages:
1. `What is the weather today in: {CITY}, {COUNTRY}` - will respond with the weather for the specified location
2. `Tell me joke #{NUMBER}` - Will reply with the requested joke. Knows 50 jokes. Doesn't tell you the same one more than once. You'll have to identify yourself (pass `uid` as well).
3. `Let me know if these are permutations of each other: {WORD_1}, {WORD_2}` - Will tell you if words are anagrams of one another

# run
`mvn clean build`  
`heroku local web`

Also available at: `https://botty-bot.herokuapp.com/chat`
