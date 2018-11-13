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
               
The project is completed and works as it is suppose to in in this version. Yet, I did run into one issue though if twitter                 
account that slack is tweeting to sends a tweet independent of slack with the specified hashtag that the first part is                     
looking for then it will enter a infinte loop of reprinting the tweet on the twitter page and slack channel.
****************************DISCLAIMER****************: Be careful with input words word with high frequency use are prone to overflow erros. Be mindful and use obscure words
How to build/Install Project(jar files located above as well):
1)Add Twitter4J twitter java api to your build path. Given above or dowload from Website(http://twitter4j.org/en/index.html)
2)Download the SLACK java api jar file to your build path. Given above or Dowload from Website(https://github.com/Ullink/simple-slack-api/tree/master/gradle/wrapper)
3)Add the following repositories and dependencies into pom file.
i)<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  ii)<dependency>
	    <groupId>com.github.Ullink</groupId>
	    <artifactId>simple-slack-api</artifactId>
	    <version>0.6.0</version>
	</dependency>
  
