package org.javaboy.vhr.controller.person;

import org.javaboy.vhr.model.Employeeremove;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.service.EmployeesMobilizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/per/move")
public class employeesmobilizeContrloler {
	@Autowired
	EmployeesMobilizeService employeesMobilizeService;
	@Autowired
	EmployeeService employeeService;

	@GetMapping("/")
	public List<Employeeremove> getEmployeeremoveList() {
		return employeesMobilizeService.getEmployeeremoveList();
	}




	/*
	* 获取员工表*/
//
//	@GetMapping("/")
//	public RespPageBean getEmployeeWithEmployeesMobilizeService(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10")Integer size){
//
//		return employeeService.getEmployeeWithEmployeesMobilizeService(currentPage,size);
//
//
//	}
	@PostMapping("/add")
	public RespBean addEmployeeremove(@RequestBody Employeeremove employeeremove) {
		if (employeesMobilizeService.addEmployeeremove(employeeremove) == 1) {
			return RespBean.ok("添加成功!");
		}
		return RespBean.error("添加失败");
	}

	@DeleteMapping("/del/{id}")
	public RespBean deleteEmployeeremovelById(@PathVariable Integer id) {
		if (employeesMobilizeService.deleteEmployeeremovelById(id) == 1) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}


	@PutMapping("/update")
	public RespBean updateEmployeeremoveById(@RequestBody Employeeremove employeeremove) {
		if (employeesMobilizeService.updateEmployeeremoveById(employeeremove) == 1) {
			return RespBean.ok("更新成功!");
		}
		return RespBean.error("更新失败!");
	}


}
