package zzh.com.utils;

import java.io.File;
import java.io.IOException;

//import org.htmlparser.Node;
//import org.htmlparser.Parser;
//import org.htmlparser.nodes.TextNode;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlParser {

	public static Page parseHtml(String url, String path) {
		assert (url != null);
		String baseUrl = parseBaseUrl(url);
		File htmlFile = new File(path);
		try {
			Document doc = Jsoup.parse(htmlFile, null, baseUrl);
			String title = doc.title();
			Element bodyElem = doc.body();
			if (bodyElem != null) {
				String body = bodyElem.text();
				return new Page(title, body);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Page parseHtml(String url, File htmlFile) {
		assert (url != null);
		String baseUrl = parseBaseUrl(url);
		try {
			Document doc = Jsoup.parse(htmlFile, null, baseUrl);
			String title = doc.title();
			Element bodyElem = doc.body();
			if (bodyElem != null) {
				String body = bodyElem.text();
				return new Page(title, body);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Page parseHtml(String text) {
		Document doc = Jsoup.parse(text);
		String title = doc.title();
		Element bodyElem = doc.body();
		if (bodyElem != null) {
			String body = bodyElem.text();
			return new Page(title, body);
		}
		return null;
	}

	private static String parseBaseUrl(String url) {
		int index = url.indexOf("/", 8);
		if (index != -1)
			return url.substring(0, index + 1);
		return url;
	}

	public static void main(String[] args) {
		String baseUrl = "http://www.cnblogs.com/";
		String path = "D:\\Temp\\luceneData\\html\\d00000\\f00000";
		Page page = HtmlParser.parseHtml(baseUrl, path);
		System.out.println(page.getBody());
	}
}
