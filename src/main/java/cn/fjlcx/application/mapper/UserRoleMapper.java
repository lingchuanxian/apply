package cn.fjlcx.application.mapper;

import java.util.List;

import cn.fjlcx.application.bean.UserRole;
import cn.fjlcx.application.core.Mapper;

public interface UserRoleMapper extends Mapper<UserRole> {
	List<UserRole> selectUserRoleByUserId(int id);
	List<UserRole> selectUserRoleByRoleId(int id);
	int deleteUserRoleByRoleId(int id);
	int falseDeletion(int id);
}