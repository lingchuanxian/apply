package cn.fjlcx.application.mapper;

import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.Dictionary;
import cn.fjlcx.application.core.Mapper;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年2月1日 下午2:57:01
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
public interface DictionaryMapper extends Mapper<Dictionary> {
	List<Dictionary> selectDictionaryByCondition(Map<String,Object> params);
	int selectMaxOrder();
}