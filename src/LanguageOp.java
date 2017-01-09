import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.antlr.v4.runtime.Token;

import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.tokenizer.ZemberekLexer;

public class LanguageOp {
	private TurkishMorphology morphology;

	public LanguageOp() {
		super();
		try {
			this.morphology = TurkishMorphology.createWithDefaults();
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
			
			// Lower case all letters according to Turkish rules
			lemma = lemma.toLowerCase(new Locale("tr"));
			
			// I don't know why but there are "â" letters in the result set
			// so convert them to letter "a"
			lemma = lemma.replaceAll("â", "a");
			
			// Add lemma to the sentence if it is a known sentence in Zemberek
			if (!(lemma.equals("unk") || lemma.equals("?")))
				sentence_list.add(lemma);
			
//			for (WordAnalysis result : results) {
//	            System.out.println(result.formatLong());
//	            System.out.println("\tStems = " + result.getStems());
//	            System.out.println("\tLemmas = " + result.getLemmas());
//		}

		}
		
		String outsentence = String.join(" ", sentence_list);
		return outsentence;		
	}
}
