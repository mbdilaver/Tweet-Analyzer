import java.util.ArrayList;
import java.util.Locale;

import zemberek.core.TextSegmenter.WordSetSegmenter;
import zemberek.core.io.Words;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.tr.TurkishTextToNumberConverter;
import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;
import zemberek.tokenizer.antlr.TurkishLexer;

public class Main {
	public static void main(String[] args) {
			System.out.println("Hello world!");
			Preprocessor pr = new Preprocessor();
			String body = "\"Hepinizden        nefret ediyorum\"cümlesi hayatımın özeti gibi_____🖊";
			
			body = pr.filterString(body);
			
//			body = Words.capitalizeFully(body);
			System.out.println(body);
	}
}
