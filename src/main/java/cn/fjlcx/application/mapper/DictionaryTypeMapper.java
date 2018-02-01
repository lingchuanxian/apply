package cn.fjlcx.application.mapper;

import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.DictionaryType;
import cn.fjlcx.application.core.Mapper;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年2月1日 上午9:28:00
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
public interface DictionaryTypeMapper extends Mapper<DictionaryType> {
	List<DictionaryType> selectDictionaryTypeByCondition(Map<String,Object> params);
	DictionaryType selectByCode(String code);
}