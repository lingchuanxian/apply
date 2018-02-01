package cn.fjlcx.application.service;
import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.Dictionary;
import cn.fjlcx.application.core.Service;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年2月1日 下午2:57:23
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
public interface DictionaryService extends Service<Dictionary> {
	List<Dictionary> selectDictionaryByCondition(Map<String,Object> params);
	int selectMaxOrder();
}
