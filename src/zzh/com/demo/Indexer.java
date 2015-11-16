package zzh.com.demo;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import zzh.com.utils.Constants;
import zzh.com.utils.HtmlParser;
import zzh.com.utils.JCSimilarity;
import zzh.com.utils.Page;

/**
 * This class demonstrate the process of creating index with Lucene for text
 * files
 */
public class Indexer {

	private String indexDirStr = "D:\\Temp\\luceneIndex";
	
	private DataSet dataSet;
	private TFIDFSimilarity similarity;
	private IndexWriter indexWriter;

	public Indexer() {
		try {
			// indexDir is the directory that hosts Lucene's index files
			FSDirectory indexDir = FSDirectory.open(Paths.get(indexDirStr));
			
			// dataSet stores the source data
			dataSet = new DataSet();
			similarity = new JCSimilarity();
			
//			Analyzer analyzer = new SmartChineseAnalyzer(true);
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig lWriterConfig = new IndexWriterConfig(analyzer);
			lWriterConfig.setOpenMode(OpenMode.CREATE);
//			lWriterConfig.setSimilarity(similarity);

			indexWriter = new IndexWriter(indexDir, lWriterConfig);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		long startTime = new Date().getTime();
		try {
			ArrayList<Html> htmls;
			while((htmls = dataSet.fecthHtmls()) != null) {
				int length = htmls.size();
				for (int i=0;i<length;i++) {
					String urlStr = htmls.get(i).url;
					indexHtml(htmls.get(i).path, urlStr);
				}
			}
			indexWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = new Date().getTime();
		System.out.println("It takes " + (endTime - startTime)
				+ " milliseconds to create index.");
	}
	
	private void indexHtml(String pathStr, String urlStr) {
		try {
			Document document = new Document();
			document.add(new StringField(Constants.URL, urlStr, Field.Store.YES));
//			Page page = HtmlParser.parseHtml(pathStr);
			Page page = HtmlParser.parseHtml(urlStr, pathStr);
			Field titleField = new TextField(Constants.TITLE, page.getTitle(), Field.Store.YES);
			document.add(titleField);
			titleField.setBoost(5); // 设置标题的权重
			document.add(new TextField(Constants.BODY, page.getBody(), Field.Store.YES));
			indexWriter.addDocument(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Indexer indexer = new Indexer();
		indexer.start();
	}
}