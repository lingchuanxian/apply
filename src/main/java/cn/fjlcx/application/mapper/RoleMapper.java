package cn.fjlcx.application.mapper;

import java.util.List;

import cn.fjlcx.application.bean.Role;
import cn.fjlcx.application.core.Mapper;

public interface RoleMapper extends Mapper<Role> {
	List<Role> selectAllOfRole();
	int selectMaxOrder();
	int falseDeletion(int id);
}