package com.shinhan.day15;

// DBUtil에 미리 작성해놓고 호출하는 형식
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.shinhan.common.DBUtil;

// CRUD (Create, Read, Update, Delete)
// Read -> Select
public class CRUDTest3 {

	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
					select department_id, max(salary), min(salary)
					from employees
					group by department_id
					having  max(salary) <> min(salary)
					""";
		
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				int deptId = rs.getInt(1);
				int max = rs.getInt(2);
				int min = rs.getInt(3);
				System.out.println(deptId + "\t" + max + "\t" + min);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
	}

}
