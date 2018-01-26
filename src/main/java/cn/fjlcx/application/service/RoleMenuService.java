package cn.fjlcx.application.service;
import java.util.List;

import cn.fjlcx.application.bean.RoleMenu;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/09/28.
 */
public interface RoleMenuService extends Service<RoleMenu> {
	/**
	 * 根据角色ID查询菜单-角色的对应关系
	 * @param id
	 * @return
	 */
	List<RoleMenu> selectMenuByRole(int id);
	/**
	 * 根据角色ID删除菜单-角色的对应关系
	 * @param id
	 * @return
	 */
	int deleteRoleMenuByRole(int id);
	/**
	 * 根据菜单ID删除菜单-角色的对应关系
	 * @param id
	 * @return
	 */
	int deleteRoleMenuByMenu(int id);
	/**
	 * 查询所有菜单-角色的对应关系
	 * @return
	 */
	List<RoleMenu> selectRoleMenuAll();
	/**
	 * 根据菜单ID查询菜单-角色的对应关系
	 * @param id
	 * @return
	 */
	List<RoleMenu> selectRoleMenuByMenu(int id);
	/**
	 * 更新内存中的角色-菜单的对应关系
	 */
	void refreshFilterChain();
	/**
	 * 根据机构ID进行假删除
	 * @param id
	 * @return
	 */
	int falseDeletion(int id);
}
