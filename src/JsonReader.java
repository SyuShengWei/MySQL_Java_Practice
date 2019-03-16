import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.*;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class JsonReader {


	public static void main(String[] args) throws IOException {
        System.out.println("Hello! World! Json");
    //The file is too large to load into memory
        //String content = new String(Files.readAllBytes(Paths.get("D:\\glove_832_100.json")), "UTF-8");
        //JSONObject J = new JSONObject(content);
        //System.out.println(J.get("Filename"));
    //Connect to DB ref :http://xken831.pixnet.net/blog/post/427790957-%5Bjava%5D%E4%BD%BF%E7%94%A8-java-%E9%80%A3%E7%B5%90-mysql
        Connection conn = null;
        Statement st;
        ResultSet rs;
        try{

        	Class.forName("com.mysql.jdbc.Driver");
        	System.out.println("連接成功MySQLToJava");
        	//建立讀取資料庫 (test 為資料庫名稱; user 為MySQL使用者名稱; passwrod 為MySQL使用者密碼)
        	String datasource = "jdbc:mysql://localhost:3306/PaperData?user=root&password=12qwaszx&autoReconnect=true&useSSL=false";
        	conn = DriverManager.getConnection(datasource);
        	System.out.println("連接成功MySQL");
        }catch(Exception e){
        	e.printStackTrace();
        }
        FileReader fr = new FileReader("D:\\glove_832_100.json");
		BufferedReader br = new BufferedReader(fr);
		
		String song_dataline;
		String insertSql;
		ResultSet resultSet = null;
		while((song_dataline = br.readLine()) != null){  
			JSONObject J = new JSONObject(song_dataline);
	        //System.out.println(song_dataline);

	        System.out.println(J.get("Filename").getClass());
	        System.out.println(J.get("WordMartix").getClass());
	        
			
		//for write
			//String stringToBeInserted = jsonObject.toString();
			//and insert this string into DB
		//for read
			//String json = Read_column_value_logic_here
			//JSONObject jsonObject = new JSONObject(json);
			try {
	
				
				insertSql = "INSERT INTO glove_832_100 (Filename, WordMatrix) VALUES "
							+"("+J.get("Filename").toString().replace("'","").replace("\"","")+','+J.get("WordMartix").toString()+");";
	        	System.out.println(insertSql);
				
				st = conn.createStatement();
				st.execute(insertSql);
				rs = st.getResultSet();
				while (rs.next()) {
	                System.out.println("Generated: " + rs.getString(1));
	            }
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			break;
		}
        //System.out.println(br.toString());
        
		

	}

}
