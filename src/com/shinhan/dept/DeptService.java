package com.shinhan.dept;

import java.util.List;

public class DeptService {
	
	DeptDAO deptDAO = new DeptDAO(); 
	
	/* 새로운 부서 입력 */
	public int insertIntoDept(DeptDTO dept) {
		return deptDAO.insertIntoDept(dept);
	};
	
	/* 특정 부서 삭제 */
	public int deleteByDept(int deptId) {
		return deptDAO.deleteByDept(deptId);
	};
	
	/* 특정 부서 수정 */
	public int updateByDept(DeptDTO dept) {
		return deptDAO.updateByDept(dept);
	}
	
	/* 특정 부서 조회 */
	public DeptDTO selectByDept(int deptId) {
		return deptDAO.selectByDept(deptId);
	}
	
	/* 부서 전부 조회 */
	public List<DeptDTO> selectAll() {
		return deptDAO.selectAll();
	}
}
