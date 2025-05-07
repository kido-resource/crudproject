package com.shinhan.day15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDTest2 {

	public static void main(String[] args) {
		// 1. JDBC Driver 준비(class path 추가)
		// 2. JDBC Driver load
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String userid = "hr", userpass = "1234";
		Connection conn = null;		// 세 가지 변수 초기화 => Connection, Statement, ResultSet
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				select department_id, count(*)
				from employees
				group by department_id
				having  count(*) >= 5
				order by 2 desc
				""";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, userid, userpass);
			st = conn.createStatement();
			rs = st.executeQuery(sql); // rs는 표와 비슷하다. (행, 열)
			while(rs.next()) {
				int deptid = rs.getInt(1);
				int cnt = rs.getInt(2);
				System.out.println("부서 코드: " + deptid + "\t인원수: " + cnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
