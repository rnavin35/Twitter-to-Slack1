# Twitter-to-Slack1
Tweet from slack. And send tweets to Slack
               Using Twitter’s stream API and Twitter4j the program will sift through tweets 
               to find a specified hashtag. From which details of the tweet itself, which contains 
               the specified hashtag, and the author will placed in a string of which will  be output
               to a Slack channel, based on the entered channel token, using a Slack java API. 
               
               The second half of the project uses the java api for Slack so that a listener is 
               created  that serves the purpose of monitoring what is posted in the channel user and 
               who posted what. From there is will invoke the use of twitter's java api 
               and tweet out the messages.
               
               The project is completed and works as it is suppose to in in this version. Yet, I did run into one issue though if twitter                  account that slack is tweeting to sends a tweet independent of slack with the specified hashtag that the first part is                      looking for then it will enter a infinte loop of reprinting the tweet on the twitter page and slack channel.
               ****************************DISCLAIMER****************: Do not use #cowboys as the specified #. It will crash/freeze your                  computer.
