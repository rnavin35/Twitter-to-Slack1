/*
 * Gets Tweets from Twitter to Slack and vise versa
 * 
 * Version:    1.0
 * 
 * Author    : Navin Raju ( rnavin787@yahoo.com )
 *
 * Description: Using Twitter’s stream API and Twitter4j the program will sift through tweets 
 *              to find a specified hashtag. From which details of the tweet itself, which contains 
 *              the specified hashtag, and the author will placed in a string of which will  be output
 *              to a Slack channel, based on the entered channel token, using a Slack java API. 
 *              
 *              The second half of the project uses the java api for Slack so that a listener is 
 *              created  that serves the purpose of monitoring what is posted in the channel user and 
 *              who posted what. From there is will invoke the use of twitter's java api 
 *              and tweet out the messages as they are printed in real time.
 * 
 * Inputs:		Twitter Stream and Specified Slack Channel
 * 

 *  		
 * Last Modified Date: 12/19/2016 4:00:00 PM CST
 */
	package twitterToSlack;

	
	
	
	import com.ullink.slack.simpleslackapi.*;
	import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
	import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
	import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

	import twitter4j.Status;
	import twitter4j.FilterQuery;
	import twitter4j.StallWarning; 
	import twitter4j.StatusDeletionNotice; 
	import twitter4j.StatusListener;
	import twitter4j.StatusUpdate;	
	import twitter4j.Twitter;
	import twitter4j.TwitterException;
	import twitter4j.TwitterFactory;
	import twitter4j.TwitterStream;
	import twitter4j.TwitterStreamFactory;
	import twitter4j.User;
	import twitter4j.auth.AccessToken;
	import twitter4j.conf.ConfigurationBuilder;

	import java.io.IOException;

	public class twitterToSlack {

		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			
			//Enter specified Slack Token
			SlackSession session = SlackSessionFactory.createWebSocketSlackSession("");
	        session.connect();
			
	        //Enter specified key and tokens from twitter
	        ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setDebugEnabled(true); 
	        cb.setOAuthConsumerKey("");
	        cb.setOAuthConsumerSecret(""); 
	        cb.setOAuthAccessToken("");
	        cb.setOAuthAccessTokenSecret("");
	        
	        //initiates Twitter Stream
	        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	        
	        //Define twitter listener - Actively runs through twitter to find specified hashtag 
	        StatusListener listener = new StatusListener() {

	  
	            @Override
	            public void onException(Exception exception) {
	                
	            	// TODO Auto-generated method stub

	            }

	            @Override
	            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	                
	            	// TODO Auto-generated method stub

	            }

	            @Override
	            public void onScrubGeo(long onScrubGeo1, long onScrubGeo2) {
	                
	            	// TODO Auto-generated method stub

	            }

	            @Override
	            public void onStatus(Status status) {
	                
	            	User user = status.getUser();
	                
	                // gets Username
	                String username = status.getUser().getScreenName();
	                System.out.println(username);
	                
	                //get the location of where it was tweeted
	                String profileLocation = user.getLocation();
	                System.out.println(profileLocation);
	                
	                //get tweet id
	                long tweetId = status.getId(); 
	                System.out.println(tweetId);
	                
	                //get the actual tweet
	                String content = status.getText();
	                System.out.println(content +"\n");
	                
	                //String of the content of the tweet so that is somewhat formatted when diplayed 
	                String express = "-@"+username + "\n" + " Just Tweeted: " + content;
	                
	                //Gets the channel to be posted in
	                SlackChannel channel = session.findChannelByName("projectrun");
	                
	                //Writes the tweet out in the channel
	                session.sendMessage(channel, express);
	                
	            } 
	             
	            public void onStallWarning(StallWarning warning) {
	                
	
	              
	            }
	            
	            @Override
	            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	                // TODO Auto-generated method stub

	            }

	        };
	        //Initiate filter
	        FilterQuery fq = new FilterQuery();
	        
	        //Specified key word
	        String keywords[] = {"#madagascar"};
	        
	        //specifies to track said keyword
	        fq.track(keywords);

	        twitterStream.addListener(listener);
	        
	        //Method Internally creates a thread to search Twitterstream and calls the listener methods continuously
	        twitterStream.filter(fq);
	       
	        
	        //*****PART TWO Tweeting directly from slack
	        //The cb/ConfigurationBuilder can only be created in one instance so initializing the tokens as
	        //strings serves as the alternative for invoking the twitter account for the action
	        //of tweeting out from the Slack channel 
	        String consumerKey= "";
	        String consumerSecret = ""; 
	        String accessToken ="";
	        String accessTokenSecret = "";
	        
	        
	        TwitterFactory twitterFactory = new TwitterFactory();

	        //Instantiate a new Twitter instance
	        Twitter twitter = twitterFactory.getInstance();

	        //setup OAuth Consumer Credentials
	        twitter.setOAuthConsumer(consumerKey, consumerSecret);

	        //setup OAuth Access Token
	        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
	        
	        //Define slack listener
	        SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener()
	        {
	            @Override
	            public void onEvent(SlackMessagePosted event, SlackSession session)
	            { 
	            	//Gets slack channel
	            	SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
	                
	            	//Gets message written in Slack
	            	String messageContent = event.getMessageContent();
	                
	            	//Gets which slack user wrote the  message
	            	SlackUser messageSender = event.getSender();
	              
	                //Instantiate and initialize a new twitter status update
	                StatusUpdate statusUpdate = new StatusUpdate(messageContent);
	                
	                //tweet or update status
	                //Handles the action of updating in the case of the updating resulting in a standard error
	                try {
					
	                	Status status = twitter.updateStatus(statusUpdate);
					
	                } catch (TwitterException e) {
						
						e.printStackTrace(); 
					
					} 
	            }
	        };
	        //adds it session
	        session.addMessagePostedListener(messagePostedListener);
	        

	    }
		
 
	}





