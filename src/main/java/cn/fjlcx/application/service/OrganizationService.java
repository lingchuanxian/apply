package cn.fjlcx.application.service;
import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.Organization;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/09/26.
 */
public interface OrganizationService extends Service<Organization> {
	/**
	 * 查询全部的机构
	 * @return
	 */
	List<Organization> selectOrganizationOfAll();
	/**
	 * 获取最大的order数值
	 * @return
	 */
	int selectMaxOrder();
	/**
	 * 根据ID查询机构信息
	 * @param id
	 * @return
	 */
	Organization selectOrganizationById(int id);
	/**
	 * 更新机构排序
	 * @param params
	 * @return
	 */
	int updateOrganizationOrder(Map<String,Integer> params);
	/**
	 * 根据机构ID进行机构假删除
	 * @param id
	 * @return
	 */
	int falseDeletion(int id);
}
