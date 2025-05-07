package com.shinhan.day15;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.shinhan.common.DBUtil;

public class CRUDTest4 {

	public static void main(String[] args) throws SQLException {
		// 모두 성공하면 commit, 하나라도 실패하면 rollback
		// insert...
		// update...
		Connection conn = null;
		Statement st1 = null;
		Statement st2 = null;
		
		String sql1= """
				insert into emp1 ( employee_id, first_name, LAST_NAME, EMAIL, HIRE_DATE, JOB_ID )
				values ( 4, 'aa', 'bb', 'zzilre', sysdate, 'IT' )
				""";
		String sql2 = """
				update emp1 set salary = 3000 where employee_id = 198
				""";
		conn = DBUtil.getConnection();
		conn.setAutoCommit(false); // 하나가 커밋되어도 나머지 하나가 오류나면 전체 커밋을 막아야 함. (트랜잭션 단위)
		st1 = conn.createStatement();
		
		
		int result1 = st1.executeUpdate(sql1); // commit;  
		st2 = conn.createStatement();
		int result2 = st2.executeUpdate(sql2);
		
		if (result1 >= 1 && result2 >= 1) {  // 하나가 커밋되어도 나머지 하나가 오류나면 전체 커밋을 막아야 함. (트랜잭션 단위)
			conn.commit();
		} else conn.rollback();
	}
	
	
	public static void f_4(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
					delete from emp1
					where employee_id = 999
					""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql); // executeUpdate -> DML 실행  // 자동으로 commit;
		System.out.println(result >= 0 ? result + "건 Delete success": "Delete fail"); 
	}
	
	
	
	public static void f_3(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
					update emp1
					set department_id = (select department_id
					                        from employees
					                        where employee_id = 102), 
					    salary = (select salary
					                from employees
					                where employee_id = 103)
					where employee_id = 999	
					""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql); // executeUpdate -> DML 실행  // 자동으로 commit;
		System.out.println(result >= 1 ? "Update success": "Update fail"); 
	}

	
	
	public static void f_2(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
					insert into emp1 values (777, '김','기','do@naver.com','폰',sysdate, 'job', 100, 0.2, 100, '20' )		
					""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql); // executeUpdate -> DML 실행  // 자동으로 commit;
		System.out.println(result >= 1 ? "insert success": "insert fail"); 
	}

	
	
	
	public static void f_1(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = """
					select ename, sal, mgr
					from emp
					where mgr = ( 
					             select empno 
					             from emp 
					             where ename = 'KING' 
				             	)
					""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()) {
			String ename = rs.getString(1);
			int sal = rs.getInt(2);
			int mgr = rs.getInt(3);
			System.out.println(ename + "\t" + sal + "\t" + mgr);
		}
		
		DBUtil.dbDisconnect(conn, st, rs);
	}

}
