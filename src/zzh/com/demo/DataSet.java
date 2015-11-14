package zzh.com.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.sound.midi.Track;

class Html {
	public Html(String url, String path) {
		this.url = url;
		this.path = path;
	}

	String url;
	String path;
}

public class DataSet {

	// directory that store data
	private String dataDirStr = "D:\\Temp\\luceneData\\html\\d00000";
	// dataDir file
	private File dataDir;
	// file that store the map relationship of pageId and url
	private String mapFilenameStr = "index";
	// map file
	private File mapFile;
	// current pageId that reading
	private int curPageId;
	// to read mapping file
	private BufferedReader bufferedReader;

	public DataSet() {
		curPageId = 0;
		dataDir = new File(dataDirStr);
		mapFile = new File(dataDir, mapFilenameStr);
	}

	public ArrayList<Html> fecthHtmls() {
		ArrayList<Html> htmls = new ArrayList<Html>();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(mapFile)));
			String line = null;
			while (true) {
				line = bufferedReader.readLine();
				if (line == null)
					break;
				Html html = parseHtml(line);
				htmls.add(html);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmls;
	}

	public Html parseHtml(String line) {
		Html html = null;
		String pageId = "f0" + line.substring(0, 4);
		pageId = pageId.replace(' ', '0');
		String pathname = dataDirStr + "\\" + pageId;
		String urlStr = line.substring(5);
		html = new Html(urlStr, pathname);
		return html;
	}

	public static void main(String[] args) {
		DataSet dataset = new DataSet();
	}
}
