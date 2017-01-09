import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instances;

public class TweetAnalyzer {
	private Database db;
	private Map hashtag_map;
	private Preprocessor pre;
	private LanguageOp lang_op;
	
	public TweetAnalyzer() {
		this.db = new Database();
		this.hashtag_map = this.db.getHashtagMap();
		this.pre = new Preprocessor();
		this.lang_op = new LanguageOp();
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
	
	public List<String> evaluateTest(String model_file, String test_file) {
		List<String> predictions = new ArrayList<String>();
		try {
			Classifier cls = (Classifier) weka.core.SerializationHelper.read("./data/" + model_file);
			
			Instances test = new Instances(
					new BufferedReader(
							new FileReader("./data/" + test_file)));
			
			test.setClassIndex(test.numAttributes() - 1);
			
			Instances labeled = new Instances(test);
			
			for (int i = 0; i < test.numInstances(); i++) {
				double clsLabel = cls.classifyInstance(test.instance(i));
				labeled.instance(i).setClassValue(clsLabel);
				
				String prediction =  labeled.instance(i).classAttribute().value((int) labeled.instance(i).classValue());
				predictions.add(prediction);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return predictions;
	}
	
	public Instances getInstance() {
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		Attribute text_attr = new Attribute("text", (FastVector) null);
		attrs.add(text_attr);
		List<String> class_list = Arrays.asList("neg","pos");
		Attribute class_attrs = new Attribute("class", class_list);
		attrs.add(class_attrs);
		Instances data = new Instances("tweets", attrs, 0);
		return data;
	}
	
	public String evaluateTestOnTweet(String model_file, Tweet tweet) {
		// Do pre-process operations
		Tweet filtered_tw = this.pre.filterTweetBody(tweet);
		
		// Get lemma values for all words
		String body = filtered_tw.getBody();
		body = this.lang_op.getLemmaSentence(body);
		filtered_tw.setBody(body);
		
		String text = filtered_tw.getBody();
		
		String prediction = "";
		try {
			Classifier cls = (Classifier) weka.core.SerializationHelper.read("./data/" + model_file);
			
			Instances test = getInstance();
			
			double [] vals = new double[test.numAttributes()];
			vals[0] = test.attribute(0).addStringValue(text);
			vals[1] = 0;

			test.add(new DenseInstance(1.0, vals));
			
			test.setClassIndex(test.numAttributes() - 1);
			
			Instances labeled = new Instances(test);
			
			double clsLabel = cls.classifyInstance(test.instance(0));
			labeled.instance(0).setClassValue(clsLabel);
			
			prediction =  labeled.instance(0).classAttribute().value((int) labeled.instance(0).classValue());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prediction;
	}
}






























