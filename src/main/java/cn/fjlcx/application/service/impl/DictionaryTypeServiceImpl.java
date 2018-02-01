package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.DictionaryTypeMapper;
import cn.fjlcx.application.bean.DictionaryType;
import cn.fjlcx.application.service.DictionaryTypeService;
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
 * @date 2018年2月1日 上午9:27:14
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Service
@Transactional
public class DictionaryTypeServiceImpl extends AbstractService<DictionaryType> implements DictionaryTypeService {
    @Resource
    private DictionaryTypeMapper oaDictionaryTypeMapper;

	@Override
	public List<DictionaryType> selectDictionaryTypeByCondition(Map<String, Object> params) {
		return oaDictionaryTypeMapper.selectDictionaryTypeByCondition(params);
	}

	@Override
	public DictionaryType selectByCode(String code) {
		return oaDictionaryTypeMapper.selectByCode(code);
	}

}
