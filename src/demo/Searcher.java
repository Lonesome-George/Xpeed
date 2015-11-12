package demo;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import utils.JCSimilarity;

class QueryRes {
	public QueryRes(String url, String title, String content) {
		this.url = url;
		this.title = title;
		this.content = content;
	}
	
	String url;
	String title;
	String content;
}

// 这里这样处理其实不支持并行查询
public class Searcher {

	private String indexDirStr = "D:\\Temp\\luceneIndex";
	
	Similarity similarity;
	IndexSearcher searcher;
	Analyzer analyzer;

	public Searcher() {
		// This is the directory that hosts the Lucene index
		File indexDir = new File(indexDirStr);
		if (!indexDir.exists()) {
			System.out.println("The Lucene index is not exist");
			return;
		}

		try {
			FSDirectory directory = FSDirectory.open(Paths.get("D:\\Temp\\luceneIndex"));
			similarity = new JCSimilarity();
			searcher = new IndexSearcher(DirectoryReader.open(directory));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		analyzer = new StandardAnalyzer();
	}
	
//	public ScoreDoc[] query(String queryStr, int n) {
//		Term term = new Term("content", queryStr.toLowerCase());
//		termQuery = new TermQuery(term);// 这里query构造得不好
//		try {
//			TopDocs topDocs = searcher.search(termQuery, n);
//			return topDocs.scoreDocs;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public ArrayList<QueryRes> query(String queryStr, int n) {
		ArrayList<QueryRes> queryRes = new ArrayList<>();
		try {
			String[] fields = { "title", "content" };  
			Query query = new MultiFieldQueryParser(fields, analyzer).parse(queryStr);  
			TopDocs topDocs = searcher.search(query, n);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int length = scoreDocs.length;
			for (int i=0;i<length;i++) {
				queryRes.add(fetchRes(query, scoreDocs[i].doc));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryRes;
	}
	
	public QueryRes fetchRes(Query query, int docID) {
		Document document = fetchDocument(docID);
		String url = document.get("url");
		String title = document.get("title");
		String content = document.get("content");
		
		try {
			title = getHighlightHtml(query, analyzer, "title", title, 100);
			content = getHighlightHtml(query, analyzer, "content", content, 100);
			return new QueryRes(url, title, content);
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

	public static void main(String[] args) throws Exception {
		String queryStr = "java";
		Searcher searcher = new Searcher();
		ArrayList<QueryRes> queryRes = searcher.query(queryStr, 10);
		int length = queryRes.size();
		for (int i=0;i<length;i++) {
			System.out.println(queryRes.get(i).url);
		}
	}

	/**
	 * 获取高亮显示结果的html代码
	 * 
	 * @param query
	 *            查询
	 * @param analyzer
	 *            分词器
	 * @param fieldName
	 *            域名
	 * @param fieldContent
	 *            域内容
	 * @param fragmentSize
	 *            结果的长度（不含html标签长度）
	 * @return 结果（一段html代码）
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	static String getHighlightHtml(Query query, Analyzer analyzer, String fieldName, String fieldContent,
			int fragmentSize) throws IOException, InvalidTokenOffsetsException {
		// 创建一个高亮器
		Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter("<font color='red'>", "</font>"),
				new QueryScorer(query));
		Fragmenter fragmenter = new SimpleFragmenter(fragmentSize);
		highlighter.setTextFragmenter(fragmenter);
		return highlighter.getBestFragment(analyzer, fieldName, fieldContent);
	}
}
