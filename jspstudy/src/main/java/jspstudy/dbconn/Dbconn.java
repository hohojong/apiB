package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {	
	
	private String url="jdbc:mysql://127.0.0.1:3306/mysql?serverTimezone=UTC&characterEncoding=UTF-8";
	private String user="root";
	private String password="1234";		

	public Connection getConnection() {
		Connection conn = null;			

	try {
		//����� ����̹��߿� ��밡���� Ŭ���� ã�Ƽ� ����
		Class.forName("com.mysql.cj.jdbc.Driver");
		//���������� ���ؼ� ���ᰴü�� �������� conn�� ��´�
		conn = DriverManager.getConnection(url, user, password);
	} catch (Exception e) {		
		e.printStackTrace();
	}	

	return conn;
	}
}
