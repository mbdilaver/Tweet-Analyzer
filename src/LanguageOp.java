import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.antlr.v4.runtime.Token;

import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.morphology.generator.SimpleGenerator;
import zemberek.morphology.lexicon.DictionaryItem;
import zemberek.morphology.lexicon.RootLexicon;
import zemberek.morphology.lexicon.Suffix;
import zemberek.tokenizer.ZemberekLexer;

public class LanguageOp {
	private TurkishMorphology morphology;
	private RootLexicon lexicon;
	private SimpleGenerator generator;
	
	public LanguageOp() {
		super();
		try {
			this.morphology = TurkishMorphology.createWithDefaults();
			this.lexicon = morphology.getLexicon();
			this.generator = morphology.getGenerator();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getLemmaSentence(String sentence) {
		ZemberekLexer lexer = new ZemberekLexer();
		Iterator<Token> tokenIterator = lexer.getTokenIterator(sentence);
		
		ArrayList<String> sentence_list = new ArrayList<String>();
		
		while (tokenIterator.hasNext()) {
			Token token = tokenIterator.next();
			String word = token.getText();
			List<WordAnalysis> results = this.morphology.analyze(word);
			
//			List<String> lemmas = results.get(0).getLemmas();
//			String lemma = lemmas.get(lemmas.size()-1);
			
			
			
			// Get first result
			String lemma = results.get(0).getLemma();
//			System.out.println(lemma);
			String feature_word = lemma;
			
			boolean cond = !lemma.toLowerCase().equals("unk") && 
					results.get(0).getSuffixes().size() > 0;
			
			if ( cond ) {
				DictionaryItem newStem = this.lexicon.getMatchingItems(lemma).get(0);
				
				
	//			results.get(0).getSuffixes().get(0);
				
				List<Suffix> listt = new ArrayList<Suffix>();
			
				listt.add(results.get(0).getSuffixes().get(0));
				
				String[] generated = this.generator.generate(newStem, listt);
				
				if (generated.length > 0)
					feature_word = generated[0];
			}
			// Lower case all letters according to Turkish rules
			feature_word = feature_word.toLowerCase(new Locale("tr"));
			
			// I don't know why but there are "â" letters in the result set
			// so convert them to letter "a"
			feature_word = feature_word.replaceAll("â", "a");
			// Add lemma to the sentence if it is a known sentence in Zemberek
			if (!(feature_word.equals("unk") || feature_word.equals("?")))
				sentence_list.add(feature_word);
			
//			for (WordAnalysis result : results) {
//	            System.out.println(result.formatLong());
//	            System.out.println("\tStems = " + result.getStems());
//	            System.out.println("\tLemmas = " + result.getLemmas());
//			}

		}
		
		String outsentence = String.join(" ", sentence_list);
//		System.out.println(outsentence);
		return outsentence;		
	}
}
