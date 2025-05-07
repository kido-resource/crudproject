package com.shinhan.emp;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

// MVC2 모델
// FrontController -> Controller 선택 -> Service -> DAO -> DB
//						<-				<-		<-		<-
public class EmpController implements CommonControllerInterface {

	static Scanner sc = new Scanner(System.in);
	static EmpService empService = new EmpService();
	
	@Override
	public void execute () {
		
		boolean isStop = false;
		
		while (!isStop) {
			menuDisplay();
			int job = sc.nextInt();
			switch (job) {
			case 1 -> { f_selectAll(); }
			case 2 -> { f_selectById();}
			case 3 -> { f_selectByDept();}
			case 4 -> { f_selectByJob();}
			case 5 -> { f_selectByJobAndDept();}
			case 6 -> { f_selectByCondition();}
			case 7 -> { f_deleteByEmpId();}
			case 8 -> { f_InsertByEmpId();}
			case 9 -> { f_updateByEmpId();}
			case 10 -> { f_sp_call();}
			case 99 -> { isStop = true; }
			}
		}
		System.out.println("=========    ====    =========");
		System.out.println("========  ===    ===  ========");
		System.out.println("=======   Good Bye!!!  =======");
		System.out.println("========  ==========  ========");
		System.out.println("==========  ======  ==========");
		System.out.println("============  ==  ============");
		System.out.println("==============   =============");
	}

	private static void f_sp_call() {
		System.out.print("조회할 직원 ID >>");
		int employee_id = sc.nextInt();
		EmpDTO emp = empService.execute_sp(employee_id);
		String message = "해당 직원은 존재하지 않습니다.";
		if(emp != null) {
			message = emp.getEmail() + "---" + emp.getSalary();
		}
		EmpView.display(message);
	}

	private static void f_updateByEmpId() {
		System.out.print("수정할 직원 ID >>");
		int employee_id = sc.nextInt();
		EmpDTO exist_emp = empService.selectById(employee_id);
		if(exist_emp == null) {
			EmpView.display("존재하지 않는 직원입니다.");
			return;
		}
		EmpView.display("==========존재하는 직원 정보입니다.==========");
		EmpView.display(exist_emp);
		int result = empService.empUpdate(makeEmp(employee_id));
		
		EmpView.display(result + "건 수정"); 
	}
	
	private static void f_InsertByEmpId() {
		System.out.print("1. 입력할 ID (PK) >> ");
		int employee_id = sc.nextInt();
		
		int result = empService.empInsertById(makeEmp2(employee_id));
		EmpView.display(result + "건 입력"); 
	}
	
	// 동적 SQL Update
	static EmpDTO makeEmp(int employee_id) {
		System.out.print("first_name>>");
		String first_name = sc.next();
		
		System.out.print("last_name>>");
		String last_name = sc.next();
		
		System.out.print("email>>");
		String email = sc.next();
		
		System.out.print("phone_number>>");
		String phone_number = sc.next();
		
		System.out.print("hdate(yyyy-MM-dd)>>");
		String hdate = sc.next();
		Date hire_date = null;
		if(!hdate.equals("0"))
		   hire_date = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
		
		System.out.print("job_id(FK:IT_PROG)>>");
		String job_id = sc.next();
		
		System.out.print("salary>>");
		Double salary = sc.nextDouble();
		
		System.out.print("commission_pct(0.2)>>");
		Double commission_pct = sc.nextDouble();
		System.out.print("manager_id(FK:100)>>");
		Integer manager_id = sc.nextInt();
		System.out.print("department_id(FK:60,90)>>");
		Integer department_id = sc.nextInt();
		
		if(first_name.equals("0")) first_name = null;
		if(last_name.equals("0")) last_name = null;
		if(email.equals("0")) email = null;
		if(phone_number.equals("0")) phone_number = null;
		if(job_id.equals("0")) job_id = null;
		if(salary==0) salary = null;
		if(commission_pct==0) commission_pct = null;
		if(manager_id==0) manager_id = null;
		if(department_id==0) department_id = null;
		
		
		
		EmpDTO emp = EmpDTO.builder().commission_pct(commission_pct).department_id(department_id).email(email)
				.employee_id(employee_id).first_name(first_name).hire_date(hire_date).job_id(job_id)
				.last_name(last_name).manager_id(manager_id).phone_number(phone_number).salary(salary).build();
		System.out.println(emp);
		
		return emp;
	}
	
	static EmpDTO makeEmp2(int employee_id) {
		System.out.print("2. 입력할 이름 >> ");
		String first_name = sc.next();
		System.out.print("3. 입력할 이름2 >> ");
		String last_name = sc.next();
		System.out.print("4. 입력할 이메일 >> ");
		String email = sc.next();
		System.out.print("5. 입력할 핸드폰 번호 >> ");
		String phone_number = sc.next();
		System.out.print("6. 입력할 고용일 >> ");
		String hdate = sc.next();
		Date hire_date = null;
		if(!hdate.equals("0")) 
			hire_date = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
		
		System.out.print("7. 입력할 직무 ID (FK) >> ");
		String job_id = sc.next();
		System.out.print("8. 입력할 급여 >> ");
		double salary = sc.nextDouble();
		System.out.print("9. 입력할 Commision PCT >> ");
		double commission_pct = sc.nextDouble();
		System.out.print("10. 입력할 직속 상관 ID (FK) >> ");
		Integer manager_id = sc.nextInt();
		System.out.print("11. 입력할 부서 ID (FK) >> ");
		Integer department_id = sc.nextInt();
		
		if(first_name.equals("0")) first_name = null;
		if(last_name.equals("0")) last_name = null;
		if(email.equals("0")) email = null;
		if(phone_number.equals("0")) phone_number = null;
		if(job_id.equals("0")) job_id = null;
		if(salary == 0) salary = (Double) null;
		if(commission_pct == 0) commission_pct = (Double) null;
		if(manager_id == 0) manager_id = null;
		if(department_id == 0) department_id = null;
		
		
		EmpDTO emp = EmpDTO.builder()
				.employee_id(employee_id)
				.first_name(first_name)
				.last_name(last_name)
				.email(email)
				.phone_number(phone_number)
				.hire_date(hire_date)
				.job_id(job_id)
				.salary(salary)
				.commission_pct(commission_pct)
				.manager_id(manager_id)
				.department_id(department_id)
				.build();
		return emp;
	}
	
	private static void f_deleteByEmpId() {
		System.out.print("삭제할 직원 ID >> ");
		int empId = sc.nextInt();
		int result = empService.empDeleteById(empId);
		EmpView.display(result + "건 삭제");
	}

	private static void f_selectByCondition() {
		// = 부서, like 직책, >= 급여, >= 입사일
		System.out.print("조회할 부서 ID >> ");
		int deptId = sc.nextInt();
		
		System.out.print("조회할 직책 >> ");
		String jobId = sc.next();
		
		System.out.print("조회할 급여(이상) >> ");
		int salary = sc.nextInt();
		
		System.out.print("조회할 입사일('YYYY-MM-DD'이상) >> ");
		String hdate = sc.next();

		List<EmpDTO> empList = empService.selectByCondition(jobId, deptId, salary, hdate);
		EmpView.display(empList);
	}

	private static void f_selectByJobAndDept() {
		System.out.print("조회할 직책 ID, 부서 ID >> "); // ex) IT_PROG,60
		String data = sc.next();
		String[] arr = data.split(",");
		String jobId = arr[0];
		int deptId = Integer.parseInt(arr[1]);
		List<EmpDTO> empList = empService.selectByJobAndDept(jobId, deptId);
		EmpView.display(empList);
	}

	private static void f_selectByJob() {
		System.out.print("조회할 직책 >> ");
		String jobId = sc.next();
		List<EmpDTO> empList = empService.selectByJob(jobId);
		EmpView.display(empList);
	}

	private static void f_selectByDept() {
		System.out.print("조회할 부서 ID >> ");
		int deptId = sc.nextInt();
		List<EmpDTO> empList = empService.selectByDept(deptId);
		EmpView.display(empList);
	}

	private static void f_selectById() {
		System.out.print("조회할 ID >> ");
		int empId = sc.nextInt();
		EmpDTO emp = empService.selectById(empId);
		EmpView.display(emp);
	}

	private static void f_selectAll() {
		List<EmpDTO> empList = empService.selectAll();
		EmpView.display(empList);
	}

	private static void menuDisplay() {
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("1. 모두 조회 | 2. 조회(직원 번호) | 3. 조회(부서) | 4. 조회(직책) | 5. 조회(부서,직책) | 6. 복합 조회 | 7. 삭제 | 8. 내용 추가 | 9. 내용 수정 | 99. 종료");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
		System.out.print("작업을 선택하세요 >> ");
	}

}
