package cn.fjlcx.application.service;
import java.util.List;

import cn.fjlcx.application.bean.UserRole;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/09/25.
 */
public interface UserRoleService extends Service<UserRole> {
	/**
	 * 根据用户ID查询用户-角色的对应关系
	 * @param id
	 * @return
	 */
	List<UserRole> selectUserRoleByUserId(int id);
	/**
	 * 根据角色ID查询用户-角色的对应关系
	 * @param id
	 * @return
	 */
	List<UserRole> selectUserRoleByRoleId(int id);
	/**
	 * 根据角色ID删除用户-角色的对应关系
	 * @param id
	 * @return
	 */
	int deleteUserRoleByRoleId(int id);
	/**
	 * 根据ID进行假删除
	 * @param id
	 * @return
	 */
	int falseDeletion(int id);
}
