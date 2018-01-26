package cn.fjlcx.application.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.fjlcx.application.bean.User;
import cn.fjlcx.application.core.AbstractService;
import cn.fjlcx.application.mapper.UserMapper;
import cn.fjlcx.application.service.UserService;
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {
	@Resource
	UserMapper mUserMapper;
	@Override
	public User selectUserWithRole(String username) {
		return mUserMapper.selectUserWithRole(username);
	}
	@Override
	public List<User> selectUserByConditions(Map<String,Object> params) {
		return mUserMapper.selectUserByConditions(params);
	}
	@Override
	public User selectUserById(int id) {
		return mUserMapper.selectUserById(id);
	}
	@Override
	public int falseDeletion(int id) {
		return mUserMapper.falseDeletion(id);
	}
}
