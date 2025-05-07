package com.shinhan.emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinhan.common.DBUtil;

// DAO (Data Access Object) : DB에 CRUD 작업 (select, Insert, update, delete)
// Statement는 SQL문을 보내는 통로... 바인딩 변수는 지원하지 않음.
// PreparedStatement : Statement를 상속 받음. 바인딩 변수를 지원함, SP 호출은 지원 안함.
// CallableStatement : sp 호출을 지원
public class EmpDAO {
	
	// Stored Procedure를 실행하기 (직원번호를 받아서 이메일과 급여를 return)
	public EmpDTO execute_sp(int empid) {
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection();
		CallableStatement st = null;
		
		String sql = "{call sp_empinfo2(?,?,?)}";
		
		try {
			st = conn.prepareCall(sql);
			st.setInt(1, empid); 	// in
			st.registerOutParameter(2, Types.VARCHAR); // out
			st.registerOutParameter(3, Types.DECIMAL); // out
			
			st.execute();
			emp = new EmpDTO();
			String email = st.getString(2);
			double salary = st.getDouble(3);
			emp.setEmail(email);
			emp.setSalary(salary);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	
	// 동적 SQL Update (수정) -> 수정한 것만 적용. 수정 안 하는 항목은 0으로 처리
	public int empUpdate(EmpDTO emp) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;		
		Map<String, Object> dynamicSQL = new HashMap<>();
		
		if(emp.getFirst_name()!=null) dynamicSQL.put("FIRST_NAME", emp.getFirst_name());
		if(emp.getLast_name()!=null) dynamicSQL.put("LAST_NAME", emp.getLast_name());
		if(emp.getSalary()!=null) dynamicSQL.put("SALARY", emp.getSalary());
		if(emp.getHire_date()!=null) dynamicSQL.put("HIRE_DATE", emp.getHire_date());
		if(emp.getEmail()!=null) dynamicSQL.put("EMAIL", emp.getEmail());
		if(emp.getPhone_number()!=null) dynamicSQL.put("PHONE_NUMBER", emp.getPhone_number());
		if(emp.getJob_id()!=null) dynamicSQL.put("JOB_ID", emp.getJob_id());
		if(emp.getCommission_pct()!=null) dynamicSQL.put("Commission_pct", emp.getCommission_pct());
		if(emp.getManager_id()!=null) dynamicSQL.put("manager_id", emp.getManager_id());
		if(emp.getDepartment_id()!=null) dynamicSQL.put("department_id", emp.getDepartment_id());

		String sql = " update employees set ";
	 	String sql2 = " where EMPLOYEE_ID = ? ";		 	
	 	for(String key:dynamicSQL.keySet()) { // map에서 내용을 keySet으로 꺼내 온다.
	 		sql += key + "=" + "?," ;  			// salary=?, email=?, 		
	 	}
	 	sql = sql.substring(0, sql.length()-1);
	 	sql += sql2;
	 	System.out.println(sql);
	 	
		try {
			st = conn.prepareStatement(sql);
			int i=1;
			for(String key:dynamicSQL.keySet()) {
		 		st.setObject(i++, dynamicSQL.get(key));
		 	} 
			st.setInt(i, emp.getEmployee_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}
	
	
	// 수정
	public int empUpdate2(EmpDTO emp) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		
		String sql = """
				update employees set 
					 first_name = ?,
					 last_name = ?,
					 email = ?,
					 phone_number = ?,
					 hire_date = ?,
					 job_id = ?,
					 salary = ?,
					 commission_pct = ?,
					 manager_id = ?,
					 department_id = ?
				where employee_id = ?
				""";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(11, emp.getEmployee_id());
			st.setString(1, emp.getFirst_name());
			st.setString(2, emp.getLast_name());
			st.setString(3, emp.getEmail());
			st.setString(4, emp.getPhone_number());
			st.setDate(5, emp.getHire_date());
			st.setString(6, emp.getJob_id());
			st.setDouble(7, emp.getSalary());
			st.setDouble(8, emp.getCommission_pct());
			st.setInt(9, emp.getManager_id());
			st.setInt(10, emp.getDepartment_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 입력
	public int empInsertById(EmpDTO emp) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = """
				insert into employees (
					 employee_id,
					 first_name,
					 last_name,
					 email,
					 phone_number,
					 hire_date,
					 job_id,
					 salary,
					 commission_pct,
					 manager_id,
					 department_id )
				values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
				""";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, emp.getEmployee_id());
			st.setString(2, emp.getFirst_name());
			st.setString(3, emp.getLast_name());
			st.setString(4, emp.getEmail());
			st.setString(5, emp.getPhone_number());
			st.setDate(6, emp.getHire_date());
			st.setString(7, emp.getJob_id());
			st.setDouble(8, emp.getSalary());
			st.setDouble(9, emp.getCommission_pct());
			st.setInt(10, emp.getManager_id());
			st.setInt(11, emp.getDepartment_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 삭제
	public int empDeleteById(int empId) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = " delete from employees where employee_id = ? ";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, empId);
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<EmpDTO> selectByCondition(String jobId, int deptId, int salary, String hdate) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "select * from employees "
				+ " where job_id like ? "
				+ " and department_id = ?"
				+ " and salary >= ?"
				+ " and hire_date >= ?";
		
		try {
			st = conn.prepareStatement(sql); 	// SQL문을 준비한다. (prepareStatement)
			st.setString(1, "%" + jobId + "%"); // 첫 번째 물음표의 값에 해당 값을 setting한다.
			st.setInt(2, deptId);  		 		// 두 번째 물음표의 값에 해당 값을 setting한다.
			st.setInt(3, salary);  		 		// 세 번째 물음표의 값에 해당 값을 setting한다.
			
			Date d = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
			st.setDate(4, d);  		 			// 네 번째 물음표의 값에 해당 값을 setting한다.
			rs = st.executeQuery(); 		
			while (rs.next()) { 			
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}
	
	public List<EmpDTO> selectByJobAndDept(String jobId, int dept) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "select * from employees where job_id = ? and department_id = ?";
		
		try {
			st = conn.prepareStatement(sql); // SQL문을 준비한다. (prepareStatement)
			st.setString(1, jobId);  		 // 첫 번째 물음표의 값에 해당 값을 setting한다.
			st.setInt(2, dept);  		 // 두 번째 물음표의 값에 해당 값을 setting한다.
			rs = st.executeQuery(); 		
			while (rs.next()) { 			
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}
	
	// 직책으로 해당되는 직원 정보 조회
	public List<EmpDTO> selectByJob(String jobId) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "select * from employees where job_id = ?";
		
		try {
			st = conn.prepareStatement(sql); // SQL문을 준비한다. (prepareStatement)
			st.setString(1, jobId);  		 // 첫 번째 물음표의 값에 해당 값을 setting한다.
			rs = st.executeQuery(); 		
			while (rs.next()) { 			
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}
	
	
	// 부서에 해당하는 직원 정보를 조회
	public List<EmpDTO> selectByDept(int deptId) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "select * from employees where department_id = " + deptId;
		
		try {
			st = conn.createStatement(); 	
			rs = st.executeQuery(sql); 		
			while (rs.next()) { 			
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	
	// 직원 번호로 직원 정보를 상세보기
	public EmpDTO selectById(int empId) {
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "select * from employees where employee_id = " + empId;
		
		try {
			st = conn.createStatement(); 	// 통신 통로 생성
			rs = st.executeQuery(sql); 		// 쿼리 실행
			if (rs.next()) { 			
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emp;
	}
	
	
	// 모든 직원 조회
	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "select * from employees";
		
		try {
			st = conn.createStatement(); 	// 통신 통로 생성
			rs = st.executeQuery(sql); 		// 쿼리 실행
			while (rs.next()) { 			// rs의 값이 없을 때까지.
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	private EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = EmpDTO.builder()
				.commission_pct(rs.getDouble("commission_pct"))
				.department_id(rs.getInt("department_id"))
				.email(rs.getString("email"))
				.employee_id(rs.getInt("employee_id"))
				.first_name(rs.getString("first_name"))
				.hire_date(rs.getDate("hire_date"))
				.job_id(rs.getString("job_id"))
				.last_name(rs.getString("last_name"))
				.manager_id(rs.getInt("manager_id"))
				.phone_number(rs.getString("phone_number"))
				.salary(rs.getDouble("salary"))
				.build();
		
		return emp;
	}

}
