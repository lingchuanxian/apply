package cn.fjlcx.application.controller.admin;

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

import cn.fjlcx.application.bean.Role;
import cn.fjlcx.application.bean.RoleMenu;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.RoleMenuService;
import cn.fjlcx.application.service.RoleService;
import cn.fjlcx.application.service.UserRoleService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月22日 下午3:51:23
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/role")
public class RoleController  extends BaseController{

	@Resource
	private RoleService roleService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RoleMenuService roleMenuService;

	@RequiresPermissions("system:role:list")
	@GetMapping("list")
	public String roleList() {
		return "admin/role/list";
	}
	/**
	 * 查询全部角色
	 * @return
	 */
	@RequiresPermissions("system:role:select")
	@GetMapping("selectAllOfRole")
	@ResponseBody
	public Result selectAllOfRole() {
		List<Role> roleList = roleService.selectAllOfRole();
		return ResultGenerator.genSuccessResult(roleList);
	}

	/**
	 * 新增或编辑角色信息
	 * @param role
	 * @return
	 */
	@RequiresPermissions("system:role:insert")
	@PostMapping("AddOrUpdateRole")
	@SystemControllerLog(description = "新增或更新角色信息")
	@ResponseBody
	public Result AddRole(@ModelAttribute Role role) {
		if(role.getRlId() == null) {
			role.setRlOrder(roleService.selectMaxOrder()+1);
			roleService.save(role);
		}else {
			roleService.update(role);
		}
		return ResultGenerator.genSuccessResult().setMessage("成功");
	}

	/**
	 * 删除指定角色(假删除)
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:role:delete")
	@PostMapping("DeleteRoleById")
	@SystemControllerLog(description = "根据id删除指定角色")
	@ResponseBody
	public Result DeleteRoleById(@RequestParam int id) {
		roleService.falseDeletion(id);
		userRoleService.deleteUserRoleByRoleId(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}

	/**
	 * 查询指定角色的权限菜单
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:role:updatemenu")
	@PostMapping("SelectRoleMenuByRid")
	@ResponseBody
	public Result SelectRoleMenuByRid(@RequestParam int id) {
		List<RoleMenu> listRole = roleMenuService.selectMenuByRole(id);
		return ResultGenerator.genSuccessResult(listRole);
	}

	/**
	 * 保存编辑过后的角色菜单
	 * @param id
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("system:role:updatemenu")
	@PostMapping("SaveRoleMenu")
	@SystemControllerLog(description = "更新角色-菜单的绑定关系")
	@ResponseBody
	public Result SaveRoleMenu(@RequestParam int id,@RequestParam String ids) {
		roleMenuService.deleteRoleMenuByRole(id);
		String[] mid = ids.split(",");
		for(int i = 0;i<mid.length;i++) {
			int muid = Integer.parseInt(mid[i]);
			if(muid!=-1) {
				RoleMenu rm = new RoleMenu();
				rm.setRmRid(id);
				rm.setRmMid(muid);
				roleMenuService.save(rm);
			}
		}
		//更新内存中的角色-菜单信息
		roleMenuService.refreshFilterChain();
		return ResultGenerator.genSuccessResult().setMessage("保存成功");
	}

}
