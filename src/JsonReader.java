import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;

import org.json.JSONObject;

public class JsonReader {
	
	private static ArrayList<String> ErrorFile = new ArrayList<String>();;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Hello! World! Json");
		// The file is too large to load into memory
		// String content = new
		// String(Files.readAllBytes(Paths.get("D:\\glove_832_100.json")), "UTF-8");
		// JSONObject J = new JSONObject(content);
		// System.out.println(J.get("Filename"));
		// Connect to DB ref
		// :http://xken831.pixnet.net/blog/post/427790957-%5Bjava%5D%E4%BD%BF%E7%94%A8-java-%E9%80%A3%E7%B5%90-mysql
		Connection conn = null;
		Statement st;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("連接成功MySQLToJava");
			// 建立讀取資料庫 (test 為資料庫名稱; user 為MySQL使用者名稱; passwrod 為MySQL使用者密碼)
			String datasource = "jdbc:mysql://localhost:3306/PaperData?user=root&password=12qwaszx&autoReconnect=true&useSSL=false";
			conn = DriverManager.getConnection(datasource);
			System.out.println("連接成功MySQL");
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileReader fr = new FileReader("D:\\MSD_mle1366.json");
		BufferedReader br = new BufferedReader(fr);

		String song_dataline;
		String insertSql;
		
		int filectr = 0;
		while ((song_dataline = br.readLine()) != null) {
			filectr +=1;
			System.out.println(filectr);
			System.out.flush();
			JSONObject J = new JSONObject(song_dataline);
//			System.out.println(J.keySet());
			// System.out.println(song_dataline);

			//System.out.println(J.get("Filename").getClass());
			//System.out.println(J.get("WordMartix").getClass());
			
	// for write
	// String stringToBeInserted = jsonObject.toString();
	// and insert this string into DB
	// for read
	// String json = Read_column_value_logic_here
	// JSONObject jsonObject = new JSONObject(json);
			try {

	// note field don't need " in name , but insert data need it, use\" to add " or
	// it will get syntax error.
				insertSql = "INSERT INTO paperdata.MSD_1366 (Filename, Spectrogram) VALUES " + "(\""
						+ J.get("Filename") + "\",\"" + J.get("Spectrogram").toString() + "\");";
				// System.out.println(insertSql);

				st = conn.createStatement();
				st.execute(insertSql);
				
			} catch (Exception e) {
				e.printStackTrace();
				ErrorFile.add(J.get("Filename").toString());
			}	
//			break;
		}
		System.out.println(ErrorFile.size());
	}

}
