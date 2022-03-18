package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeremoveMapper;
import org.javaboy.vhr.model.Employeeremove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesMobilizeService {

	@Autowired
	EmployeeremoveMapper employeeremoveMapper;
	public List<Employeeremove> getEmployeeremoveList() {
		return employeeremoveMapper.getEmployeeremoveList();
	}

	//添加
	public Integer addEmployeeremove(Employeeremove employeeremove) {
//		adjustSalary.setCreateDate(new Date());
//		adjustSalary.setEnabled(true);
		return employeeremoveMapper.insertSelective(employeeremove);
	}


	public Integer updateEmployeeremoveById(Employeeremove employeeremove) {
		return employeeremoveMapper.updateByPrimaryKeySelective(employeeremove);
	}

	public Integer deleteEmployeeremovelById(Integer id) {
		return employeeremoveMapper.deleteByPrimaryKey(id);
	}


}
