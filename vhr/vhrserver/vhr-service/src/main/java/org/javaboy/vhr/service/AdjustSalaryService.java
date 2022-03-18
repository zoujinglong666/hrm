package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.AdjustSalaryMapper;
import org.javaboy.vhr.model.AdjustSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service


public class AdjustSalaryService {

	@Autowired
	AdjustSalaryMapper adjustSalaryMapper;

	public List<AdjustSalary> getList() {
		return adjustSalaryMapper.getList();
	}


	//添加
	public Integer addAdjustSalary(AdjustSalary adjustSalary) {
//		adjustSalary.setCreateDate(new Date());
//		adjustSalary.setEnabled(true);
		return adjustSalaryMapper.insertSelective(adjustSalary);
	}


	public Integer updateAdjustSalaryById(AdjustSalary adjustSalary) {
		return adjustSalaryMapper.updateByPrimaryKeySelective(adjustSalary);
	}

	public Integer deleteAdjustSalarylById(Integer id) {
		return adjustSalaryMapper.deleteByPrimaryKey(id);
	}
}
