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
	
	
	private Connection connect() {
		// SQLite connection url
		String url = "jdbc:sqlite:";
		String path = "C:/EclipseWorkspace/TweetCollector/db/tweets.db";
		url += path;
		
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	private ArrayList<Tweet> getAllTweets() {
        String sql = String.format("SELECT * FROM tweets");
        ResultSet rs;
        ArrayList<Tweet> tweets_list = new ArrayList<Tweet>();
        try 
        {
        	Connection conn = this.connect();
        	Statement stmt  = conn.createStatement();
            rs    = stmt.executeQuery(sql);
            
            while(rs.next()) 
            {
            	int id = rs.getInt("id");
            	String body = rs.getString("body");
            	int hashtag_id = rs.getInt("hashtag_id");
            	Tweet tweet = new Tweet(id, body, hashtag_id);
            	tweets_list.add(tweet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return tweets_list;
	}
	
	private String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
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
	
	
	public String filterString(String str) {
		//////// Filter tweets
		// 1 Remove URLs
		// 2 Remove hashtag symbol '#'
		// 3 Remove mention symbol '@' and usertag
		// 4 Remove re-tweet indicator 'RT'
		// 5 Remove punctuations
		// 6 Lower letters
		// 7 Double or more spaces to one space
		
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
		str = str.toLowerCase(new Locale("tr"));
		
		//// 7 ////
		str = removeExtraSpaces(str);
		
		return str;
	}
	
	public void filterTweets() {

		ArrayList<Tweet> tweets_list = new ArrayList<Tweet>();
		for (Tweet tweet : tweets_list) {
			String body = tweet.getBody();
			body = filterString(body);
			
		}
	}
}
