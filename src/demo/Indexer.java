package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.FSDirectory;

import utils.HtmlParser;
import utils.JCSimilarity;
import utils.Page;

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
			
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig lWriterConfig = new IndexWriterConfig(analyzer);
			lWriterConfig.setSimilarity(similarity);

			indexWriter = new IndexWriter(indexDir, lWriterConfig);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		long startTime = new Date().getTime();
		try {
			ArrayList<Html> htmls = dataSet.fecthHtmls();
			int length = htmls.size();
			for (int i=0;i<length;i++) {
				String urlStr = htmls.get(i).url;
				indexHtml(htmls.get(i).path, urlStr);
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
//			System.out.println("Indexing file " + dataFiles[i].getCanonicalPath());
			Document document = new Document();
			File htmlFile = new File(pathStr);
			document.add(new StringField("url", urlStr, Field.Store.YES));
//			document.add(new TextField("contents", new FileReader(htmlFile)));
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFile)));
//			String content = "";
//			String line = null;
//			while ((line = bufferedReader.readLine()) != null) {
//				content += line;
//			}
			Page page = HtmlParser.parseHtml(pathStr);
			document.add(new StringField("title", page.getTitle(), Field.Store.YES));
			document.add(new TextField("content", page.getContent(), Field.Store.YES));
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