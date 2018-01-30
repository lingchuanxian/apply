package cn.fjlcx.application.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fjlcx.application.bean.Department;
import cn.fjlcx.application.bean.TreeJson;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.DepartmentService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月22日 下午3:10:01
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/department")
public class DepartmentController extends BaseController{

	@Resource
	private DepartmentService departmentService;

	@RequiresPermissions("system:department:list")
	@GetMapping("list")
	public String departmentList() {
		return "admin/department/list";
	}

	/**
	 * 查询置顶机构下的部门
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:department:select")
	@PostMapping("selectDepByOrg")
	@ResponseBody
	public Result selectDepByOrg(@RequestParam int id) {
		List<Department> depList = departmentService.selectDepByOrgId(id);
		return ResultGenerator.genSuccessResult(depList);
	}

	/**
	 * 获取待选择部门
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:department:select")
	@GetMapping("selectDepForSelect")
	@ResponseBody
	public Result selectDepForSelect(@RequestParam int id) {
		List<Department> department = departmentService.selectDepByOrgId(id);
		List<TreeJson> treeList = new ArrayList<>();
		for(Department dep : department) {
			TreeJson tree = new TreeJson();
			tree.setId(dep.getDepId());
			tree.setText(dep.getDepName());
			tree.setPid(dep.getDepPid());
			treeList.add(tree);
		}
		return ResultGenerator.genSuccessResult(TreeJson.formatTree(treeList));
	}

	/**
	 * 添加部门信息
	 * @param dep
	 * @return
	 */
	@RequiresPermissions("system:department:insert")
	@PostMapping("insert")
	@SystemControllerLog(description = "新增部门信息")   
	@ResponseBody
	public Result AddDep(@ModelAttribute Department dep) {
		dep.setDepOrder(departmentService.selectMaxOrder()+1);
		departmentService.save(dep);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
	
	/**
	 * 更新部门信息
	 * @param dep
	 * @return
	 */
	@RequiresPermissions("system:department:update")
	@PostMapping("update")
	@SystemControllerLog(description = "更新部门信息")   
	@ResponseBody
	public Result updateDep(@ModelAttribute Department dep) {
		departmentService.update(dep);
		return ResultGenerator.genSuccessResult().setMessage("修改成功");
	}

	/**
	 * 删除指定部门(假删除)
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:department:delete")
	@PostMapping("delete")
	@SystemControllerLog(description = "根据id删除指定部门") 
	@ResponseBody
	public Result DeleteDepById(@RequestParam int id) {
		departmentService.falseDeletion(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}

	/**
	 * 交换相邻部门的位置
	 * @param id1
	 * @param id2
	 * @return
	 */
	@RequiresPermissions("system:department:exchange")
	@PostMapping("ExchangeDepPosition")
	@ResponseBody
	public Result ExchangeDepPosition(@RequestParam int id1,@RequestParam int id2) {
		Department dep1 = departmentService.selectDepById(id1);
		Department dep2 = departmentService.selectDepById(id2);
		int order1 = dep1.getDepOrder();
		dep1.setDepOrder(dep2.getDepOrder());
		dep2.setDepOrder(order1);
		departmentService.update(dep1);
		departmentService.update(dep2);
		return ResultGenerator.genSuccessResult().setMessage("修改成功");
	}
}
