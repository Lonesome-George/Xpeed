package utils;

public class Page {
	
	public Page(String title, String content) {
		this.setTitle(title);
		this.setContent(content);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String title;
	private String content;
}