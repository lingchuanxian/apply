package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.UserRoleMapper;
import cn.fjlcx.application.bean.UserRole;
import cn.fjlcx.application.service.UserRoleService;
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
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper oaUserRoleMapper;

	@Override
	public List<UserRole> selectUserRoleByUserId(int id) {
		return oaUserRoleMapper.selectUserRoleByUserId(id);
	}

	@Override
	public List<UserRole> selectUserRoleByRoleId(int id) {
		return oaUserRoleMapper.selectUserRoleByRoleId(id);
	}

	@Override
	public int deleteUserRoleByRoleId(int id) {
		return oaUserRoleMapper.deleteUserRoleByRoleId(id);
	}

	@Override
	public int falseDeletion(int id) {
		return oaUserRoleMapper.falseDeletion(id);
	}

}
