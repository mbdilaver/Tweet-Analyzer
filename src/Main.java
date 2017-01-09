import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.antlr.v4.runtime.Token;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;
import zemberek.core.TextSegmenter.WordSetSegmenter;
import zemberek.core.io.Words;
import zemberek.core.logging.Log;
import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.analysis.tr.TurkishSentenceAnalyzer;
import zemberek.morphology.analysis.tr.TurkishTextToNumberConverter;
import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;
import zemberek.tokenizer.ZemberekLexer;
import zemberek.tokenizer.antlr.TurkishLexer;

public class Main {
	
	public static void main(String[] args) throws IOException {

			TweetAnalyzer twa = new TweetAnalyzer();
//			ArffData arff_train = twa.createTrainingData("tweets");
//			arff_train.saveData2File("train-6");

//			ArffData arff_test = twa.createTestData("test");
//			arff_test.saveData2File("test-6");
   
			Tweet tw = new Tweet("kederliyim");
			String prediction = twa.evaluateTestOnTweet("model-lemma-first-naive.model", tw);
			
			
	}
}
