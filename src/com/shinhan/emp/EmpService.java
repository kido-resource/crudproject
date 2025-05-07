package com.shinhan.emp;

import java.util.List;

// Service : business logic 수행
// 1) 이체 업무 : ( 인출하기, 입금하기 )
// 2) 비밀번호 암호화
public class EmpService {
	
	EmpDAO empDAO = new EmpDAO();
	
	public EmpDTO execute_sp(int empid) {
		return empDAO.execute_sp(empid);
	}
	
	public int empUpdate(EmpDTO emp) { 
		return empDAO.empUpdate(emp);
	}
	
	public int empInsertById(EmpDTO emp) {
		return empDAO.empInsertById(emp);
	}
	
	public int empDeleteById(int empId) {
		return empDAO.empDeleteById(empId);
	}
	
	public List<EmpDTO> selectByCondition(String jobId, int deptId, int salary, String hdate) {
		return empDAO.selectByCondition(jobId, deptId, salary, hdate);
	}
	
	public List<EmpDTO> selectByJobAndDept(String jobId, int dept) {
		return empDAO.selectByJobAndDept(jobId, dept);
	}
	
	public List<EmpDTO> selectByJob(String jobId) {
		return empDAO.selectByJob(jobId);
	}
	
	public List<EmpDTO> selectByDept(int deptId) {
		return empDAO.selectByDept(deptId);
	}
	
	public EmpDTO selectById(int empId) {
		return empDAO.selectById(empId);
	}
	
	public List<EmpDTO> selectAll() {
		return empDAO.selectAll();
	}

}
