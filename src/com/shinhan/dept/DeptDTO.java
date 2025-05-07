package com.shinhan.dept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeptDTO {
	private int department_id;
	private String department_name;
	private String manager_id;
	private String location_id;
}
