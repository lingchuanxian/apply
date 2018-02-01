package cn.fjlcx.application.service;
import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.DictionaryType;
import cn.fjlcx.application.core.Service;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年2月1日 上午9:26:50
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
public interface DictionaryTypeService extends Service<DictionaryType> {

	List<DictionaryType> selectDictionaryTypeByCondition(Map<String,Object> params);
	DictionaryType selectByCode(String code);
}
