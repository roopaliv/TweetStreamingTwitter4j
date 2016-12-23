import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.management.Query;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLEngineResult.Status;

import twitter4j.FilterQuery;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public class MainFile {
	static Integer  numberOfTweets = 0;
	 
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

//		Twitter4jSearch();
//		RestApiSearch();
		Twitter4jStreaming();
		
	}
	
	public static void RestApiSearch(){
		
	}
	
	
	
	public static void Twitter4jStreaming() throws FileNotFoundException, UnsupportedEncodingException{

		 PrintWriter streamingWriter;
			try {
				streamingWriter = new PrintWriter("/Users/paali/Documents/IRProject1/TwitterFeedCollection/Collections/SyrianCivil/Stream18Sep_en" + ".txt", "UTF-8");
			
		try {
		String consumerKey = "GRtWLHaxyDCWRyRQnT4q9ZiPp";
		String consumerSecret = "D3CA903mLivz6WiuM85DUPf61Wvo8ooW532vA73iTiZ5h9JUFl";
		String accessToken = "2330619510-aNK3w2F5XlbrpKYm4Ttv1BNqEOqGJN2CqX2dZr5";
		String accessSecret = "Gyn9iiHydhHfxWfBIOkcM7KewEG09V3PWuiVikzJ1H3C3";
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken)
			.setOAuthAccessTokenSecret(accessSecret);
		cb.setJSONStoreEnabled(true);
		
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	    StatusListener listener = new StatusListener() {

	        public void onStatus(twitter4j.Status status) {
	        	if(!status.isRetweet()){
	            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
	           

	           try{
	        	   
					String json = DataObjectFactory.getRawJSON(status);
					
					System.out.println(json);
					streamingWriter.println(json);
					

					System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	    			System.out.println("Streamed tweets are: " + ++numberOfTweets);
	           }
	           finally{
	        	  // streamingWriter.close();
	           }
				
	        	}
	        }

	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	            System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	            
	        }

	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	        }

	        public void onScrubGeo(long userId, long upToStatusId) {
	            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	        }

	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
	    };

	    //FilterQuery fq = new FilterQuery();
	    //String keywords[] = {"France", "Germany"};

	  
	  
	      FilterQuery tweetFilterQuery = new FilterQuery(); // See 
	      tweetFilterQuery.track(new String[]{"assad","#assad","Syrian civil war","#syriancivilwar","#syrianrevolution","#pray4syria","#syria_drama","#SyriaWar","Save Syria","Syria violence","Syrian refugees"}); // OR on keywords
	     // tweetFilterQuery.locations(new double[][]{new double[]{-126.562500,30.448674}, new double[]{-61.171875,44.087585}}); 
	      tweetFilterQuery.language(new String[]{"en"}); // Note that language does not work properly on Norwegian tweets 
	      twitterStream.addListener(listener);
	      twitterStream.filter(tweetFilterQuery); 
		} finally {
			//streamingWriter.close();
		}
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void Twitter4jSearch() throws FileNotFoundException, UnsupportedEncodingException{


		String consumerKey = "GRtWLHaxyDCWRyRQnT4q9ZiPp";
		String consumerSecret = "D3CA903mLivz6WiuM85DUPf61Wvo8ooW532vA73iTiZ5h9JUFl";
		String accessToken = "2330619510-aNK3w2F5XlbrpKYm4Ttv1BNqEOqGJN2CqX2dZr5";
		String accessSecret = "Gyn9iiHydhHfxWfBIOkcM7KewEG09V3PWuiVikzJ1H3C3";

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken)
			.setOAuthAccessTokenSecret(accessSecret);
		cb.setJSONStoreEnabled(true);

		int totalTweets = 0;
		TwitterFactory factory = new TwitterFactory(cb.build());
		Twitter twitter = factory.getInstance();
	
		//US Open
		//twitter4j.Query query = new twitter4j.Query("#usopen lang:en until:2016-08-30 since:2016-08-29  +exclude:retweets"); // for 29aug_en_ = 1790
		//twitter4j.Query query = new twitter4j.Query("#usopen lang:es since:2016-08-29 until:2016-08-30 +exclude:retweets"); // for 29aug_es_ = 3615
		//twitter4j.Query query = new twitter4j.Query("#usopen lang:en since:2016-08-30 until:2016-08-31 +exclude:retweets"); // for 30aug_en_ = 7595
		//twitter4j.Query query = new twitter4j.Query("#usopen lang:es since:2016-08-30 until:2016-08-31 +exclude:retweets"); // for 30aug_es_ = 4802
		
		//US Elections
		//twitter4j.Query query = new twitter4j.Query("#neverhillary lang:en since:2016-09-01 until:2016-09-02 +exclude:retweets"); // for 01sep_en_ = 7813	
		//twitter4j.Query query = new twitter4j.Query("#trumpenmexico lang:en since:2016-08-31 until:2016-09-01 +exclude:retweets"); // for 31aug_en_ = 9923
		//twitter4j.Query query = new twitter4j.Query("#nevertrump lang:en since:2016-09-09 until:2016-09-10 +exclude:retweets"); // for 09sep_en_ = 8179
		
		//Apple Event
		//twitter4j.Query query = new twitter4j.Query("#iPhone7 lang:en since:2016-09-10 until:2016-09-11 +exclude:retweets"); // for 10sep_en_ = 1595
		//twitter4j.Query query = new twitter4j.Query("#applewatch2 lang:en since:2016-09-05 +exclude:retweets"); // for 10sep_en_ = 3391
		
		
		//Syrian Civil
//		twitter4j.Query query = new twitter4j.Query("#syriancivilwar lang:en since:2016-09-05 +exclude:retweets"); // for since05sep_en_ = 258
//		twitter4j.Query query = new twitter4j.Query("#aleppo lang:en since:2016-09-09 until:2016-09-10 +exclude:retweets"); // for since03sep_en_ = 73
	//	twitter4j.Query query = new twitter4j.Query("#prayforsyria lang:en since:2016-09-05 +exclude:retweets"); // for 
		twitter4j.Query query = new twitter4j.Query("assad OR #assad OR Syriancivilwar OR #syriancivilwar OR #syrianrevolution OR #pray4syria OR #syria_drama OR #SyriaWar OR SaveSyria OR Syriaviolence OR Syrianrefugees lang:en since:2016-09-05 +exclude:retweets"); // 273
	
		//Game Of Thrones
		//twitter4j.Query query = new twitter4j.Query("#gameofthrones lang:en since:2016-09-10 until:2016-09-11 +exclude:retweets"); // for 10Sep_en_ = 96
		//twitter4j.Query query = new twitter4j.Query("#GOT lang:en since:2016-09-05 +exclude:retweets"); // for since05Sep_en_ = 4701
		
		 
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		PrintWriter writer = new PrintWriter("/Users/paali/Documents/IRProject1/TwitterFeedCollection/Collections/SyrianCivil/mixed05Sep_en_" + timeLog + ".txt", "UTF-8");

		query.count(100);
		try {
		twitter4j.QueryResult result = null;
		do {
			try {
				result = twitter.search(query);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			List<twitter4j.Status> tweets = result.getTweets();
			totalTweets = totalTweets + tweets.size();
		
				for (twitter4j.Status status : tweets) {
					System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
					String json = DataObjectFactory.getRawJSON(status);
					writer.println(json);
				}
				query = result.nextQuery();			
		} while (result == null || result.hasNext());
		writer.println("Buddy the number of tweets are: " + totalTweets);
		System.out.println("Buddy the number of tweets are: " + totalTweets);
		} finally {
			writer.close();
		}

	}
}
