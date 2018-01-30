package cn.fjlcx.application.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fjlcx.application.bean.Organization;
import cn.fjlcx.application.bean.TreeJson;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.OrganizationService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月22日 下午3:09:55
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/organization")
public class OrganizationController extends BaseController{

	@Resource
	private OrganizationService organizationService;

	@RequiresPermissions("system:organization:list")
	@GetMapping("list")
	public String organizationList() {
		return "admin/organization/list";
	}

	/**
	 * 查询全部机构
	 * @return
	 */
	@RequiresPermissions("system:organization:select")
	@GetMapping("select")
	@ResponseBody
	public Result selectOrganizationOfAll() {
		List<Organization> organization = organizationService.selectOrganizationOfAll();
		return ResultGenerator.genSuccessResult(Organization.formatTree(organization));
	}

	/**
	 * 查询指定机构
	 * @return
	 */
	@RequiresPermissions("system:organization:select")
	@PostMapping("select/{id}")
	@ResponseBody
	public Result selectOrganizationById(@PathVariable int id) {
		Organization organization = organizationService.selectOrganizationById(id);
		return ResultGenerator.genSuccessResult(organization);
	}

	/**
	 * 查询待选择机构
	 * @return
	 */
	@RequiresPermissions("system:organization:select")
	@GetMapping("selectOrganizationForSelect")
	@ResponseBody
	public Result selectOrganizationForSelect() {
		List<Organization> organization = organizationService.selectOrganizationOfAll();
		List<TreeJson> treeList = new ArrayList<>();
		for(Organization org : organization) {
			TreeJson tree = new TreeJson();
			tree.setId(org.getOrgId());
			tree.setText(org.getOrgName());
			tree.setPid(org.getOrgPid());
			treeList.add(tree);
		}
		return ResultGenerator.genSuccessResult(TreeJson.formatTree(treeList));
	}

	/**
	 * 新增机构信息
	 * @param org
	 * @return
	 */
	@RequiresPermissions("system:organization:insert")
	@PostMapping("insert")
	@SystemControllerLog(description = "新增机构信息")
	@ResponseBody
	public Result insertOrganization(@ModelAttribute Organization org) {
		org.setOrgOrder(organizationService.selectMaxOrder()+1);
		organizationService.save(org);
		return ResultGenerator.genSuccessResult().setMessage("操作成功");
	}
	
	/**
	 * 更新机构信息
	 * @param org
	 * @return
	 */
	@RequiresPermissions("system:organization:update")
	@PostMapping("update")
	@SystemControllerLog(description = "更新机构信息")
	@ResponseBody
	public Result updateOrganization(@ModelAttribute Organization org) {
		organizationService.update(org);
		return ResultGenerator.genSuccessResult().setMessage("更新成功");
	}

	/**
	 * 删除指定机构(假删除)
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:organization:delete")
	@PostMapping("delete")
	@SystemControllerLog(description = "根据id删除指定机构")
	@ResponseBody
	public Result DeleteOrganizationById(@PathVariable int id) {
		organizationService.falseDeletion(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}

	/**
	 * 交换相邻两个机构的排序
	 * @param id1
	 * @param id2
	 * @return
	 */
	@RequiresPermissions("system:organization:exchange")
	@PostMapping("ExchangeOrgPosition")
	@ResponseBody
	public Result ExchangeOrgPosition(@RequestParam int id1,@RequestParam int id2) {
		Organization org1 = organizationService.selectOrganizationById(id1);
		Organization org2 = organizationService.selectOrganizationById(id2);
		int order1 = org1.getOrgOrder();
		org1.setOrgOrder(org2.getOrgOrder());
		org2.setOrgOrder(order1);
		organizationService.update(org1);
		organizationService.update(org2);
		return ResultGenerator.genSuccessResult().setMessage("修改成功");
	}
}
