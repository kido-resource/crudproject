package com.shinhan.dept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.common.DBUtil;

public class DeptDAO {
	
	
	// 새로운 부서 정보 입력 (Insert)
	public int insertIntoDept(DeptDTO dept) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = """
				insert into Departments (
					department_id,
					department_name,
					manager_id,
					location_id )
				values (?, ?, ?, ?) 
				""";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, dept.getDepartment_id());
			st.setString(2, dept.getDepartment_name());
			st.setInt(3, Integer.parseInt(dept.getManager_id()));
			st.setInt(4, Integer.parseInt(dept.getLocation_id()));
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 특정 부서 번호의 정보 삭제 (Delete)
	public int deleteByDept(int deptId) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		
		String sql = "delete from departments where department_id = ? ";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, deptId);
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 특정 부서 번호의 정보 수정 (Update)
	public int updateByDept(DeptDTO dept) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		
		String sql = """
					update departments set
						department_name = ?,
						manager_id = ?,
						location_id = ?
					where department_id = ?
				""";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(4, dept.getDepartment_id());
			st.setString(1, dept.getDepartment_name());
			st.setString(2, dept.getManager_id());
			st.setString(3, dept.getLocation_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 부서 번호로 조회
	public DeptDTO selectByDept(int deptId) {
		DeptDTO dept = null; 
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "select * from departments where department_id = " + deptId;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				dept = makeDept(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return dept;
	}
	
	// 모든 부서 조회
	public List<DeptDTO> selectAll() {
		List<DeptDTO> deptList = new ArrayList<DeptDTO>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "select * from departments order by department_id";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				DeptDTO dept = makeDept(rs);
				deptList.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return deptList;
	}
	
	private DeptDTO makeDept(ResultSet rs) throws SQLException {
		DeptDTO dept = DeptDTO.builder()
				.department_id(rs.getInt("department_id"))
				.department_name(rs.getString("department_name"))
				.manager_id(rs.getString("manager_id"))
				.location_id(rs.getString("location_id"))
				.build();
		return dept;
	}
}
