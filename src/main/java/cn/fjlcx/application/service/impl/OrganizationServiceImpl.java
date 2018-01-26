package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.OrganizationMapper;
import cn.fjlcx.application.bean.Organization;
import cn.fjlcx.application.service.OrganizationService;
import cn.fjlcx.application.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 *  @author ling_cx 
 *  @date   2017/09/26.
 */
@Service
@Transactional
public class OrganizationServiceImpl extends AbstractService<Organization> implements OrganizationService {
    @Resource
    private OrganizationMapper oaOrganizationMapper;

	@Override
	public List<Organization> selectOrganizationOfAll() {
		return oaOrganizationMapper.selectOrganizationOfAll();
	}

	@Override
	public int selectMaxOrder() {
		return oaOrganizationMapper.selectMaxOrder();
	}

	@Override
	public Organization selectOrganizationById(int id) {
		return oaOrganizationMapper.selectOrganizationById(id);
	}

	@Override
	public int updateOrganizationOrder(Map<String, Integer> params) {
		return oaOrganizationMapper.updateOrganizationOrder(params);
	}

	@Override
	public int falseDeletion(int id) {
		return oaOrganizationMapper.falseDeletion(id);
	}

}
