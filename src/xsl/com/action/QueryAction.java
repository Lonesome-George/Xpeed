package xsl.com.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import xsl.com.model.QueryBean;
import xsl.com.service.QueryService;
import xsl.com.util.util;
import zzh.com.demo.Searcher;
import zzh.com.utils.QueryDoc;

import com.opensymphony.xwork2.ActionSupport;

public class QueryAction extends ActionSupport implements ServletRequestAware {

	private int pageSize = 10;
	private int totalPage;
	private List<QueryDoc> queryDocs = new ArrayList<QueryDoc>();
	private int pageNo =1;


	private QueryBean query = new QueryBean();
	util stuUtil = new util();


	private String queId;
	private String queryWords;
	private int totalHits;
	private double querySeconds;

	public String queDetail() throws Exception {
		String queryWord = query.getQueryWords();
		if (!queryWord.equals("")) {
			long startTime = new Date().getTime();
			
//			 orderService.findAll(orderVOpage, order,flag);
			System.out.println("query: " + queryWord);
			Searcher searcher = new Searcher();
			totalHits = searcher.query(queryWord, 10);
			queryDocs = searcher.fetchQueryDocs(10, 20);
			totalPage = getTotalPages();
			System.out.println("totalPage: " + totalPage);
			int start_id = pageSize*(pageNo-1);
			int end_id = pageSize*pageNo;
			queryDocs = searcher.fetchQueryDocs(start_id, end_id);
			
			long endTime = new Date().getTime();
			querySeconds = (double)(endTime - startTime) / 1000;
//			int length = queryDocs.size();
//			for (int i = 0; i < length; i++) {
//				System.out.println(queryDocs.get(i).getUrl() + '\t');
//				System.out.println(queryDocs.get(i).getBody());
//			}
		}
		return "detail";
	}
	
	
	public int getTotalPages() {
		if (totalHits == -1)
			return -1;

		int count = totalHits / pageSize;
		if (totalHits % pageSize > 0) {
			count++;
		}
		return count;
	}

	// --------------setter and getter--------------------
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub

	}

	public QueryBean getQuery() {
		return query;
	}

	public void setQuery(QueryBean query) {
		this.query = query;
	}


	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	public util getStuUtil() {
		return stuUtil;
	}

	public void setStuUtil(util stuUtil) {
		this.stuUtil = stuUtil;
	}

	public String getQueId() {
		return queId;
	}

	public void setQueId(String queId) {
		this.queId = queId;
	}

	public String getQueryWords() {
		return queryWords;
	}

	public void setQueryWords(String queryWords) {
		this.queryWords = queryWords;
	}

	public List<QueryDoc> getQueryDocs() {
		return queryDocs;
	}

	public void setQueryDocs(List<QueryDoc> queryDocs) {
		this.queryDocs = queryDocs;
	}
	

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}
	

	public double getQuerySeconds() {
		return querySeconds;
	}

	public void setQuerySeconds(double querySeconds) {
		this.querySeconds = querySeconds;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
