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

import cn.fjlcx.application.bean.Menu;
import cn.fjlcx.application.bean.RoleMenu;
import cn.fjlcx.application.bean.TreeJson;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.MenuService;
import cn.fjlcx.application.service.RoleMenuService;
import cn.fjlcx.application.utils.SortListUtil;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 后台菜单管理
 * @date 2018年1月22日 上午10:53:34
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/menu")
public class MenuController extends BaseController{

	@Resource
	private MenuService menuService;
	@Resource
	private RoleMenuService roleMenuService;

	@RequiresPermissions("system:menu:list")
	@GetMapping("list")
	public String menuList() {
		return "/admin/menu/list";
	}
	
	/**
	 * 菜单管理列表
	 * @return
	 */
	@RequiresPermissions("system:menu:select")
	@PostMapping("MagageMenuList")
	@ResponseBody
	public Result MagageMenuList() {
		List<TreeJson> tjs=new ArrayList<TreeJson>();  
		List<Menu> menuList = menuService.selectAllOfMenu();
		@SuppressWarnings("unchecked")
		List<Menu> menuListOrder = (List<Menu>)SortListUtil.sort(menuList,"muOrder",SortListUtil.ASC);
		MenuToTreeJson(tjs, menuListOrder);  
		List<TreeJson> treeList = new ArrayList<>();
		TreeJson tree = new TreeJson();
		tree.setText("主菜单");
		tree.setId(-1);
		tree.setChildren(TreeJson.formatTree(tjs));
		treeList.add(tree);
		return ResultGenerator.genSuccessResult(treeList);
	}

	/**
	 * 根据id获取子菜单
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:menu:select")
	@PostMapping("GetMenuChildrenById")
	@ResponseBody
	public Result GetMenuChildrenById(@RequestParam int id) {
		List<Menu> menuList = menuService.selectMenuByPid(id);
		return ResultGenerator.genSuccessResult(menuList);
	}

	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:menu:delete")
	@PostMapping("DeleteMenuById")
	@SystemControllerLog(description = "根据id删除指定菜单") 
	@ResponseBody
	public Result DeleteMenuById(@RequestParam int id) {
		if(menuService.falseDeletion(id) == 1) {
			roleMenuService.deleteRoleMenuByMenu(id);
			return ResultGenerator.genSuccessResult().setMessage("删除成功");
		}else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}

	/**
	 * 添加菜单
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("system:menu:insert")
	@PostMapping("AddMenu")
	@SystemControllerLog(description = "新增菜单信息") 
	@ResponseBody
	public Result AddMenu(@ModelAttribute Menu menu) {
		logger.info("menu-add:"+menu.toString());
		menuService.save(menu);
		logger.info("menu-add-after:"+menu.toString());
		roleMenuService.save(new RoleMenu(1,12));
		return ResultGenerator.genSuccessResult().setMessage("添加成功");
	}
	/**
	 * 通过id获取菜单
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:menu:select")
	@PostMapping("GetMenuById")
	@ResponseBody
	public Result GetMenuById(@RequestParam int id) {
		Menu menu = menuService.selectMenuById(id);
		return ResultGenerator.genSuccessResult(menu);
	}

	/**
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("system:menu:update")
	@PostMapping("UpdateMenu")
	@SystemControllerLog(description = "更新菜单信息")   
	@ResponseBody
	public Result UpdateMenu(@ModelAttribute Menu menu) {
		logger.info("menu-edit:"+menu.toString());
		if(menu.getMuId() == null) {
			menu.setMuOrder(menuService.selectMaxOrder()+1);
			menuService.save(menu);
			roleMenuService.save(new RoleMenu(1,menuService.selectMaxId()));
		}else {
			menuService.update(menu);
		}
		return ResultGenerator.genSuccessResult().setMessage("修改成功");
	}

	/**
	 * 交换菜单位置（重新排序）
	 * @param id1
	 * @param id2
	 * @return
	 */
	@RequiresPermissions("system:menu:exchange")
	@PostMapping("ExchangeMenuPosition")
	@ResponseBody
	public Result ExchangeMenuPosition(@RequestParam int id1,@RequestParam int id2) {
		Menu menu1 = menuService.selectMenuById(id1);
		Menu menu2 = menuService.selectMenuById(id2);
		int order1 = menu1.getMuOrder();
		menu1.setMuOrder(menu2.getMuOrder());
		menu2.setMuOrder(order1);
		menuService.update(menu1);
		menuService.update(menu2);
		return ResultGenerator.genSuccessResult().setMessage("修改成功");
	}

	@RequiresPermissions("system:menu:select")
	@GetMapping("SelectMenuByRidWithChecked")
	@ResponseBody
	public Result SelectMenuByRidWithChecked(@RequestParam int rid) {
		List<TreeJson> tjs=new ArrayList<TreeJson>();  
		List<RoleMenu> listRole = roleMenuService.selectMenuByRole(rid);
		List<Menu> menuList = menuService.selectAllOfMenu();
		@SuppressWarnings("unchecked")
		List<Menu> menuListOrder = (List<Menu>)SortListUtil.sort(menuList,"muOrder",SortListUtil.ASC);
		MenuToTreeJsonWithRole(tjs, menuListOrder,listRole);  
		List<TreeJson> treeList = new ArrayList<>();
		TreeJson tree = new TreeJson();
		tree.setText("主菜单");
		tree.setId(-1);
		tree.setChildren(TreeJson.formatTree(tjs));
		treeList.add(tree);
		return ResultGenerator.genSuccessResult(treeList);
	}
	
	public void MenuToTreeJsonWithRole(List<TreeJson> tjs, List<Menu> menuList,List<RoleMenu> listRole) {
		for (Menu menu : menuList) {  
			TreeJson tj=new TreeJson();  
			tj.setId(menu.getMuId());  
			tj.setPid(menu.getMuPid());  
			tj.setText(menu.getMuText());  
			tj.setIconCls(menu.getMuIconcls());  
			tj.setState(menu.getMuState());
			tj.setUrl(menu.getMuUrl());
			for(RoleMenu rm:listRole) {
				if(menu.getMuId() == rm.getMenu().getMuId()) {
					tj.setChecked(true);
					break;
				}else {
					tj.setChecked(false);
				}
			}
			tjs.add(tj);  
		}
	}

}
