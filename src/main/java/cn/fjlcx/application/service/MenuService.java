package cn.fjlcx.application.service;
import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.Menu;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/09/16.
 */
public interface MenuService extends Service<Menu> {
	/**
	 * 获取所有菜单
	 * @return
	 */
	List<Menu> selectAllOfMenu();
	/**
	 * 根据ID删除菜单
	 * @param id
	 * @return
	 */
	int deleteMenuById(int id);
	/**
	 * 根据ID查询菜单
	 * @param id
	 * @return
	 */
	Menu selectMenuById(int id);
	/**
	 * 根据Pid查询子菜单
	 * @param pid
	 * @return
	 */
	List<Menu> selectMenuByPid(int pid);
	/**
	 * 更新菜单排序
	 * @param params
	 * @return
	 */
	int updateMenuOrder(Map<String,Integer> params);
	/**
	 * 获取最大的order数值
	 * @return
	 */
	int selectMaxOrder();
	/**
	 * 获取最大的ID数值
	 * @return
	 */
	int selectMaxId();
	/**
	 * 根据ID进行假删除
	 * @param id
	 * @return
	 */
	int falseDeletion(int id);
}
