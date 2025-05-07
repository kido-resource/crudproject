package com.shinhan.dept;

import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

import oracle.net.aso.d;

public class DeptController implements CommonControllerInterface {
	
	static Scanner sc = new Scanner(System.in);
	static DeptService deptService = new DeptService();
	
	@Override
	//public static void main(String[] args) {
	public void execute () {
		boolean isStop = false;
		
		while(!isStop) {
			menuDisplay();
			int num = sc.nextInt();
			switch (num) {
			case 1 -> { f_selectAll(); }
			case 2 -> { f_selectByDept(); }
			case 3 -> { f_updateByDept(); }
			case 4 -> { f_deleteByDept(); }
			case 5 -> { f_insertIntoDept();}
			case 6 -> { isStop = true; }
			}
		}
		System.out.println("======== 프로그램 종료 ==========");
	}


	private static void f_insertIntoDept() {
		System.out.print("새롭게 생성할 부서 ID를 기입하세요: ");
		int deptId = sc.nextInt();
		int result = deptService.insertIntoDept(insertDetail(deptId));
		DeptView.display(result + "건이 새롭게 생성되었습니다.");
		
	}


	private static DeptDTO insertDetail(int deptId) {
		System.out.print("새 부서의 Name을 기입하세요: ");
		String department_name = sc.next();
		
		System.out.print("새 부서의 Manager_ID를 기입하세요: ");
		String manager_id =  sc.next();

		System.out.print("새 부서의 Location_ID를 기입하세요: ");
		String location_id = sc.next();
		
		DeptDTO dept = DeptDTO.builder()
				.department_id(deptId)
				.department_name(department_name)
				.manager_id(manager_id)
				.location_id(location_id)
				.build();
		return dept;
	}


	private static void f_deleteByDept() {
		System.out.print("삭제할 부서의 부서 ID를 기입하세요: ");
		int deptId = sc.nextInt();
		int result = deptService.deleteByDept(deptId);
		DeptView.display(result + "건이 삭제되었습니다.");
	}


	private static void f_updateByDept() {
		System.out.print("수정할 부서의 부서 ID를 기입하세요: ");
		int deptId = sc.nextInt();
		int result = deptService.updateByDept(updateDetail(deptId));
		DeptView.display(result + "건이 수정되었습니다.");
	}


	private static DeptDTO updateDetail(int deptId) {
		System.out.print("수정할 부서 이름 : ");
		String department_name = sc.next();
		System.out.print("수정할 매니저 ID : ");
		String manager_id =  sc.next();
		System.out.print("수정할 Location ID : ");
		String location_id = sc.next();
		
		if (department_name.equals("0")) department_name = null;
		if (manager_id.equals("0")) manager_id = null;
		if (location_id.equals("0")) location_id = null;
		
		DeptDTO dept = DeptDTO.builder()
				.department_id(deptId)
				.department_name(department_name)
				.manager_id(manager_id)
				.location_id(location_id)
				.build();
		
		return dept;
	}


	private static void f_selectByDept() {
		System.out.print("조회할 부서 ID를 기입하세요: ");
		int deptId = sc.nextInt();
		DeptDTO dept = deptService.selectByDept(deptId);
		DeptView.display(dept);
	}


	private static void f_selectAll() {
		List<DeptDTO> deptList = deptService.selectAll();
		DeptView.display(deptList);
	}

	private static void menuDisplay() {
		System.out.println("---------------------------------------------------------------");
		System.out.println("1. 모두 조회 | 2. 조회(부서 번호) | 3. 수정 | 4. 삭제 | 5. 입력 | 6. 종료");
		System.out.println("---------------------------------------------------------------");
		System.out.print("작업을 선택하세요 >> ");
	}
}
