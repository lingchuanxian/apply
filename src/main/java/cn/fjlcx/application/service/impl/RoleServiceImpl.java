package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.RoleMapper;
import cn.fjlcx.application.bean.Role;
import cn.fjlcx.application.service.RoleService;
import cn.fjlcx.application.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/**
 *  @author ling_cx 
 *  @date   2017/09/25.
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper oaRoleMapper;
    @Override
	public List<Role> selectAllOfRole() {
		return oaRoleMapper.selectAllOfRole();
	}

	@Override
	public int selectMaxOrder() {
		return oaRoleMapper.selectMaxOrder();
	}

	@Override
	public int falseDeletion(int id) {
		return oaRoleMapper.falseDeletion(id);
	}

}
