package com.shinhan.common;

import java.util.Scanner;

import com.shinhan.dept.DeptController;
import com.shinhan.emp.EmpController;

// FrontController 패턴 : Controller가 여러 개인 경우 사용자의 요청과 응답은 출구가 여러 개
// 바람직하지 않음
// 하나로 통합 (Front는 1개)
// Servlet : DispatcherServlet이 있다. (Spring이 제공(FrontController))
public class FrontController {
	
public static void main(String[] args) {
		
		// 사용자가 emp or dept 작업할 것인지 결정
		Scanner sc = new Scanner(System.in);
		boolean isStop = false;
		CommonControllerInterface controller = null;
		while (!isStop) {
			System.out.println("emp, dept, job, end>> ");
			String job = sc.next();
			switch (job) {
			case "emp" -> {controller = ControllerFactory.make("emp");}
			case "dept" -> {controller = ControllerFactory.make("dept");}
			case "job" -> {controller = ControllerFactory.make("job");}
			case "end" -> {isStop = true; continue;}
			default -> {continue;}
			}
			// 전략 패턴
			controller.execute(); // 작업은 달라져도 사용법은 같다. (전략 패턴)
		}
		sc.close();
		System.out.println("========== MAIN END ==========");
	}
	
	public static void main2(String[] args) {
		
		// 사용자가 emp or dept 작업할 것인지 결정
		Scanner sc = new Scanner(System.in);
		
		boolean isStop = false;
		CommonControllerInterface controller = null;
		while (!isStop) {
			String job = sc.next();
			System.out.println("emp, dept>> ");
			if (job.equals("emp")) {
				controller = new EmpController();
			} else if(job.equals("dept")) {
				controller = new DeptController();
			} else if(job.equals("end")) {
				isStop = true;
			}
			controller.execute(); // 작업은 달라져도 사용법은 같다. (전략 패턴)
		}
		sc.close();
		System.out.println("========== MAIN END ==========");
	}
}
