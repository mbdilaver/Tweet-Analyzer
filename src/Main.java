import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {

			TweetAnalyzer twa = new TweetAnalyzer();
//			ArffData arff_train = twa.createTrainingData("tweets");
//			arff_train.saveData2File("train-7");
////
//			ArffData arff_test = twa.createTestData("test");
//			arff_test.saveData2File("test-7");
//			
//			
//			Tweet tw = new Tweet("sevmiyorum");
//			String prediction = twa.evaluateTestOnTweet("model-2.model", tw);
			
			
			
			new MyGUI("model-3-ngram.model");
			
	}
}
