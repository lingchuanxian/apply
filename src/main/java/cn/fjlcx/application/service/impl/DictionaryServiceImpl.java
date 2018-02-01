package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.DictionaryMapper;
import cn.fjlcx.application.bean.Dictionary;
import cn.fjlcx.application.service.DictionaryService;
import cn.fjlcx.application.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年2月1日 下午2:57:54
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Service
@Transactional
public class DictionaryServiceImpl extends AbstractService<Dictionary> implements DictionaryService {
    @Resource
    private DictionaryMapper oaDictionaryMapper;

	@Override
	public List<Dictionary> selectDictionaryByCondition(Map<String, Object> params) {
		return oaDictionaryMapper.selectDictionaryByCondition(params);
	}

	@Override
	public int selectMaxOrder() {
		return oaDictionaryMapper.selectMaxOrder();
	}

}
