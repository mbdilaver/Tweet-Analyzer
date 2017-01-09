import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
	private Map hashtag_map;
	private Connection conn;
	
	public Database() {
		conn = connect();
		getHashtagInfo();
	}
	
	private Connection connect() {
		// SQLite connection url
		String url = "jdbc:sqlite:";
		String path = "C:/EclipseWorkspace/TweetAnalyzer/db/twatweets.db";
		url += path;
		
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	private void getHashtagInfo() {
		String sql = String.format("SELECT * FROM hashtags");
		ResultSet rs;
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
        try {
        	Statement stmt = this.conn.createStatement();
        	rs = stmt.executeQuery(sql);
			while(rs.next()) 
			{
				int id = rs.getInt("id");
				int emotion = rs.getInt("emotion");
				m.put(id, emotion);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.hashtag_map = m;
	}
	
	public Map getHashtagMap() {
		return hashtag_map;
	}

	public ArrayList<Tweet> getAllTweets(String table_name) {
        String sql = String.format("SELECT * FROM " + table_name);
        ResultSet rs;
        ArrayList<Tweet> tweets_list = new ArrayList<Tweet>();
        try 
        {
//        	Connection conn = this.connect();
        	Statement stmt  = this.conn.createStatement();
            rs    = stmt.executeQuery(sql);
            
            while(rs.next()) 
            {
            	long id = rs.getLong("id");
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
	public ArrayList<Tweet> getAllTestTweets(String table_name) {
        String sql = String.format("SELECT * FROM " + table_name);
        ResultSet rs;
        ArrayList<Tweet> tweets_list = new ArrayList<Tweet>();
        try 
        {
//        	Connection conn = this.connect();
        	Statement stmt  = this.conn.createStatement();
            rs    = stmt.executeQuery(sql);
            
            while(rs.next()) 
            {
            	long id = rs.getLong("id");
            	String body = rs.getString("body");
            	int hashtag_id = rs.getInt("real");
            	System.out.println(hashtag_id);
            	Tweet tweet = new Tweet(id, body, hashtag_id);
            	tweets_list.add(tweet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return tweets_list;
	}
	
	
	public void insertTweet(Tweet tweet,String table_name) {
		String sql = "INSERT INTO " + table_name + "(id,body,hashtag_id) VALUES(?,?,?) ";
		long id = tweet.getId();
		String body = tweet.getBody();
		int hashtag_id = tweet.getHashtag_id();
		try {
//			Connection conn = this.connect();
			PreparedStatement statement = this.conn.prepareStatement(sql);
			statement.setLong(1, id);
			statement.setString(2, body);
			statement.setInt(3, hashtag_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
