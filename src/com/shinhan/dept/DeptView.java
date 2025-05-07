package com.shinhan.dept;

import java.util.List;

public class DeptView {

	public static void display(List<DeptDTO> deptList) {
		if(deptList.size() == 0) {
			System.out.println("해당 조건의 값이 존재하지 않습니다.");
			return;
		}
		
		System.out.println("=========부서 여러건 조회==========");
		deptList.stream().forEach(dept -> {
			System.out.println(dept);
		});
	}
	
	public static void display(DeptDTO dept) {
		if (dept == null) {
			System.out.println("해당 조건의 값이 존재하지 않습니다.");
			return;
		}
		System.out.println("부서 정보 : " + dept);
	}

	public static void display(String message) {
		System.out.println("결과: " + message);
	}

}
