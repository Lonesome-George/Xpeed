package xsl.com.model;

import java.util.List;

public class QueryBean {
	
	private String queryId;
	private String queryWords;
	private List<String>results;
	
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getQueryWords() {
		return queryWords;
	}
	public void setQueryWords(String queryWords) {
		this.queryWords = queryWords;
	}
	public List<String> getResults() {
		return results;
	}
	public void setResults(List<String> results) {
		this.results = results;
	}
	
	

}
