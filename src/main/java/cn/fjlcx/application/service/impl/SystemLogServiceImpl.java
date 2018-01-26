package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.SystemLogMapper;
import cn.fjlcx.application.bean.SystemLog;
import cn.fjlcx.application.service.SystemLogService;
import cn.fjlcx.application.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 *  @author ling_cx 
 *  @date   2017/12/06.
 */
@Service("SystemLogService")
@Transactional
public class SystemLogServiceImpl extends AbstractService<SystemLog> implements SystemLogService {
    @Resource
    private SystemLogMapper oaSystemLogMapper;

	@Override
	public List<SystemLog> selectSystemLogByCondition(Map<String, Object> params) {
		return oaSystemLogMapper.selectSystemLogByCondition(params);
	}

	@Override
	public SystemLog selectSystemLogById(int id) {
		return oaSystemLogMapper.selectSystemLogById(id);
	}

}
