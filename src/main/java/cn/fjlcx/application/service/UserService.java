package cn.fjlcx.application.service;

import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.User;
import cn.fjlcx.application.core.Service;

public interface UserService extends Service<User>{
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	User selectUserWithRole(String username);
	/**
	 * 通过条件查询用户信息
	 * @param params
	 * @return
	 */
	List<User> selectUserByConditions(Map<String,Object> params);
	/**
	 * 根据用户ID查询用户信息
	 * @param id
	 * @return
	 */
	User selectUserById(int id);
	/**
	 * 根据用户ID进行用户假删除
	 * @param id
	 * @return
	 */
	int falseDeletion(int id);
}
