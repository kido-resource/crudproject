package com.shinhan.day15;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// CRUD (Create, Read, Update, Delete)
// Read -> Select
public class CRUDTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 1. JDBC Driver 준비(class path에 추가)
		// 2. JDBC Driver가 메모리에 load
		Class.forName("oracle.jdbc.driver.OracleDriver"); // 첫 번쨰, 드라이버 불러오기 (Class.forName)
		System.out.println("2. JDBC Driver load 성공");
		
		// 3. Connection
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 두 번째, url 주소를 선언하고 계정, 비밀번호 선언 후 연결
		String userid = "hr", userpass = "1234";			// DriverManager.getConnection(a, b, c);
		Connection conn = DriverManager.getConnection(url, userid, userpass);
		System.out.println("3. Connection 성공");
		
		// 4. SQL문 보낼 통로 얻기
		Statement st = conn.createStatement();	// 세 번째, sql문을 보내기 위한 통로 세우기. (createStatement)
		System.out.println("4. SQL문 보낼 통로 얻기 성공");
		
		// 5. SQL문 생성, 실행						// 네 번째, sql문 작성 및 실행 (ResultSet, executeQuery)
		String sql = """						
				select * 
				from employees 
				where department_id = 80
				""";
		ResultSet rs = st.executeQuery(sql); // 결과가 넘어온다.
		while(rs.next()) {
			int empid = rs.getInt("employee_id");
			String fname = rs.getString("first_name");
			Date hdate = rs.getDate("hire_date");
			double comm = rs.getDouble("commission_pct");
			System.out.printf("직원 번호: %d, 이름: %s, 날짜: %s, comm: %3.1f\n", empid, fname, hdate, comm);
		}
		rs.close(); // 자원 반납					// 다섯 번째, 사용한 자원 반납하기.
		st.close();
		conn.close();
	}

}
