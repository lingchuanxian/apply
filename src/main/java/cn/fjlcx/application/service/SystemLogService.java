package cn.fjlcx.application.service;
import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.SystemLog;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/12/06.
 */
public interface SystemLogService extends Service<SystemLog> {
	/**
	 * 根据条件查询系统操作日志
	 * @param params
	 * @return
	 */
	List<SystemLog>  selectSystemLogByCondition(Map<String,Object> params);
	/**
	 * 根据ID查询系统操作日志
	 * @param id
	 * @return
	 */
	SystemLog selectSystemLogById(int id);
}
