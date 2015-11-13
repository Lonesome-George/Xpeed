package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {

	// public static String getCharset(String content) {
	// final String CHARSET_STRING = "charset";
	// int index;
	// String ret;
	// ret = null;
	// if (null != content) {
	// index = content.indexOf(CHARSET_STRING);
	// if (index != -1) {
	// content = content.substring(index + CHARSET_STRING.length()).trim();
	// if (content.startsWith("=")) {
	// content = content.substring(1).trim();
	// index = content.indexOf(";");
	// if (index != -1)
	// content = content.substring(0, index);
	// // remove any double quotes from around charset string
	// if (content.startsWith("\"") && content.endsWith("\"") && (1 <
	// content.length()))
	// content = content.substring(1, content.length() - 1);
	// // remove any single quote from around charset string
	// if (content.startsWith("'") && content.endsWith("'") && (1 <
	// content.length()))
	// content = content.substring(1, content.length() - 1);
	// ret = findCharset(content, ret);
	// }
	// }
	// }
	// return (ret);
	// }
	//
	// public void setEncoding(String character_set) throws ParserException {
	// if (character_set != null &&
	// character_set.toLowerCase().equals("gb2312")) {
	// character_set = "GBK";
	// }
	// }
	//
	// public void parseHTML(HttpURLConnection uc) throws ParserException {
	// Node node;
	// String stringText;
	// String contentType = uc.getContentType();
	// String charSet = getCharset(contentType);
	// Lexer lexer = null;
	// if (charSet != null) {
	// try {
	// lexer = new Lexer(new Page(uc.getInputStream(), charSet));
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace(System.out);
	// return;
	// }
	// } else {
	// try {
	// lexer = new Lexer(new Page(uc.getInputStream(), "GB2312"));
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return;
	// }
	// }
	// lexer.setNodeFactory(typicalFactory);
	// boolean tryAgain = false;
	// while (null != (node = lexer.nextNode())) {
	// // omit script tag
	// if (node instanceof org.htmlparser.tags.ScriptTag) {
	// while (null != (node = lexer.nextNode())) {
	// if (node instanceof org.htmlparser.tags.Tag) {
	// org.htmlparser.tags.Tag tag = (org.htmlparser.tags.Tag) node;
	// if (tag.isEndTag() && "SCRIPT".equals(tag.getTagName())) {
	// // System.out.println("tagname:"+tag.getTagName());
	// break;
	// }
	// }
	// }
	// if (null == node)
	// break;
	// }
	// // omit script tag
	// else if (node instanceof org.htmlparser.tags.StyleTag) {
	// while (null != (node = lexer.nextNode())) {
	// if (node instanceof org.htmlparser.tags.Tag) {
	// org.htmlparser.tags.Tag tag = (org.htmlparser.tags.Tag) node;
	// if (tag.isEndTag())
	// break;
	// }
	// }
	// if (null == node)
	// break;
	// } else if (node instanceof StringNode) {
	// stringText = node.toPlainTextString();
	// if ("".equals(title))
	// continue;
	// stringText = stringText.replaceAll("[ \t\n\f\r ]+", " ");
	// stringText = TextHtml.html2text(stringText.trim());
	// if (!"".equals(stringText)) {
	// // System.out.println("stringText.len:"+stringText.length());
	// this.body.append(stringText);
	// this.body.append(" ");
	// // System.out.println(this.body);
	// }
	// } else if (node instanceof TagNode) {
	// TagNode tagNode = (TagNode) node;
	// String name = ((TagNode) node).getTagName();
	// if (name.equals("OPTION")) {
	// // omit option
	// lexer.nextNode();
	// lexer.nextNode();
	// // System.out.println("tag name:"+name);
	// } else if (name.equals("A") && !tagNode.isEndTag() && expand) {
	// String href = tagNode.getAttribute("HREF");
	// String urlDesc = null;
	// node = lexer.nextNode();
	// if (node instanceof StringNode) {
	// StringNode sNode = (StringNode) node;
	// String title = sNode.getText().trim();
	// if (title.length() >= 4) {
	// urlDesc = title;
	// // System.out.println("next node:"+title);
	// }
	// }
	// addLink(this.url, href, urlDesc);
	// } else if (name.equals("FRAME") && !tagNode.isEndTag() && expand) {
	// // FRAME SRC=
	// addLink(url, include, tagNode.getAttribute("SRC"), null);
	// // handle internal frame (iframes) as well
	// } else if (name.equals("TITLE") && !tagNode.isEndTag()) {
	// node = lexer.nextNode();
	// stringText = node.toPlainTextString().trim();
	// if (!"".equals(stringText)) {
	// this.title = stringText;
	// }
	// } else if (name.equals("META") && charSet == null) {
	// String contentCharSet = tagNode.getAttribute("CONTENT");
	// charSet = URLSummary.getCharset(contentCharSet);
	// tryAgain = true;
	// break;
	// }
	// }
	// }
	// // 如果在Meta信息中检测到新的字符编码， 则需要按照meta信息中的编码再次解析网页。
	// if (tryAgain) {
	// this.body = new StringBuffer();
	// try {
	// uc = (HttpURLConnection) uc.getURL().openConnection();
	// lexer = new Lexer(new Page(uc.getInputStream(), charSet));
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// lexer.setNodeFactory(typicalFactory);
	// while (null != (node = lexer.nextNode())) {
	// // System.out.println("node:"+node);
	// // omit script tag
	// if (node instanceof org.htmlparser.tags.ScriptTag) {
	// while (null != (node = lexer.nextNode())) {
	// if (node instanceof org.htmlparser.tags.Tag) {
	// org.htmlparser.tags.Tag tag = (org.htmlparser.tags.Tag) node;
	// if (tag.isEndTag() && "SCRIPT".equals(tag.getTagName())) {
	// break;
	// }
	// }
	// }
	// if (null == node)
	// break;
	// }
	// // omit script tag
	// else if (node instanceof org.htmlparser.tags.StyleTag) {
	// while (null != (node = lexer.nextNode())) {
	// if (node instanceof org.htmlparser.tags.Tag) {
	// org.htmlparser.tags.Tag tag = (org.htmlparser.tags.Tag) node;
	// if (tag.isEndTag())
	// break;
	// }
	// }
	// if (null == node)
	// break;
	// } else if (node instanceof StringNode) {
	// stringText = node.toPlainTextString();
	// if ("".equals(title))
	// continue;
	// stringText = stringText.replaceAll("[ \t\n\f\r ]+", "");
	// stringText = TextHtml.html2text(stringText.trim());
	// if (!"".equals(stringText)) {
	// this.body.append(stringText);
	// this.body.append(" ");
	// }
	// } else if (node instanceof TagNode) {
	// TagNode tagNode = (TagNode) node;
	// String name = ((TagNode) node).getTagName();
	// if (name.equals("OPTION")) {
	// // omit option
	// lexer.nextNode();
	// lexer.nextNode();
	// } else if (name.equals("A") && !tagNode.isEndTag() && expand) {
	// String href = tagNode.getAttribute("HREF");
	// String urlDesc = null;
	// node = lexer.nextNode();
	// if (node instanceof StringNode) {
	// StringNode sNode = (StringNode) node;
	// String title = sNode.getText().trim();
	// if (title.length() >= 4) {
	// urlDesc = title;
	// }
	// }
	// addLink(this.url, include, href, urlDesc);
	// } else if (name.equals("FRAME") && !tagNode.isEndTag() && expand) {
	// // FRAME SRC=
	// addLink(url, include, tagNode.getAttribute("SRC"), null);
	// // handle internal frame (iframes) as well
	// } else if (name.equals("TITLE") && !tagNode.isEndTag()) {
	// node = lexer.nextNode();
	// stringText = node.toPlainTextString().trim();
	// if (!"".equals(stringText)) {
	// this.title = stringText;
	// }
	// }
	// }
	// }
	// }
	// }

	public static Page parseHtml(String path) {
		try {
			Parser parser = new Parser(path);
			parser.setEncoding("utf-8");
			NodeList list = parser.parse(null);
			String title = "This is title";
			String content = list.toHtml();
			return new Page(title, content);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Page parseHtml(String url, String path) {
		assert (url != null);
		String baseUrl = parseBaseUrl(url);
		File htmlFile = new File(path);
		try {
			Document doc = Jsoup.parse(htmlFile, null, baseUrl);
			String title = doc.title();
			String body = doc.body().text();
			return new Page(title, body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		// try {
		// Parser parser = new Parser(path);
		//// parser.setEncoding("utf-8");
		// NodeList list = parser.parse(null);
		// int length = list.size();
		// Node node = null;
		// for (int i = 0; i < length; i++) {
		// node = list.elementAt(i);
		// if (node instanceof TextNode) {
		// System.out.println(node.getText());
		// }
		// }
		// } catch (ParserException pe) {
		// pe.printStackTrace();
		// }
		// Lexer lexer = new Lexer(path);
		// Node node = null;
		// try {
		// while (null != (node = lexer.nextNode()))
		// System.out.println(node.toString());
		// } catch (ParserException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		Page page = HtmlParser.parseHtml(baseUrl, path);
		System.out.println(page.getBody());
	}
}
