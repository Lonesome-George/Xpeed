package demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
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

import utils.JCSimilarity;

public class Searcher {

	public static void main(String[] args) throws Exception {
		String queryStr = "java";
		// This is the directory that hosts the Lucene index
		File indexDir = new File("D:\\Temp\\luceneIndex");
		if (!indexDir.exists()) {
			System.out.println("The Lucene index is not exist");
			return;
		}

		FSDirectory directory = FSDirectory.open(Paths.get("D:\\Temp\\luceneIndex"));
		Similarity similarity = new JCSimilarity();
		IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(directory));
		searcher.setSimilarity(similarity);

		Term term = new Term("contents", queryStr.toLowerCase());
		TermQuery luceneQuery = new TermQuery(term);
		TopDocs topDocs = searcher.search(luceneQuery, 10);
		ScoreDoc[] pageDocs = topDocs.scoreDocs;

		Analyzer analyzer = new StandardAnalyzer();// 设定分词器
		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<B>", "</B>");// 设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter, new QueryScorer(luceneQuery));
		highlighter.setTextFragmenter(new SimpleFragmenter(150));// 设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据

		for (int i = 0; i < pageDocs.length; i++) {
			Document document = searcher.doc(pageDocs[i].doc);
			String fieldName = "contents";
			String fieldContent = document.get(fieldName);
//			System.out.println(displayHtmlHighlight(luceneQuery, analyzer, fieldName, fieldContent, 200));
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
	static String displayHtmlHighlight(Query query, Analyzer analyzer, String fieldName, String fieldContent,
			int fragmentSize) throws IOException, InvalidTokenOffsetsException {
		// 创建一个高亮器
		Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter("<font color='red'>", "</font>"),
				new QueryScorer(query));
		Fragmenter fragmenter = new SimpleFragmenter(fragmentSize);
		highlighter.setTextFragmenter(fragmenter);
		return highlighter.getBestFragment(analyzer, fieldName, fieldContent);
	}
}
