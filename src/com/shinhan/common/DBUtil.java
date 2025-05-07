package com.shinhan.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	// Oracle DB 연결 함수
	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.0.54:1521:xe";
		//String url = "jdbc:oracle:thin:@192.168.0.18:1521:xe"; // 교수님 DB 주소
		String userid = "hr", userpass = "1234"; 		// 상황에 맞게 계정 변경하기.
		//String userid = "scott", userpass = "tiger";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, userid, userpass);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// DB 연결시 사용한 자원 해제 함수
	public static void dbDisconnect(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (st != null) st.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// SQL 로그 찍는 메서드
	public static String buildDebugSQL(String sqlTemplate, Object... params) {
	    for (Object param : params) {
	        String val = (param instanceof String) ? "'" + param + "'" : String.valueOf(param);
	        sqlTemplate = sqlTemplate.replaceFirst("\\?", val);
	    }
	    return sqlTemplate;
	}
}
