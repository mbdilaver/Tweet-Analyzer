
public class Tweet {
	private long id;
	private String body;
	private int hashtag_id;
	public Tweet(long id, String body, int hashtag_id) {
		super();
		this.id = id;
		this.body = body;
		this.hashtag_id = hashtag_id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public long getId() {
		return id;
	}
	public int getHashtag_id() {
		return hashtag_id;
	}
	
	
}
