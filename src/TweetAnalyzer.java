import java.util.ArrayList;
import java.util.Map;

public class TweetAnalyzer {
	private Database db;
	private Map hashtag_map;
	
	public TweetAnalyzer() {
		this.db = new Database();
		this.hashtag_map = this.db.getHashtagMap();
	}
	
	public ArffData createTrainingData(String table_name) {
		Preprocessor pr = new Preprocessor();
		LanguageOp lang_op = new LanguageOp();
		ArffData arff = new ArffData();
		
		// Get all tweets from given table 
		ArrayList<Tweet> tweets_list = db.getAllTweets(table_name);
		
		int i = 0, 
			pos = 0,
			neg = 0,
			size = tweets_list.size();
		
		System.out.println("[INFO] Creating training data set...");
		
		for (Tweet tweet : tweets_list) {
			// Do pre-process operations
			Tweet filtered_tw = pr.filterTweetBody(tweet);
			
			// Get lemma form of all words
			String body = filtered_tw.getBody();
			body = lang_op.getLemmaSentence(body);
			filtered_tw.setBody(body);
			
			// Emotion value
			int em = (int) hashtag_map.get(tweet.getHashtag_id());
			
			// Add instance to data set
			arff.addData(tweet.getBody(), em);
			
			// Information operations and increments
			System.out.println("[INFO] " + i + " : " + size);
			if (em == 1)
				pos += 1;
			else if (em == 0)
				neg += 1;
			i++;
		}		
		
		System.out.println("[INFO] Creating training data set is completed");
		
		System.out.println("[RESULT] Pos : " + pos + " Neg : " + neg + " Total: " + i);
		
		return arff;
	}
	
	public ArffData createTestData(String table_name) {
		Preprocessor pr = new Preprocessor();
		LanguageOp lang_op = new LanguageOp();
		ArffData arff = new ArffData();
		
		// Get tweets from given table
		ArrayList<Tweet> tweets_list = db.getAllTestTweets(table_name);
		
		int i = 0, 
			pos = 0,
			neg = 0,
			size = tweets_list.size();
		
		System.out.println("[INFO] Creating test data set...");
		
		for (Tweet tweet : tweets_list) {
			// Do pre-process operations
			Tweet filtered_tw = pr.filterTweetBody(tweet);
			
			// Get lemma values for all words
			String body = filtered_tw.getBody();
			body = lang_op.getLemmaSentence(body);
			filtered_tw.setBody(body);
			
			// Emotion value
			int em = (int) tweet.getHashtag_id();
			
			// Add instance to data set
			arff.addData(tweet.getBody(), em);
			
			// Information
			System.out.println("[INFO] " + i + " : " + size);
			i++;
		}		
		
		System.out.println("[INFO] Creating test data set is completed");
		
		return arff;
	}
}
