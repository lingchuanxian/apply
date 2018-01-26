package cn.fjlcx.application.service;
import java.util.List;

import cn.fjlcx.application.bean.Role;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/09/25.
 */
public interface RoleService extends Service<Role> {
	/**
	 * 查询所有角色
	 * @return
	 */
	List<Role> selectAllOfRole();
	/**
	 * 获取最大的order值
	 * @return
	 */
	int selectMaxOrder();
	/**
	 * 根据ID进行假删除
	 * @param id
	 * @return
	 */
	int falseDeletion(int id);
}
