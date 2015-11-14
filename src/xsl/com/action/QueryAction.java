package xsl.com.action;

import java.util.ArrayList;
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

//	private List<QueryBean> list = new ArrayList<QueryBean>();
	private List<QueryDoc> queryDocs = new ArrayList<QueryDoc>();

	public List<QueryDoc> getQueryDocs() {
		return queryDocs;
	}

	public void setQueryDocs(List<QueryDoc> queryDocs) {
		this.queryDocs = queryDocs;
	}

	private QueryBean query = new QueryBean();
//	QueryService queService = new QueryService();
	util stuUtil = new util();
	private String queId;
	private String queryWords;

	public String queDetail() throws Exception {
		String queryWord = query.getQueryWords();
		if (!queryWord.equals("")) {
			System.out.println(queryWord);
//			list = queService.findQuery(query.getQueryWords());

			Searcher searcher = new Searcher();
			int totalHits = searcher.query(queryWord, 10);
			queryDocs = searcher.fetchQueryDocs(0, 10);
			
//			int length = queryDocs.size();
//			for (int i = 0; i < length; i++) {
//				System.out.println(queryDocs.get(i).getUrl() + '\t');
//				System.out.println(queryDocs.get(i).getBody());
//			}

		}

		return "detail";
//		return "index";
	}

	// --------------setter and getter--------------------
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub

	}

//	public List<QueryBean> getList() {
//		return list;
//	}
//
//	public void setList(List<QueryBean> list) {
//		this.list = list;
//	}

	public QueryBean getQuery() {
		return query;
	}

	public void setQuery(QueryBean query) {
		this.query = query;
	}

//	public QueryService getQueService() {
//		return queService;
//	}
//
//	public void setQueService(QueryService queService) {
//		this.queService = queService;
//	}

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

}
