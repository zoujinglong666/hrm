package org.javaboy.vhr.controller.person;

import org.javaboy.vhr.model.AdjustSalary;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.AdjustSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/per/salary")
public class AdjustSalaryContrloler {

	@Autowired
	AdjustSalaryService adjustSalaryService;

	@GetMapping("/")
	public List<AdjustSalary> getList() {
		return adjustSalaryService.getList();
	}

	@PostMapping("/add")
	public RespBean addAdjustSalary(@RequestBody AdjustSalary adjustSalary) {
		if (adjustSalaryService.addAdjustSalary(adjustSalary) == 1) {
			return RespBean.ok("添加成功!");
		}
		return RespBean.error("添加失败");
	}

	@DeleteMapping("/del/{id}")
	public RespBean deleteAdjustSalarylById(@PathVariable Integer id) {
		if (adjustSalaryService.deleteAdjustSalarylById(id) == 1) {
			return RespBean.ok("删除成功!");
		}
		return RespBean.error("删除失败!");
	}

	@PutMapping("/update")
	public RespBean updateAdjustSalaryById(@RequestBody AdjustSalary adjustSalary) {
		if (adjustSalaryService.updateAdjustSalaryById(adjustSalary) == 1) {
			return RespBean.ok("更新成功!");
		}
		return RespBean.error("更新失败!");
	}

}
