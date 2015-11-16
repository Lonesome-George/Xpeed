package zzh.com.demo;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import zzh.com.utils.Constants;
import zzh.com.utils.JCSimilarity;
import zzh.com.utils.QueryDoc;

// 
public class Searcher {

	private static Logger logger = Logger.getLogger(Searcher.class);

	private String indexDirStr = "D:\\Temp\\luceneIndex";

	private Similarity similarity;
	private IndexSearcher searcher;
	private Analyzer analyzer;
	private Query query;
	private TopDocs topDocs;

	public Searcher() {
		// This is the directory that hosts the Lucene index
		File indexDir = new File(indexDirStr);
		if (!indexDir.exists()) {
			System.out.println("The Lucene index is not exist");
			return;
		}

		try {
			FSDirectory directory = FSDirectory.open(Paths.get(indexDirStr));
			similarity = new JCSimilarity();
			searcher = new IndexSearcher(DirectoryReader.open(directory));
			// searcher.setSimilarity(similarity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		analyzer = new SmartChineseAnalyzer(true);
		analyzer = new IKAnalyzer();
	}

	public int query(String queryStr, int n) {
		String[] fields = { Constants.TITLE, Constants.BODY };
		try {
			query = new MultiFieldQueryParser(fields, analyzer).parse(queryStr);
			topDocs = searcher.search(query, n);
			
			// for logs
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int length = scoreDocs.length;
			logger.error("query: " + queryStr);
			for (int i = 0; i < length; i++) {
				logger.error("doc" + i + " score: " + scoreDocs[i].score);
			}
			// end
			return topDocs.totalHits;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<QueryDoc> fetchQueryDocs(int startDocID, int endDocID) {
		ArrayList<QueryDoc> queryDocs = new ArrayList<QueryDoc>();
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		int maxDocID = scoreDocs.length - 1;   // 当前查询结果中最大的文档ID
		if (maxDocID < endDocID && topDocs.totalHits > 0) { // 当前查询的结果数目不足以显示
			try {
				TopDocs tempTopDocs = searcher.searchAfter(scoreDocs[maxDocID], query, 100);
				topDocs = TopDocs.merge(topDocs.totalHits, new TopDocs[]{tempTopDocs});
				scoreDocs = topDocs.scoreDocs;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 返回检索结果
		int totalHits = topDocs.totalHits;
		for (int i = startDocID; i < endDocID && i < totalHits; i++) {
			QueryDoc queryDoc = fetchQueryDoc(query, scoreDocs[i].doc);
			queryDocs.add(queryDoc);
		}
		return queryDocs;
	}

	public QueryDoc fetchQueryDoc(Query query, int docID) {
		Document document = fetchDocument(docID);
		String url = document.get(Constants.URL);
		String title = document.get(Constants.TITLE);
		String body = document.get(Constants.BODY);

		try {
			title = displayHighlightHtml(query, analyzer, Constants.TITLE,
					title, 100);
			body = displayHighlightHtml(query, analyzer, Constants.BODY, body,
					100);
			return new QueryDoc(url, title, body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Document fetchDocument(int docID) {
		try {
			return searcher.doc(docID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取高亮显示的html代码
	 * 
	 * @param query
	 *            查询
	 * @param analyzer
	 *            分词器
	 * @param fieldName
	 *            域名
	 * @param text
	 *            域文本
	 * @param fragmentSize
	 *            结果的长度（不含html标签长度）
	 * @return 高亮显示的html代码
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	static String displayHighlightHtml(Query query, Analyzer analyzer,
			String fieldName, String text, int fragmentSize)
			throws IOException, InvalidTokenOffsetsException {
		// 创建一个高亮器
		Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(
				"<font color='red'>", "</font>"), new QueryScorer(query));
		Fragmenter fragmenter = new SimpleFragmenter(fragmentSize);
		highlighter.setTextFragmenter(fragmenter);
		String highlightText = highlighter.getBestFragment(analyzer, fieldName, text);
		return highlightText != null ? highlightText : text;
	}

	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		String queryStr = "毛泽东";
		Searcher searcher = new Searcher();
		int totalHits = searcher.query(queryStr, 10);
		ArrayList<QueryDoc> queryDocs = searcher.fetchQueryDocs(10, 20);
		int length = queryDocs.size();
		for (int i = 0; i < length; i++) {
			System.out.println(queryDocs.get(i).getUrl() + '\t');
			System.out.println(queryDocs.get(i).getBody());
		}
	}
}
