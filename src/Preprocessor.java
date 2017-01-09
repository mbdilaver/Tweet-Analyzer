import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessor {
	
	private String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i)," ").trim();
            i++;
        }
        return commentstr;
    }
	
	private String removeHashtagSymbol(String str) {
		String pattern = "[#]";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		while (m.find()) {
			str = str.replaceAll(m.group(0)," ").trim();
		}
		return str;
	}
	
	private String removeUsertag(String str) {
		String pattern = "[@]\\w+";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		while (m.find()) {
			str = str.replaceAll(m.group(0)," ").trim();
		}
		return str;
	}
	
	private String removeRTindicator(String str) {
		String pattern = "\\ART";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		while (m.find()) {
			str = str.replaceFirst(m.group(0)," ").trim();
		}
		return str;
	}
	
	private String removePunctuations(String str) {
		str = str.replaceAll("\\p{P}+"," ").trim();
		return str;
	}
	
	private String removeExtraSpaces(String str) {
		str = str.replaceAll("[ ]+", " ");
		return str;
	}
	
	private String removeNumbers(String str) {
		str = str.replaceAll("\\d+", " ");
		return str;
	}
	
	private String toLowerCase(String str) {
		str = str.toLowerCase(new Locale("tr"));
		return str;
	}
	
	private String removeSymbols(String str) {
		str = str.replaceAll("\\p{S}", " ");
		return str;
	}
	
	private String filterString(String str) {
		//////// Filter tweets
		// 1 Remove URLs
		// 2 Remove hashtag symbol '#'
		// 3 Remove mention symbol '@' and usertag
		// 4 Remove re-tweet indicator 'RT'
		// 5 Remove punctuations
		// 6 Remove symbols
		// 7 Lower letters
		// 8 Remove numbers
		// 9 Double or more spaces to one space
		
		//// 1 ////
		str = removeUrl(str);
		
		//// 2 ////
		str = removeHashtagSymbol(str);
		
		//// 3 ////
		str = removeUsertag(str);
		
		//// 4 ////
		str = removeRTindicator(str);
		
		//// 5 ////
		str = removePunctuations(str);
		
		//// 6 ////
		str = removeSymbols(str);
		
		//// 7 ////
		str = toLowerCase(str);
		
		//// 8 ////
		str = removeNumbers(str);
		
		//// 9 ////
		str = removeExtraSpaces(str);
		

		
		return str;
	}
	
	public Tweet filterTweetBody(Tweet tweet) {

		String body = tweet.getBody();
		body = filterString(body);
		tweet.setBody(body);
		return tweet;

	}
}
