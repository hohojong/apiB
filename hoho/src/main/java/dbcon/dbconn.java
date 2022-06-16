package dbcon;

import java.sql.Connection;
import java.sql.DriverManager;


public class dbconn {
	private Connection conn = null;
	private String coninfo ="jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC";
	private String idinfo ="root";
	private String pwdinfo="1234";



public Connection getConnection() {
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(coninfo, idinfo, pwdinfo);
		System.out.println("성공");
	
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("미연결");		//연결이 안되면 시스아웃에 미연결로 출력됨
	}

	
	System.out.println("연결");
	return conn;
 }
}