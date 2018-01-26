package cn.fjlcx.application.service;
import java.util.List;

import cn.fjlcx.application.bean.Department;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/09/26.
 */
public interface DepartmentService extends Service<Department> {
	/**
	 * 根据机构ID查询下属部门
	 * @param id
	 * @return
	 */
	List<Department> selectDepByOrgId(int id);
	/**
	 * 根据部门ID查询部门信息
	 * @param id
	 * @return
	 */
	Department selectDepById(int id);
	/**
	 * 获取最大的order数值
	 * @return
	 */
	int selectMaxOrder();
	/**
	 * 根据部门ID进行部门假删除
	 * @param id
	 * @return
	 */
	int falseDeletion(int id);
}
